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

(defmacro infix
  "this is a list -> [1 + 2]"
  [infixed]
  (let [op (second infixed)
        x (first infixed)
        y (last infixed)
        result (list op x y)]
    (println (str "result = " result " -> op=" op "; x="x "; y="y))
    result))
(infix (1 + 2))
; 3


(defmacro resolve-expression
  [[x1 f-plus x2 f-* x3 f-minus x4]]
  ;(infix ((infix (x1 f-plus (infix (x2 f-* x3)))) f-minus x4))
  (println "x2" x2)
  (println "x3" x3)
  (println "re" (f-* 3 4))
  (infix (x2 f-* x3)))

(println (eval (resolve-expression (1 + 3 * 4 - 5))))
; 8
