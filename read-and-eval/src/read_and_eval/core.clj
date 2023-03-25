(ns read-and-eval.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(read-string "(1 + 1)")

(let [infix (read-string "(1 + 1)")]
  (list (second infix) (first infix) (last infix)))

(eval (let [infix (read-string "(1 + 1)")]
        (list (second infix) (first infix) (last infix))))

(defmacro ignore-last-operand
  [function-call]
  (println (str "function-call <"
                (type function-call)
                "> is: "
                function-call))
  (butlast function-call))

(ignore-last-operand (+ 1 2 10))
