;;   Copyright (c) Nicola Mometto, Rich Hickey & contributors.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;;   which can be found in the file epl-v10.html at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns clojure.tools.analyzer.passes.jvm.validate
  (:require [clojure.tools.analyzer :refer [-analyze]]
            [clojure.tools.analyzer.utils :refer [arglist-for-arity]]
            [clojure.tools.analyzer.jvm.utils :as u])
  (:import (clojure.lang IFn)))

(defmulti -validate :op)

(defmethod -validate :maybe-class
  [{:keys [class form env] :as ast}]
  (let [{:keys [ns namespaces]} env]
    (if-let [the-class (or (u/maybe-class class)
                           (u/maybe-class (-> @namespaces (get ns) :mappings (get class))))]
      (assoc (-analyze :const the-class env :class)
        :tag  Class
        :form form)
      (if (.contains (str class) ".") ;; try and be smart for the exception
        (throw (ex-info (str "class not found: " class)
                        {:class class}))
        (throw (ex-info (str "could not resolve var: " class)
                        {:var class}))))))

(defmethod -validate :maybe-host-form
  [{:keys [class]}]
  (throw (ex-info (str "No such namespace: " class)
                  {:ns class})))

(defmethod -validate :catch
  [{:keys [class validated?] :as ast}]
  (if-not validated?
    (if-let [the-class (u/maybe-class class)]
      (assoc ast
        :class the-class
        :validated? true)
      (throw (ex-info (str "class not found: " class)
                      {:class class})))
    ast))

(defmethod -validate :set!
  [{:keys [target] :as ast}]
  (when (and (not (:assignable? target))
             (not (= :host-interop (:op target))))
    (throw (ex-info "cannot set! non-assignable target" {:target target})))
  ast)

(defn tag-match? [arg-tags meth]
  (every? identity (map u/convertible? arg-tags (:parameter-types meth))))

(defn try-best-match [tags methods]
  (let [o-tags (mapv #(or (u/maybe-class %) Object) tags)
        exact-matches (seq (filter
                            #(= o-tags (mapv u/maybe-class  (:parameter-types %))) methods))]
    (if exact-matches
      (if (next exact-matches)
        [(reduce (fn [prev next]
                   (if (.isAssignableFrom (u/maybe-class (:return-type prev))
                                          (u/maybe-class (:return-type next)))
                     next
                     prev)) exact-matches)]
        exact-matches)
      (if-let [methods (seq (filter #(tag-match? tags %) methods))]
        (reduce (fn [[prev & _ :as p] next]
                  (if (or (not prev)
                          (and (= (mapv u/maybe-class (:parameter-types prev))
                                  (mapv u/maybe-class (:parameter-types next)))
                               (.isAssignableFrom (u/maybe-class (:return-type prev))
                                                  (u/maybe-class (:return-type next))))
                          (some true? (mapv u/subsumes? (:parameter-types next)
                                         (:parameter-types prev))))
                    [next]
                    (conj p next))) [] methods)
        methods))))

(defn validate-class
  [{:keys [class] :as ast}]
  (if-let [the-class (u/maybe-class class)]
    (assoc ast :class the-class)
    (throw (ex-info (str "class not found: " class)
                    {:class class}))))

(defmethod -validate :new
  [{:keys [validated?] :as ast}]
  (if validated?
    ast
    (let [{:keys [args ^Class class] :as ast} (validate-class ast)
          c-name (symbol (.getName class))
          argc (count args)
          tags (mapv :tag args)]
      (let [[ctor & rest] (->> (filter #(= (count (:parameter-types %)) argc)
                                       (u/members class c-name))
                               (try-best-match tags))]
        (if ctor
          (if (empty? rest)
            (let [arg-tags (mapv u/maybe-class (:parameter-types ctor))
                  args (mapv (fn [arg tag] (assoc arg :tag tag))
                             args arg-tags)]
              (assoc ast
                :args       args
                :validated? true))
            ast)
          (throw (ex-info (str "no ctor found for ctor of class: " class " and give signature")
                          {:class class
                           :args  args})))))))

(defn validate-call [class method args tag ast type]
  (let [argc (count args)
        instance? (= :instance type)
        f (if instance? u/instance-methods u/static-methods)
        tags (mapv :tag args)]
    (if-let [matching-methods (seq (f class method argc))]
      (let [[m & rest :as matching] (try-best-match tags matching-methods)]
        (if m
          (if (empty? rest)
            (let [ret-tag  (u/maybe-class (:return-type m))
                  arg-tags (mapv u/maybe-class (:parameter-types m))
                  args (mapv (fn [arg tag] (assoc arg :tag tag)) args arg-tags)]
              (assoc ast
                :method     (:name m)
                :validated? true
                :ret-tag    ret-tag
                :tag        (or tag ret-tag)
                :args       args))
            (if (apply = (mapv (comp u/maybe-class :return-type) matching))
              (let [ret-tag (u/maybe-class (:return-type m))]
                (assoc ast
                  :ret-tag Object
                  :tag     (or tag ret-tag)))
              ast))
          (if instance?
            (dissoc ast :class :tag)
            (throw (ex-info (str "No matching method: " method " for class: " class " and given signature")
                            {:method method
                             :class  class
                             :args   args})))))
      (if instance?
        (dissoc ast :class :tag)
        (throw (ex-info (str "No matching method: " method " for class: " class " and arity: " argc)
                        {:method method
                         :class  class
                         :argc   argc}))))))

(defmethod -validate :static-call
  [{:keys [class method validated? tag args] :as ast}]
  (if validated?
    ast
    (validate-call class method args tag ast :static)))

(defmethod -validate :instance-call
  [{:keys [class validated? method tag args instance] :as ast}]
  (let [class (or class (u/maybe-class (:tag instance)))]
    (if (and class (not validated?))
      (validate-call class method args tag (assoc ast :class class) :instance)
      ast)))

(defmethod -validate :import
  [{:keys [class validated? env] :as ast}]
  (if-not validated?
    (if-let [the-class (u/maybe-class class)]
      (let [{:keys [ns namespaces]} env
            class-name (.getName the-class)
            class-sym (-> class-name (subs (inc (.lastIndexOf class-name "."))) symbol)
            sym-val (get-in @namespaces [ns :mappings class-sym])]
        (if (and sym-val (not= (.getName ^Class sym-val) class-name)) ;; allow deftype redef
          (throw (ex-info (str class-sym " already refers to: " sym-val
                               " in namespace: " ns)
                          {:class     class
                           :class-sym class-sym
                           :sym-val   sym-val}))
          (do
            (swap! namespaces assoc-in
                   [ns :mappings class-sym] the-class)
            (assoc ast :class the-class
                   :validated? true))))
      (throw (ex-info (str "class not found: " class)
                      {:class class})))
    ast))

(defn validate-tag [tag]
  (if-let [the-class (u/maybe-class tag)]
    the-class
    (throw (ex-info (str "class not found: " tag)
                    {:class tag}))))

(defmethod -validate :def
  [{:keys [var init] :as ast}]
  (when-let [tag (:tag init)]
    #_(alter-meta! var assoc :tag tag))
  (when-let [arglists (:arglists init)]
    (doseq [arglist arglists]
      (when-let [tag (:tag (meta arglist))]
        (validate-tag tag)))
    #_(alter-meta! var assoc :arg-lists arglists))
  ast)

(defmethod -validate :invoke
  [{:keys [args tag env fn form] :as ast}]
  (let [argc (count args)]
    (when (and (= :const (:op fn))
               (not (instance? IFn (:form fn))))
      (throw (ex-info (str (class (:form fn)) " is not a function, but it's used as such")
                      {:form form})))
    (if (and (:arglists fn)
             (not (arglist-for-arity fn argc)))
      (assoc ast :maybe-mismatch-arity true)
      #_(throw (ex-info (str "No matching arity found for function: " (:name fn))
                        {:arity (count args)
                         :fn    fn}))
      ast)))

(defn validate-interfaces [interfaces]
  (when-not (every? #(.isInterface ^Class %) (disj interfaces Object))
    (throw (ex-info "only interfaces or Object can be implemented by deftype/reify"
                    {:interfaces interfaces}))))

(defmethod -validate :deftype
  [{:keys [name class-name fields interfaces] :as ast}]
  (validate-interfaces interfaces)
  (assoc ast :class-name (u/maybe-class class-name)))

(defmethod -validate :reify
  [{:keys [interfaces class-name] :as ast}]
  (validate-interfaces interfaces)
  (assoc ast :class-name (u/maybe-class class-name)))

(defmethod -validate :method
  [{:keys [name class interfaces methods tag params fixed-arity] :as ast}]
  (if interfaces
    (let [tags (mapv :tag params)
          methods-set (set (mapv (fn [x] (dissoc x :declaring-class :flags)) methods))]
      (let [[m & rest :as matches] (try-best-match tags methods)]
        (if m
          (let [ret-tag  (u/maybe-class (:return-type m))
                i-tag    (u/maybe-class (:declaring-class m))
                arg-tags (mapv u/maybe-class (:parameter-types m))
                params   (mapv (fn [arg tag] (assoc arg :tag tag)) params arg-tags)]
            (if (or (empty? rest)
                    (every? (fn [{:keys [return-type parameter-types]}]
                         (and (= (u/maybe-class return-type) ret-tag)
                              (= arg-tags (mapv u/maybe-class parameter-types)))) rest))
              (assoc (dissoc ast :interfaces :methods)
                :bridges   (filter (fn [{:keys [return-type]}]
                                     (.isAssignableFrom (u/maybe-class return-type)
                                                        ret-tag))
                                   (disj methods-set
                                         (dissoc m :declaring-class :flags)))
                :methods   methods
                :interface i-tag
                :tag       ret-tag
                :params    params)
              (throw (ex-info (str "ambiguous method signature for method: " name)
                              {:method     name
                               :interfaces interfaces
                               :params     params
                               :matches    matches}))))
          (throw (ex-info (str "no such method found: " name " with given signature in any of the"
                               " provided interfaces: " interfaces)
                          {:method     name
                           :methods    methods
                           :interfaces interfaces
                           :params     params})))))
    ast))

(defmethod -validate :default [ast] ast)

(defn validate
  "Validate tags, classes, method calls.
   Throws exceptions when invalid forms are encountered, replaces
   class symbols with class objects."
  [{:keys [tag ret-tag bind-tag return-tag] :as ast}]
  (let [ast (merge ast
                   (when tag
                     {:tag (validate-tag tag)})
                   (when ret-tag
                     {:ret-tag (validate-tag ret-tag)})
                   (when return-tag
                     {:return-tag (validate-tag return-tag)})
                   (when bind-tag
                     {:bind-tag (validate-tag bind-tag)}))]
    (-validate ast)))
