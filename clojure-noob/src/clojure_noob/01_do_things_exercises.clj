(ns clojure-noob.01-do-things-exercises)

(defn takes-a-number-and-adds-100
  [x]
  (+ x 100))
(takes-a-number-and-adds-100 12)

(defn inc-maker
  [inc-by]
  #(+ % inc-by))
(def inc3 (inc-maker 3))
(inc3 7)

(defn dec-maker
  [dec-by]
  #(- % dec-by))
(def dec9 (dec-maker 9))
(dec9 10)

(defn calculator
  [operation]
  #(operation %1 %2))
(def inc-numbers
  (calculator +))
(def dec-numbers
  (calculator -))
(inc-numbers 3 4)
(dec-numbers 7 5)

(defn dec-maker
  [x]
  (- x 9))

(defn mapset
  [operation values]
  (let [unique-values (set values)
        calculated-values (map operation unique-values)]
    (set calculated-values)))
(mapset inc [1 1 2 2])
; => #{3 2}

