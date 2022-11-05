(ns clojure-noob.03-composition)
(require '[clojure.string :as s])

(defn clean
  [text]
  (s/replace (s/trim text) #"lol" "LOL"))

(clean "My lol! ")
; => "My LOL!"

(def character
  {:name       "Smooches McCutes"
   :attributes {:intelligence 10
                :strength     4
                :dexterity    5}})
(def c-int (comp :intelligence :attributes))
(def c-str (comp :strength :attributes))
(def c-dex (comp :dexterity :attributes))

(c-int character) ; => 10
(c-str character) ; =>  4
(c-dex character) ; =>  5

; its equal to
; (fn [c] (:strength (:attributes c)))
