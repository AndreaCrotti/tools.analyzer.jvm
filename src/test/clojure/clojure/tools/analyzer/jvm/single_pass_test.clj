(ns clojure.tools.analyzer.jvm.single-pass-test
  (:require [clojure.tools.analyzer.jvm.single-pass :as si :refer [ast]]
            [clojure.tools.analyzer.jvm :as ana.jvm]
            [clojure.tools.analyzer.passes.jvm.emit-form :refer [emit-form]]
            [clojure.tools.analyzer.passes :refer [schedule]]
            [clojure.tools.analyzer.passes.trim :refer [trim]]
            [clojure.data :refer [diff]]
            [clojure.test :refer [deftest is]]
            [clojure.pprint :refer [pprint]]
            [clojure.set :as set]))

(defn leaf-keys [m]
  (cond
    (map? m)
    (reduce (fn [ks [k v]]
              (cond
                (map? v) (set/union ks (leaf-keys v))

                (and (vector? v)
                     (every? map? v))
                (apply set/union ks (map leaf-keys v))

                :else (conj ks k)))
            #{}
            m)
    (coll? m) (apply set/union (map leaf-keys m))
    :else #{}))

(defn leaf-diff [x y]
  (apply set/union
         (map leaf-keys
              (take 2
                    (diff x y)))))

(def passes (schedule (disj ana.jvm/default-passes #'trim)))

(defmacro taj [form]
  `(binding [ana.jvm/run-passes passes]
     (ana.jvm/analyze '~form)))

(defn ppdiff [x y]
  (pprint (diff x y)))

(deftest KeywordExpr-test
  (is (= (ast :abc)
         (taj :abc)
         #_{:val :abc, 
          :tag clojure.lang.Keyword
          :o-tag clojure.lang.Keyword
          :form :abc
          :type :keyword, 
          :op :const, 
          :top-level true
          :env {:context :ctx/expr
                :ns 'clojure.tools.analyzer.jvm.single-pass-test,
                :file "clojure/tools/analyzer/jvm/single_pass_test.clj",
                :locals {}},
          :literal? true})))

(deftest NumberExpr-test
  (is (= (ast 1.2)
         (taj 1.2)
         #_{:op :const, 
          :tag Double/TYPE
          :o-tag Double/TYPE
          :env {:context :ctx/expr, 
                :locals {}, 
                :ns 'clojure.tools.analyzer.jvm.single-pass-test
                :file "clojure/tools/analyzer/jvm/single_pass_test.clj"}, 
          :type :number, 
          :literal? true, 
          :val 1.2, 
          :form 1.2, 
          :top-level true}))
  (is (= (ast 1)
         (taj 1)))
  )

(deftest StringExpr-test
  (is (= (ast "abc")
         (taj "abc")
         #_{:op :const, 
          :tag String
          :o-tag String
          :env {:context :ctx/expr, 
                :locals {}, 
                :ns 'clojure.tools.analyzer.jvm.single-pass-test
                :file "clojure/tools/analyzer/jvm/single_pass_test.clj"}, 
          :type :string, 
          :literal? true, 
          :val "abc", 
          :form "abc", 
          :top-level true})))

(deftest NilExpr-test
  (is (= (ast nil)
         (taj nil)
         #_'{:op :const, 
           :tag nil
           :o-tag nil
           :env {:context :ctx/expr, 
                 :locals {}, 
                 :ns clojure.tools.analyzer.jvm.single-pass-test
                 :file "clojure/tools/analyzer/jvm/single_pass_test.clj"}, 
           :type :nil, :literal? true, :val nil, :form nil, :top-level true})))

(deftest BooleanExpr-test
  (is (= #{:tag :o-tag}
         (leaf-diff
           (ast true)
           (taj true))
         #_{:op :const, 
          :tag Boolean
          :o-tag Boolean
          :env {:context :ctx/expr, 
                :locals {}, 
                :ns 'clojure.tools.analyzer.jvm.single-pass-test
                :file "clojure/tools/analyzer/jvm/single_pass_test.clj"}, 
          :type :bool, :literal? true, :val true, :form true, :top-level true}))
  (is (= #{:tag :o-tag}
         (leaf-diff
           (ast false)
           (taj false)))))


(deftest ConstantExpr-test
  (is (= (ast 'sym)
         (taj 'sym)
         #_{:op :quote
          :tag clojure.lang.Symbol
          :literal? true
          :top-level true
          :o-tag clojure.lang.Symbol
          :env {:context :ctx/expr, 
                :locals {}, 
                :ns 'clojure.tools.analyzer.jvm.single-pass-test
                :file "clojure/tools/analyzer/jvm/single_pass_test.clj"}, 
          :form '(quote sym)
          :expr
          {:val 'sym, 
           :tag clojure.lang.Symbol
           :o-tag clojure.lang.Symbol
           :form 'sym
           :type :symbol,
           :op :const, 
           :env {:context :ctx/expr, 
                 :locals {}, 
                 :ns 'clojure.tools.analyzer.jvm.single-pass-test
                 :file "clojure/tools/analyzer/jvm/single_pass_test.clj"}, 
           :literal? true}
          :children [:expr]}))
  ;; FIXME 'nil doesn't do what you'd expect
  #_(is (= (ast 'nil)
         (taj 'nil)))
  ;; FIXME '1 doesn't do what you'd expect
  #_(is (= (ast '1)
         (taj '1)))
  ;; (not= #"" #""), so :val and :form will not match
  (is (let [[l] (diff (ast '#"")
                      (taj '#""))]
        (= #{:val :form}
           (leaf-keys l))))
  ;; Difference: tag for Compiler is APersistentMap, but t.a.j is PersistentArrayMap
  (is (= (taj '{:a 1})
         (ast '{:a 1})))
  ;;FIXME
  #_(is (= (taj '"")
         (ast '"")))
  ;;FIXME
  #_(is (= (ast 1N)
         (taj 1N)))
  (is (= (ast '1N)
         (taj '1N)))
  )


(deftest DefExpr-test
  ;; FIXME :tag is different
  (is (= #{:line :tag}
         (leaf-diff (taj (def a 1))
                    (ast (def a 1)))))
  ;; FIXME :doc is not a node
  #_(is (= (ast (def a "foo" 1))
         (taj (def a "foo" 1)))))

(deftest BodyExpr-test
  ;; Compiler prints (do nil) instead of (do).
  (is (= #{:form :line}
         (leaf-diff
           (ast (do))
           (taj (do)))))
  (is (= #{:line}
         (leaf-diff
           (ast (do 1))
           (taj (do 1)))))
  ;; inner column is wrong since Compiler's BodyExpr does not remember it
  (is (= #{:line :column}
         (leaf-diff
           (ast (do (do 1)))
           (taj (do (do 1)))))))

(deftest FnExpr-test
  (is (=
       #{:loop-id :o-tag :line :column :form :tag :arglists :top-level}
       (leaf-diff
         ;; taj is wrapped in implicit do?
         (ast (fn []))
         (:ret (taj (fn []))))))
  (is (=
       ;; :children is always empty
       #{:children :loop-id :arglist :o-tag :column :line :top-level :form :tag :arglists :atom}
       (leaf-diff
         (ast (fn [a]))
         (:ret (taj (fn [a]))))))
  (is (=
       ;; :children is always empty
       #{:children :loop-id :o-tag :line :tag :atom :assignable?}
       (ppdiff
         (-> (ast (fn [a] a)) :methods first :body :ret)
         (-> (taj (fn [a] a)) :ret :methods first :body :ret))))
  )

(deftest InvokeExpr-test
  (is (=
       #{:body? :loop-id :o-tag :column :line :form :tag :arglists :raw-forms}
       (leaf-diff
         (ast ((do (fn []))))
         (taj ((fn []))))))
  (is (=
       #{:o-tag :column :line :tag}
       (leaf-diff
         (ast (:a nil))
         (taj (:a nil)))))
  (is (=
       #{:o-tag :column :line :tag}
       (leaf-diff
         (ast (:a nil 1))
         (taj (:a nil 1))))))

(deftest LetExpr-test
  (is (= #{:loop-locals :loop-id :file :o-tag :column :line :once :context :tag :atom :assignable?}
         (leaf-diff
           (-> (ast (let [a 1] a)) :fn :methods first :body :ret :body :ret)
           (-> (taj (let [a 1] a)) :body :ret))))
  (is (= #{:loop-locals :loop-id :file :o-tag :column :line :once :top-level :context :form 
           :tag :atom :assignable? :raw-forms}
         (leaf-diff
           (-> (ast (let [a 1] a)) :fn :methods first :body :ret)
           (-> (taj (let [a 1] a)))))))

(deftest NewExpr-test
  (is 
    (= #{:line :form :raw-forms}
       (leaf-diff
         (ast (Exception.))
         (taj (Exception.)))))
  (is 
    (= #{:line :raw-forms}
       (leaf-diff
         ;; fully qualified :form isn't different
         (ast (java.io.File. "a"))
         (taj (java.io.File. "a"))))))

(deftest VarExpr-test
  (is (= #{:tag :o-tag}
         (leaf-diff
           (ast +)
           (taj +)))))

(deftest TheVarExpr-test
  (is (= (ast #'+)
         (taj #'+))))

(deftest InstanceOfExpr-test
  (is (= 
        #{:column :line :form}
        (leaf-diff
          (ast (instance? Object 1))
          (taj (instance? Object 1))))))

(deftest EmptyExpr-test
  (is (= (ast {})
         (taj {})))
  (is (= (ast [])
         (taj [])))
  (is (= (ast #{})
         (taj #{})))
  ;; TODO annotate-tag's logic for ISeq
  (is (= #{:tag :o-tag}
         (leaf-diff 
           (ast ())
           (taj ())))))

(deftest MetaExpr-test
  (is (= 
        (:op (ast ^:a #()))
        (:op (taj ^:a #())))))

(deftest IfExpr-test
  (is 
    (= #{:o-tag :line :tag}
       (leaf-diff
         (ast (if 1 2 3))
         (taj (if 1 2 3))))))

(deftest StaticMethodExpr-test
  (is (=
       #{:o-tag :line :tag :raw-forms}
       (leaf-diff
         (ast (Long/valueOf "1"))
         (taj (Long/valueOf "1"))))))

(deftest InstanceMethodExpr-test
  (is (=
       ;; constructors inherit :line and :column
       #{:column :line :raw-forms}
       (leaf-diff
         (-> (ast (.getName (java.io.File. "a"))) :instance)
         (-> (taj (.getName (java.io.File. "a"))) :instance)))))

(deftest SetExpr-test
  (is (=
       #{:o-tag :column :line :tag}
       (leaf-diff
         (ast #{(if 1 2 3)})
         (taj #{(if 1 2 3)})))))

(deftest VectorExpr-test
  (is (=
       #{:o-tag :column :line :tag}
       (leaf-diff
         (ast [(if 1 2 3)])
         (taj [(if 1 2 3)])))))

(deftest MapExpr-test
  (is (=
       #{:o-tag :column :line :tag}
       (leaf-diff
         (ast {'a (if 'a 'b 'c)})
         (taj {'a (if 'a 'b 'c)})))))

(deftest MonitorEnter-ExitExpr-test
  (is 
    (= #{:line :top-level}
       (leaf-diff
         (ast (monitor-enter 1))
         (taj (monitor-enter 1)))))
  (is 
    (= #{:line :top-level}
       (leaf-diff
         (ast (monitor-exit 1))
         (taj (monitor-exit 1))))))

(deftest ThrowExpr-Test
  (is (=
       #{:loop-locals :loop-id :ignore-tag :column :line :once :top-level :context :form :raw-forms}
       (leaf-diff
         (-> (ast (throw (Exception.))) :fn :methods first :body :ret)
         (taj (throw (Exception.)))))))

(deftest ImportExpr-test
  (is (= 
        #{:line :tag :validated? :raw-forms}
        (leaf-diff
          (ast (import 'java.lang.Object))
          (taj (import 'java.lang.Object))))))

(deftest NewInstanceExpr-test
  (is (=
       #{:loop-locals :loop-id :line :once :context :class-name :form :tag}
        (leaf-diff
          (-> (ast (deftype A [])) :fn :methods first :body :ret :body :statements first)
          (-> (taj (deftype A [])) :body :statements first))))
  (is (=
        #{:loop-locals :loop-id :o-tag :line :once :context :class-name :form :tag :atom}
        (leaf-diff
          (-> (ast (deftype A [f])) :fn :methods first :body :ret :body :statements first)
          (-> (taj (deftype A [f])) :body :statements first)))))

(defprotocol Foo
  (bar [this a]))

#_(require '[clojure.tools.trace :as tr])
#_(tr/untrace-vars clojure.tools.analyzer.passes.uniquify/-uniquify-locals)
#_(tr/untrace-vars clojure.tools.analyzer.passes.uniquify/uniquify-locals*)

(deftest NewInstanceMethod-test
  ; :this
  (is 
    ;; :children is nil and absent
    (= #{:loop-locals :children :this :loop-id :o-tag :line :once :context :tag :atom}
       (leaf-diff
         (-> (ast (deftype A []
                    Foo
                    (bar [this a])))
             :fn :methods first :body :ret :body :statements first :methods first :this)
         (-> (taj (deftype A []
                    Foo
                    (bar [this a])))
             :body :statements first :methods first :this))))
  (is 
    (= #{:loop-id :o-tag :line :once :form :tag :atom}
       (leaf-diff
         (-> (ast (deftype A []
                    Foo
                    (bar [this a])))
             :fn :methods first :body :ret :body :statements first :methods first :body)
         (-> (taj (deftype A []
                    Foo
                    (bar [this a])))
             :body :statements first :methods first :body))))
  (is 
    (= #{:loop-locals :children :loop-id :o-tag :line :once :context :tag :atom}
       (leaf-diff
         (-> (ast (deftype A []
                    Foo
                    (bar [this a])))
             :fn :methods first :body :ret :body :statements first :methods first :params first)
         (-> (taj (deftype A []
                    Foo
                    (bar [this a])))
             :body :statements first :methods first :params first))))
  (is 
    (= #{:loop-locals :children :interface :this :locals :ns :loop-id :name :file 
         :op :o-tag :column :methods :line :once :context :form :tag :atom :local}
       (leaf-diff
         (-> (ast (deftype A []
                    Foo
                    (bar [this a])))
             :fn :methods first :body :ret :body :statements first :methods first)
         (-> (taj (deftype A []
                    Foo
                    (bar [this a])))
             :body :statements first :methods first))))
  (is (=
        (-> (ast (deftype A []
                   Foo
                   (bar [this a])))
            :fn :methods first :body :ret :body :statements first emit-form)
        (-> (taj (deftype A []
                   Foo
                   (bar [this a])))
            :body :statements first emit-form))))

(deftest CaseExpr-test
  (is 
    (= #{:loop-locals :children :ns :loop-id :name :file :val :type :op :o-tag :literal? 
         :column :line :once :top-level :context :form :tag :atom :local :assignable?}
       (leaf-diff
         (-> (ast (case 1 2 3)) :fn :methods first :body :ret :body :ret :test)
         (-> (taj (case 1 2 3)) :body :ret :test))))
  ;shift
  (is
    (= (-> (ast (case 1 2 3)) :fn :methods first :body :ret :body :ret :shift)
       (-> (taj (case 1 2 3)) :body :ret :shift)))
  ;mask
  (is
    (= (-> (ast (case 1 2 3)) :fn :methods first :body :ret :body :ret :mask)
       (-> (taj (case 1 2 3)) :body :ret :mask)))
  ;low
  (is
    (= (-> (ast (case 1 2 3)) :fn :methods first :body :ret :body :ret :low)
       (-> (taj (case 1 2 3)) :body :ret :low)))
  ;high
  (is
    (= (-> (ast (case 1 2 3)) :fn :methods first :body :ret :body :ret :high)
       (-> (taj (case 1 2 3)) :body :ret :high)))
  ;switch-type
  (is
    (= (-> (ast (case 1 2 3)) :fn :methods first :body :ret :body :ret :switch-type)
       (-> (taj (case 1 2 3)) :body :ret :switch-type)))
  ;test-type
  (is
    (= (-> (ast (case 1 2 3)) :fn :methods first :body :ret :body :ret :test-type)
       (-> (taj (case 1 2 3)) :body :ret :test-type)))
  ;skip-check?
  (is
    (= (-> (ast (case 1 2 3)) :fn :methods first :body :ret :body :ret :skip-check?)
       (-> (taj (case 1 2 3)) :body :ret :skip-check?)))
  ;;FIXME more tests for children
  )

(deftest AssignExpr-test
  (is 
    (= #{:o-tag :line :tag :arglists :assignable?}
       (leaf-diff
         (ast (set! *warn-on-reflection* true))
         (taj (set! *warn-on-reflection* true))))))

(deftest TryExpr-test
  ;; body
  (is 
    (= 
      #{:no-recur :loop-locals :loop-id :line :once :context :form}
      (leaf-diff
        (-> (ast (try)) :fn :methods first :body :ret 
            :body)
        (-> (taj (try)) 
            :body))))
  ;; empty catches
  (is 
    (= (-> (ast (try)) :fn :methods first :body :ret 
           :catches)
       (-> (taj (try)) 
           :catches)))
  (is 
    (= #{:no-recur :loop-locals :loop-id :o-tag :line :once :top-level :context :form}
       (leaf-diff
         (-> (ast (try)) :fn :methods first :body :ret)
         (-> (taj (try))))))
  (is 
    (= #{:no-recur :loop-locals :loop-id :o-tag :line :once :top-level :context :form}
       (leaf-diff
         (-> (ast (try (finally))) :fn :methods first :body :ret)
         (-> (taj (try (finally))))))))

(deftest CatchExpr-test
  (is 
    (= #{:loop-locals :ns :loop-id :file :column :line :once :context :tag :atom}
       (leaf-diff
         (-> (ast (try (catch Exception e))) :fn :methods first :body :ret
             :catches first :body :ret)
         (-> (taj (try (catch Exception e))) 
             :catches first :body :ret))))
  )

(deftest RecurExpr-test
  (is (= 
        #{}
        (leaf-diff
          (-> (ast (loop [] (recur))) :fn :methods first :body :ret)
          (-> (taj (loop [] (recur)))))))

(defmacro juxt-ast [f]
  `(do (time (si/analyze-one (ana.jvm/empty-env) '~f))
       (time (si/analyze-form '~f))
       (time (ast ~f))
       (time (taj ~f))
       nil))

#_(juxt-ast
  (defn foo [{:keys [a b c]}]
    [(inc a) (+ b c)]))

#_(juxt-ast
  (doseq [{:keys [a b c]} [1 2 3]
          {:keys [d e f]} [4 5 6]
          {:keys [d e f]} [4 5 6]
          {:keys [d e f]} [4 5 6]
          {:keys [d e f]} [4 5 6]
          :when a]
    (inc a)))

#_(do
  (time (ast (defn foo [{:keys [a b c]}]
               [(inc a) (+ b c)])))
  nil)
#_(do
  (time (taj (defn foo [{:keys [a b c]}]
               [(inc a) (+ b c)])))
  nil)
