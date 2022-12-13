(ns clojure-noob.atoms-refs)

(def learn-atom (atom 0))

(println (+ @learn-atom 1))

(println learn-atom)

(println (swap! learn-atom inc))
(println (swap! learn-atom + 9))
(println (swap! learn-atom / 5))
