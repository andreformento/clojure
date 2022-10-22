(ns clojure-noob.02-core-functions-in-depth)

(defn titleize
  [topic]
  (str topic " blah"))

(map #(titleize (second %)) {:a "AA" :b "BBB"})
; => ("AA blah" "BBB blah")
