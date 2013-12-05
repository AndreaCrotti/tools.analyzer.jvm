;;   Copyright (c) Nicola Mometto, Rich Hickey & contributors.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;;   which can be found in the file epl-v10.html at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns clojure.tools.analyzer.passes.jvm.annotate-tag
  (:require [clojure.tools.analyzer.jvm.utils :refer [unbox maybe-class]]
            [clojure.tools.analyzer.passes :refer [prewalk]]
            [clojure.tools.analyzer.utils :refer [update!]])
  (:import (clojure.lang IPersistentMap IPersistentSet ISeq Var ArraySeq
                         PersistentTreeMap PersistentTreeSet PersistentVector)))

(defmulti -annotate-literal-tag
  "If the AST node type is a constant object, attach a :tag field to the
   node representing the form type."
  :op)

(defmethod -annotate-literal-tag :default
  [{:keys [op form] :as ast}]
  (if (= :const op)
    (assoc ast :tag (class form))
    ast))

(defmethod -annotate-literal-tag :map
  [{:keys [form] :as ast}]
  (if (sorted? form)
    (assoc ast :tag PersistentTreeMap)
    (assoc ast :tag IPersistentMap)))

(defmethod -annotate-literal-tag :set
  [{:keys [form] :as ast}]
  (if (sorted? form)
    (assoc ast :tag PersistentTreeSet)
    (assoc ast :tag IPersistentSet)))

(defmethod -annotate-literal-tag :vector
  [ast]
  (assoc ast :tag PersistentVector))

(defmethod -annotate-literal-tag :seq
  [ast]
  (assoc ast :tag ISeq))

;; char and numbers are unboxed by default

(defmethod -annotate-literal-tag :number
  [{:keys [form] :as ast}]
  (assoc ast :tag (unbox (class form))))

(defmethod -annotate-literal-tag :char
  [ast]
  (assoc ast :tag Character/TYPE))

(defmethod -annotate-literal-tag :the-var
  [ast]
  (assoc ast :tag Var))

(defmethod -annotate-literal-tag :const
  [{:keys [op type form] :as ast}]
  ((get-method -annotate-literal-tag type) ast))

(defmethod -annotate-literal-tag :quote
  [{:keys [expr] :as ast}]
  (let [{:keys [tag]} (-annotate-literal-tag expr)]
    (assoc ast :tag tag)))

;; postwalk
(defn annotate-literal-tag
  [{:keys [form tag] :as ast}]
  (if-let [tag (or tag (:tag (meta form)))]
    (assoc ast :tag tag)
    (-annotate-literal-tag ast)))

(defmulti annotate-binding-tag
  "Assocs the correct tag to local bindings, needs to be run after
   annotate-literal-tag and add-binding-atom, in conjuction with infer-tag"
  :op)

(defmethod annotate-binding-tag :default [ast] ast)

(defmethod annotate-binding-tag :binding
  [{:keys [form init local name atom variadic?] :as ast}]
  (if-let [tag (and (not (:case-test @atom))
                    (maybe-class (:tag (meta form))))] ;;explicit tag first
    (let [ast (assoc ast :tag tag)]
      (swap! atom assoc :tag tag)
      (if init
        (assoc ast :init (assoc init :tag tag))
        ast))
    (if-let [tag (maybe-class
                  (or (and init (:tag init))
                      (and (= :fn local) clojure.lang.AFunction)
                      (and (= :arg local)
                           (and variadic? clojure.lang.ArraySeq))))] ;;?
      (do (swap! atom assoc :tag tag)
          (assoc ast :tag tag))
      ast)))

(defmethod annotate-binding-tag :local
  [{:keys [name form tag atom case-test] :as ast}]
  (if-let [tag (or tag
                   (and (not case-test)
                        (:tag (meta form))) ;;explicit tag first
                   (@atom :tag))]
    (assoc ast :tag tag)
    ast))
