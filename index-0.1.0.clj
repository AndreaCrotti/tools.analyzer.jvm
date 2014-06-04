{:namespaces
 ({:source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/37679c74039507042050f88bb25eac362d595d00/src/main/clojure/clojure/tools/analyzer/jvm.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm/clojure.tools.analyzer.jvm-api.html",
   :name "clojure.tools.analyzer.jvm",
   :doc
   "Analyzer for clojure code, extends tools.analyzer with JVM specific passes/forms"}
  {:source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm/clojure.tools.analyzer.jvm.utils-api.html",
   :name "clojure.tools.analyzer.jvm.utils",
   :doc nil}),
 :vars
 ({:arglists ([c]),
   :name "box",
   :namespace "clojure.tools.analyzer.jvm.utils",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L115",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm.utils/box",
   :doc
   "If the argument is a primitive Class, returns its boxed equivalent,\notherwise returns the argument",
   :var-type "function",
   :line 115,
   :file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj"}
  {:arglists ([c1 c2]),
   :name "convertible?",
   :namespace "clojure.tools.analyzer.jvm.utils",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L161",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm.utils/convertible?",
   :doc "Returns true if it's possible to convert from c1 to c2",
   :var-type "function",
   :line 161,
   :file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj"}
  {:file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L51",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm.utils/maybe-class",
   :namespace "clojure.tools.analyzer.jvm.utils",
   :line 51,
   :var-type "var",
   :doc
   "Takes a Symbol, String or Class and tires to resolve to a matching Class",
   :name "maybe-class"}
  {:arglists ([c]),
   :name "numeric?",
   :namespace "clojure.tools.analyzer.jvm.utils",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L145",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm.utils/numeric?",
   :doc "Returns true if the given class is numeric",
   :var-type "function",
   :line 145,
   :file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj"}
  {:arglists ([tag]),
   :name "prim-or-obj",
   :namespace "clojure.tools.analyzer.jvm.utils",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L299",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm.utils/prim-or-obj",
   :doc
   "If the given Class is a primitive, returns that Class, otherwise returns Object",
   :var-type "function",
   :line 299,
   :file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj"}
  {:file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L97",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm.utils/primitive?",
   :namespace "clojure.tools.analyzer.jvm.utils",
   :line 97,
   :var-type "var",
   :doc
   "Returns non-nil if the argument represents a primitive Class other than Void",
   :name "primitive?"}
  {:arglists ([c1 c2]),
   :name "subsumes?",
   :namespace "clojure.tools.analyzer.jvm.utils",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L151",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm.utils/subsumes?",
   :doc "Returns true if c2 is subsumed by c1",
   :var-type "function",
   :line 151,
   :file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj"}
  {:arglists ([tags methods]),
   :name "try-best-match",
   :namespace "clojure.tools.analyzer.jvm.utils",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L314",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm.utils/try-best-match",
   :doc
   "Given a vector of arg tags and a collection of methods, tries to return the\nsubset of methods that match best the given tags",
   :var-type "function",
   :line 314,
   :file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj"}
  {:arglists ([c]),
   :name "unbox",
   :namespace "clojure.tools.analyzer.jvm.utils",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L130",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm.utils/unbox",
   :doc
   "If the argument is a Class with a primitive equivalent, returns that,\notherwise returns the argument",
   :var-type "function",
   :line 130,
   :file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj"}
  {:arglists ([from to]),
   :name "wider-primitive",
   :namespace "clojure.tools.analyzer.jvm.utils",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L184",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm.utils/wider-primitive",
   :doc "Given two numeric primitive Classes, returns the wider one",
   :var-type "function",
   :line 184,
   :file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj"}
  {:arglists ([tags]),
   :name "wider-tag",
   :namespace "clojure.tools.analyzer.jvm.utils",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L213",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm.utils/wider-tag",
   :doc "Given a collection of Classes returns the wider one",
   :var-type "function",
   :line 213,
   :file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj"}
  {:arglists ([from to]),
   :name "wider-tag*",
   :namespace "clojure.tools.analyzer.jvm.utils",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L191",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm.utils/wider-tag*",
   :doc "Given two Classes returns the wider one",
   :var-type "function",
   :line 191,
   :file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj"}
  {:file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/385d397fc02742f63a83fd4553246dd013c0457d/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L174",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm.utils/wider-than",
   :namespace "clojure.tools.analyzer.jvm.utils",
   :line 174,
   :var-type "var",
   :doc
   "If the argument is a numeric primitive Class, returns a set of primitive Classes\nthat are narrower than the given one",
   :name "wider-than"})}
