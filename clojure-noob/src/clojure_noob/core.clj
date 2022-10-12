(ns clojure-noob.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (if false
    (do (println "is true")
        "after true")
    (do (println "failure")
        "and after failure"))
  )

(when true
  (println "Success!")
  "abra cadabra")
; => Success!
; => "abra cadabra"

(or false nil :large_I_mean_venti :why_cant_I_just_say_large)
; => :large_I_mean_venti

(or (= 0 1) (= "yes" "no"))
; => false

(or nil)
; => nil

(defn error-message
  [severity]
  (str "OH GOD! IT'S A DISASTER! WE'RE "
       (if (= severity :mild)
         "MILDLY INCONVENIENCED!"
         "DOOOOOOOMED!")))

(error-message :mild)
; => "OH GOD! IT'S A DISASTER! WE'RE MILDLY INCONVENIENCED!"

; list
(nth '(:a :b :c) 0)
; => :a

; hash set
#{"kurt vonnegut" 20 :icicle}
(hash-set 1 1 2 2)
; => #{1 2}

; create a set from a list
(set [3 3 3 4 4])
; => #{3 4}

(defn chooser
  "many choices"
  [[first-choice, second-choice & other-choices]]
  (println (str "first: " first-choice))
  (println (str "second: " second-choice))
  (println (str "other choices: " (clojure.string/join ", " other-choices)))
  )

(defn announce-treasure-location
  ;[{lat :lat lng :lng}]
   [{:keys [lat lng]}]
  (println (str "Treasure lag: " lat))
  (println (str "Treasure lng: " lng))
  )

(defn steer-ship! [treasure-location]
  (println (str "go go go! " treasure-location))
  )

(defn receive-treasure-location
  [{:keys [lat lng] :as treasure-location}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng))

  (steer-ship! treasure-location)
  )

; (receive-treasure-location {:lat 1 :lng 2})
; Treasure lat: 1
; Treasure lng: 2
; go go go! {:lat 1, :lng 2}
; nil

(#(str %1 " and " %2 " and then... " %&) "first" "second" "blah" 4)

(defn inc-maker
  "Create a custom incrementator"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))

(inc3 7)
; => 10

(def dalmatian-list ["pongo" "Perdita" "Puppy 1" "Puppy 2"])
(let [[pongo & dalmatians] dalmatian-list]
  [pongo dalmatians])
; ["pongo" ("Perdita" "Puppy 1" "Puppy 2")]

; recursive
(loop [iteration 0]
  (println (str "Iteration " iteration))
  (if (> iteration 3)
    (println "Goodbye")
    (recur (inc iteration))))
; Iteration 0
; Iteration 1
; Iteration 2
; Iteration 3
; Iteration 4
; Goodbye
; nil

; it's equal to:
(defn recursive-printer
  ([]
   (recursive-printer 0))
  ([iteration]
   (println (str "Iteration " iteration))
   (if (> iteration 3)
     (println "Goodbye")
     (recursive-printer (inc iteration))))
  )

(recursive-printer)

; regular expressions
(re-find #"^left-" "left-eye")
; => "left-"

(re-find #"^left-" "cleft-chin")
; => nil

(re-find #"^left-" "wongleblart")
; => nil

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "ring-")
   :size (:size part)}
  )
(matching-part {:name "left-eye" :size 1})
; => {:name "right-eye" :size 1}]

(matching-part {:name "head" :size 3})
; => {:name "head" :size 3}]

