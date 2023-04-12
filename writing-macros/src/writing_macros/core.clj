(ns writing-macros.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn new-line [] (println "------"))

(when (= "666" "666")
  (println "yes, is yes")
  (println "confirm my confirmation")
  (println "bias doesn't exist on my bubble"))

; yes, is yes
; confirm my confirmation
; bias doesn't exist on my bubble

(new-line)

(println
  (pr-str
    (macroexpand
      '(when (= "666" "666")
         (println "yes, is yes")
         (println "confirm my confirmation")
         (println "bias doesn't exist on my bubble")))))

; (if (= "666" "666")
;   (do (println "yes, is yes")
;       (println "confirm my confirmation")
;       (println "bias doesn't exist on my bubble")))

(defmacro infix
  "Use this macho when you pine for the notation of your childhood"
  [infixed]
  (list (second infixed)
        (first infixed)
        (last infixed)))

(new-line)

(println (infix (1 + 1)))
; 2
(println '(infix (1 + 1)))
; (infix (1 + 1))
(println (macroexpand '(infix (1 + 1))))
; (+ 1 1)

(defmacro infix-2
  [[operand1 op operand2]]
  (list op operand1 operand2))

(new-line)

(def expression (identity 666))
(let [result expression]
  (println result)
  result)

;(defmacro my-print-whoopsie
;  [expression]
;  (list let [result expression
;             (list println result)]
;        result))

(defmacro my-print
  [expression]
  (list 'let ['result expression]
        (list 'println 'result)
        'result))

