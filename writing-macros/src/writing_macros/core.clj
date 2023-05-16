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

(def order-details-validations
  {:name
   ["Please enter a name" not-empty]

   :email
   ["Please enter an email address" not-empty
    "Your email address doesn't look like an email address" #(or (empty? %) (re-seq #"@" %))]})

(defn error-messages-for
  "Return a seq of error messages"
  [to-validate message-validator-pairs]
  (map first (filter #(not ((second %) to-validate))
                     (partition 2 message-validator-pairs))))

(error-messages-for "" ["Please enter a name" not-empty])
; ("Please enter a name")

(defn validate
  "Returns a map with a vector of errors for each key"
  [to-validate validations]
  (reduce (fn [errors validation]
            (let [[field-name validation-check-groups] validation
                  value (get to-validate field-name)
                  error-messages (error-messages-for value validation-check-groups)]
              (if (empty? error-messages)
                errors
                (assoc errors field-name error-messages))))
          {}
          validations))

(def order-details
  {:name  "Mitchard Blimmons"
   :email "mitchard.blimmonsgmail.com"})
(validate order-details order-details-validations)

; {:email ("Your email address doesn't look like an email address")}

; 1) Validate a record and bind the result to errors
; 2) Check whether there were any errors
; 3) If there were, do the success thing, here (println :success)
; 4) Otherwise, do the failure thing, here (println :failure errors)

(let [errors (validate order-details order-details-validations)]
  (if (empty? errors)
    (println :success)
    (println :failure errors)))

(defmacro if-valid
  "Handle validation more concisely"
  [to-validate validations errors-name & then-else]
  `(let [~errors-name (validate ~to-validate ~validations)]
     (if (empty? ~errors-name)
       ~@then-else)))

