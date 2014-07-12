{:namespaces
 ({:source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm/clojure.tools.analyzer.jvm-api.html",
   :name "clojure.tools.analyzer.jvm",
   :doc
   "Analyzer for clojure code, extends tools.analyzer with JVM specific passes/forms"}
  {:source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm/clojure.tools.analyzer.jvm.utils-api.html",
   :name "clojure.tools.analyzer.jvm.utils",
   :doc nil}),
 :vars
 ({:arglists ([e]),
   :name "->ExceptionThrown",
   :namespace "clojure.tools.analyzer.jvm",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj#L481",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm/->ExceptionThrown",
   :doc
   "Positional factory function for class clojure.tools.analyzer.jvm.ExceptionThrown.",
   :var-type "function",
   :line 481,
   :file "src/main/clojure/clojure/tools/analyzer/jvm.clj"}
  {:arglists ([form] [form env] [form env opts]),
   :name "analyze",
   :namespace "clojure.tools.analyzer.jvm",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj#L450",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm/analyze",
   :doc
   "Returns an AST for the form that's compatible with what tools.emitter.jvm requires.\n\nBinds tools.analyzer/{macroexpand-1,create-var,parse} to\ntools.analyzer.jvm/{macroexpand-1,create-var,parse} and calls\ntools.analyzer/analyzer on form.\n\nIf provided, opts should be a map of options to analyze, currently the only valid option\nis :bindings.\nIf provided, :bindings should be a map of Var->value pairs that will be merged into the\ndefault bindings for tools.analyzer, useful to provide custom extension points.\n\nE.g.\n(analyze form env {:bindings  {#'ana/macroexpand-1 my-mexpand-1}})\n\nCalls `run-passes` on the AST.",
   :var-type "function",
   :line 450,
   :file "src/main/clojure/clojure/tools/analyzer/jvm.clj"}
  {:arglists ([form] [form env] [form env opts]),
   :name "analyze'",
   :namespace "clojure.tools.analyzer.jvm",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj#L541",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm/analyze'",
   :doc "Like `analyze` but runs cleanup on the AST",
   :var-type "function",
   :line 541,
   :file "src/main/clojure/clojure/tools/analyzer/jvm.clj"}
  {:arglists ([form] [form env] [form env opts]),
   :name "analyze+eval",
   :namespace "clojure.tools.analyzer.jvm",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj#L494",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm/analyze+eval",
   :doc
   "Like analyze but evals the form after the analysis and attaches the\nreturned value in the :result field of the AST node.\nIf evaluating the form will cause an exception to be thrown, the exception\nwill be caught and the :result field will hold an ExceptionThrown instance\nwith the exception in the \"e\" field.\n\nUseful when analyzing whole files/namespaces.",
   :var-type "function",
   :line 494,
   :file "src/main/clojure/clojure/tools/analyzer/jvm.clj"}
  {:arglists ([form] [form env] [form env opts]),
   :name "analyze+eval'",
   :namespace "clojure.tools.analyzer.jvm",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj#L548",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm/analyze+eval'",
   :doc "Like `analyze+eval` but runs cleanup on the AST",
   :var-type "function",
   :line 548,
   :file "src/main/clojure/clojure/tools/analyzer/jvm.clj"}
  {:arglists ([s]),
   :name "butlast+last",
   :namespace "clojure.tools.analyzer.jvm",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj#L483",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm/butlast+last",
   :doc
   "Returns same value as (juxt butlast last), but slightly more\nefficient since it only traverses the input sequence s once, not\ntwice.",
   :var-type "function",
   :line 483,
   :file "src/main/clojure/clojure/tools/analyzer/jvm.clj"}
  {:arglists ([sym {:keys [ns]}]),
   :name "create-var",
   :namespace "clojure.tools.analyzer.jvm",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj#L177",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm/create-var",
   :doc
   "Creates a Var for sym and returns it.\nThe Var gets interned in the env namespace.",
   :var-type "function",
   :line 177,
   :file "src/main/clojure/clojure/tools/analyzer/jvm.clj"}
  {:arglists ([]),
   :name "empty-env",
   :namespace "clojure.tools.analyzer.jvm",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj#L82",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm/empty-env",
   :doc "Returns an empty env map",
   :var-type "function",
   :line 82,
   :file "src/main/clojure/clojure/tools/analyzer/jvm.clj"}
  {:arglists ([form] [form env]),
   :name "macroexpand-1",
   :namespace "clojure.tools.analyzer.jvm",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj#L135",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm/macroexpand-1",
   :doc
   "If form represents a macro form or an inlineable function,\nreturns its expansion, else returns form.",
   :var-type "function",
   :line 135,
   :file "src/main/clojure/clojure/tools/analyzer/jvm.clj"}
  {:file "src/main/clojure/clojure/tools/analyzer/jvm.clj",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj#L60",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm/parse",
   :namespace "clojure.tools.analyzer.jvm",
   :line 60,
   :var-type "multimethod",
   :doc "Extension to tools.analyzer/-parse for JVM special forms",
   :name "parse"}
  {:arglists ([ast]),
   :name "run-passes",
   :namespace "clojure.tools.analyzer.jvm",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj#L376",
   :dynamic true,
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm/run-passes",
   :doc
   "Applies the following passes in the correct order to the AST:\n* uniquify\n* add-binding-atom\n* cleanup\n* source-info\n* elide-meta\n* warn-earmuff\n* collect\n* jvm.box\n* jvm.constant-lifter\n* jvm.annotate-branch\n* jvm.annotate-loops\n* jvm.annotate-class-id\n* jvm.annotate-internal-name\n* jvm.annotate-methods\n* jvm.fix-case-test\n* jvm.clear-locals\n* jvm.classify-invoke\n* jvm.validate\n* jvm.infer-tag\n* jvm.annotate-tag\n* jvm.validate-loop-locals\n* jvm.analyze-host-expr",
   :var-type "function",
   :line 376,
   :file "src/main/clojure/clojure/tools/analyzer/jvm.clj"}
  {:file "src/main/clojure/clojure/tools/analyzer/jvm.clj",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/2a88256e59731ad7cd1252f1cc4756e0fb7f6337/src/main/clojure/clojure/tools/analyzer/jvm.clj#L55",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm/specials",
   :namespace "clojure.tools.analyzer.jvm",
   :line 55,
   :var-type "var",
   :doc "Set of the special forms for clojure in the JVM",
   :name "specials"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm/ExceptionThrown",
   :namespace "clojure.tools.analyzer.jvm",
   :var-type "type",
   :name "ExceptionThrown"}
  {:arglists ([c]),
   :name "box",
   :namespace "clojure.tools.analyzer.jvm.utils",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L115",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
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
   "https://github.com/clojure/tools.analyzer.jvm/blob/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L161",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm.utils/convertible?",
   :doc "Returns true if it's possible to convert from c1 to c2",
   :var-type "function",
   :line 161,
   :file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj"}
  {:file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L51",
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
   "https://github.com/clojure/tools.analyzer.jvm/blob/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L145",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
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
   "https://github.com/clojure/tools.analyzer.jvm/blob/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L299",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm.utils/prim-or-obj",
   :doc
   "If the given Class is a primitive, returns that Class, otherwise returns Object",
   :var-type "function",
   :line 299,
   :file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj"}
  {:file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L97",
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
   "https://github.com/clojure/tools.analyzer.jvm/blob/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L151",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
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
   "https://github.com/clojure/tools.analyzer.jvm/blob/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L314",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
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
   "https://github.com/clojure/tools.analyzer.jvm/blob/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L130",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
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
   "https://github.com/clojure/tools.analyzer.jvm/blob/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L184",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
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
   "https://github.com/clojure/tools.analyzer.jvm/blob/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L213",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
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
   "https://github.com/clojure/tools.analyzer.jvm/blob/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L191",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm.utils/wider-tag*",
   :doc "Given two Classes returns the wider one",
   :var-type "function",
   :line 191,
   :file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj"}
  {:file "src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :raw-source-url
   "https://github.com/clojure/tools.analyzer.jvm/raw/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj",
   :source-url
   "https://github.com/clojure/tools.analyzer.jvm/blob/829c996198ecbc7023e8bd3f0757e830cd6b475e/src/main/clojure/clojure/tools/analyzer/jvm/utils.clj#L174",
   :wiki-url
   "http://clojure.github.com/tools.analyzer.jvm//clojure.tools.analyzer.jvm-api.html#clojure.tools.analyzer.jvm.utils/wider-than",
   :namespace "clojure.tools.analyzer.jvm.utils",
   :line 174,
   :var-type "var",
   :doc
   "If the argument is a numeric primitive Class, returns a set of primitive Classes\nthat are narrower than the given one",
   :name "wider-than"})}
