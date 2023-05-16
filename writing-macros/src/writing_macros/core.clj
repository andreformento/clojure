(ns writing-macros.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn new-line [what] (println (str "------ " what)))

(when (= "666" "666")
  (println "yes, is yes")
  (println "confirm my confirmation")
  (println "bias doesn't exist on my bubble"))

; yes, is yes
; confirm my confirmation
; bias doesn't exist on my bubble

(new-line "macroexpand")

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

(new-line "infix")

(println (infix (1 + 1)))
; 2
(println '(infix (1 + 1)))
; (infix (1 + 1))
(println (macroexpand '(infix (1 + 1))))
; (+ 1 1)

(defmacro infix-2
  [[operand1 op operand2]]
  (list op operand1 operand2))

(new-line "print and return")

(def expression (identity 666))
(let [result expression]
  (println result)
  result)

(when (test false) 1)

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

(list '+ 1 (inc 1))
; => (+ 1 2)

`(+ 1 ~(inc 1))
; => (clojure.core/+ 1 2)

(new-line "critic")

(defmacro code-critic-verbose
  "Phrases are courtesy Hermes Conrad from Futurama"
  [bad good]
  (list 'do
        (list 'println
              "Great squid of Madrid, this is bad code:"
              (list 'quote bad))
        (list 'println
              "Sweet gorilla of Manila, this is good code:"
              (list 'quote good))))

(defmacro code-critic-together
  "Phrases are courtesy Hermes Conrad from Futurama"
  [bad good]
  `(do (println "Great squid of Madrid, this is bad code:"
                (quote ~bad))
       (println "Sweet gorilla of Manila, this is good code:"
                (quote ~good))))

(defn criticize-code
  [criticism code]
  `(println ~criticism (quote ~code)))

(defmacro code-critic-refactor
  [bad good]
  `(do ~(criticize-code "Cursed bacteria of Liberia, this is bad code:" bad)
       ~(criticize-code "Sweet sacred boa of Western and Eastern Samoa, this is good code:" good)))

(defmacro code-critic
  [bad good]
  `(do ~@(map #(apply criticize-code %)
              [["Cursed bacteria of Liberia, this is bad code:" bad]
               ["Sweet sacred boa of Western and Eastern Samoa, this is good code:" good]])))

(code-critic (1 + 1) (+ 1 1))
; => Great squid of Madrid, this is bad code: (1 + 1)
; => Sweet gorilla of Manila, this is good code: (+ 1 1)

; Validation functions

