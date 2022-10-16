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

(def asym-aliens-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn rename-part
  [part match-part index]
  (let [raw-name (:name part)
        renamed-part (clojure.string/replace raw-name match-part (str index))
        size (:size part)]
    {:name renamed-part :size size}))
(rename-part {:name "left-hand" :size 2} "left" 1)
; => {:name "1-hand", :size 2}

(defn replace-body-part
  [part match-part-name count-parts]
  (let [members-index (map str (range 1 (+ 1 count-parts)))
        replace-member-function #(rename-part part match-part-name %1)]
    (map replace-member-function members-index))
  )
(replace-body-part {:name "left-hand" :size 2} "left" 3)
; => ({:name "1-hand", :size 2} {:name "2-hand", :size 2} {:name "3-hand", :size 2})

(defn create-members-from-part
  [part match-part-name count-parts]
  (if (clojure.string/includes? part match-part-name)
    (replace-body-part part match-part-name count-parts)
    (list part)))
(create-members-from-part {:name "head" :size 3} "left" 3)
; => ({:name "head", :size 3})
(create-members-from-part {:name "left-hand" :size 2} "left" 3)
; => ({:name "1-hand", :size 2}
;     {:name "2-hand", :size 2}
;     {:name "3-hand", :size 2})

(defn aliens-body-parts
  [left-parts]
  (map #(create-members-from-part %1 "left" 5) left-parts))

(aliens-body-parts asym-aliens-body-parts)

; => (({:name "head", :size 3})
;     ({:name "1-eye", :size 1} {:name "2-eye", :size 1} {:name "3-eye", :size 1} {:name "4-eye", :size 1} {:name "5-eye", :size 1})
;     ({:name "1-ear", :size 1} {:name "2-ear", :size 1} {:name "3-ear", :size 1} {:name "4-ear", :size 1} {:name "5-ear", :size 1})
;     ({:name "mouth", :size 1})
;     ({:name "nose", :size 1})
;     ({:name "neck", :size 2})
;     ({:name "1-shoulder", :size 3} {:name "2-shoulder", :size 3} {:name "3-shoulder", :size 3} {:name "4-shoulder", :size 3} {:name "5-shoulder", :size 3})
;     ({:name "1-upper-arm", :size 3} {:name "2-upper-arm", :size 3} {:name "3-upper-arm", :size 3} {:name "4-upper-arm", :size 3} {:name "5-upper-arm", :size 3})
;     ({:name "chest", :size 10})
;     ({:name "back", :size 10})
;     ({:name "1-forearm", :size 3} {:name "2-forearm", :size 3} {:name "3-forearm", :size 3} {:name "4-forearm", :size 3} {:name "5-forearm", :size 3})
;     ({:name "abdomen", :size 6})
;     ({:name "1-kidney", :size 1} {:name "2-kidney", :size 1} {:name "3-kidney", :size 1} {:name "4-kidney", :size 1} {:name "5-kidney", :size 1})
;     ({:name "1-hand", :size 2} {:name "2-hand", :size 2} {:name "3-hand", :size 2} {:name "4-hand", :size 2} {:name "5-hand", :size 2})
;     ({:name "1-knee", :size 2} {:name "2-knee", :size 2} {:name "3-knee", :size 2} {:name "4-knee", :size 2} {:name "5-knee", :size 2})
;     ({:name "1-thigh", :size 4} {:name "2-thigh", :size 4} {:name "3-thigh", :size 4} {:name "4-thigh", :size 4} {:name "5-thigh", :size 4})
;     ({:name "1-lower-leg", :size 3} {:name "2-lower-leg", :size 3} {:name "3-lower-leg", :size 3} {:name "4-lower-leg", :size 3} {:name "5-lower-leg", :size 3})
;     ({:name "1-achilles", :size 1} {:name "2-achilles", :size 1} {:name "3-achilles", :size 1} {:name "4-achilles", :size 1} {:name "5-achilles", :size 1})
;     ({:name "1-foot", :size 2} {:name "2-foot", :size 2} {:name "3-foot", :size 2} {:name "4-foot", :size 2} {:name "5-foot", :size 2}))
