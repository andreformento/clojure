(ns clojure-noob.02-core-functions-in-depth)

(defn titleize
  [topic]
  (str topic " blah"))

(map #(titleize (second %)) {:a "AA" :b "BBB"})
; => ("AA blah" "BBB blah")

(map str ["a" "b" "c" "d"] ["A" "B" "C" "1"])
; => ("aA" "bB" "cC" "d1")

(def human-consumption   [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])
(defn unify-diet-data
  [human critter]
  {:human human
   :critter critter})

(map unify-diet-data human-consumption critter-consumption)
; => ({:human 8.1, :critter 0.0}
; =>  {:human 7.3, :critter 0.2}
; =>  {:human 6.6, :critter 0.3}
; =>  {:human 5.0, :critter 1.1})

(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))
(stats [3 4 10])
; => (17 3 17/3)
(stats [80 1 44 13 6])
; => (144 5 144/5)

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-man" :real "Peter Parker"}])
(map :real identities)

(reduce (fn [new-map [key val]]
          (assoc new-map key (inc val)))
        {}
        {:max 30 :min 10})

