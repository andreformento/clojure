(ns clojure-noob.symmetrizer)

(def asym-hobbit-body-parts [{:name "head" :size 3}
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

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))

(symmetrize-body-parts asym-hobbit-body-parts)

;[{:name "head", :size 3}
; {:name "left-eye", :size 1}
; {:name "right-eye", :size 1}
; {:name "left-ear", :size 1}
; {:name "right-ear", :size 1}
; {:name "mouth", :size 1}
; {:name "nose", :size 1}
; {:name "neck", :size 2}
; {:name "left-shoulder", :size 3}
; {:name "right-shoulder", :size 3}
; {:name "right-upper-arm", :size 3}
; {:name "left-upper-arm", :size 3}
; {:name "chest", :size 10}
; {:name "back", :size 10}
; {:name "left-forearm", :size 3}
; {:name "right-forearm", :size 3}
; {:name "abdomen", :size 6}
; {:name "left-kidney", :size 1}
; {:name "right-kidney", :size 1}
; {:name "left-hand", :size 2}
; {:name "right-hand", :size 2}
; {:name "right-knee", :size 2}
; {:name "left-knee", :size 2}
; {:name "right-thigh", :size 4}
; {:name "left-thigh", :size 4}
; {:name "right-lower-leg", :size 3}
; {:name "left-lower-leg", :size 3}
; {:name "right-achilles", :size 1}
; {:name "left-achilles", :size 1}
; {:name "right-foot", :size 2}
; {:name "left-foot", :size 2}]

;; sum with reduce
(reduce + [1 2 3 4])
; => 10

(defn my-reduce
  ([f initial coll]
   (loop [result initial
          remaining coll]
     (if (empty? remaining)
       result
       (recur (f result (first remaining)) (rest remaining)))))
  ([f [head & tail]]
   (my-reduce f head tail)))

(my-reduce + [1 2 3 4])

(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))

(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
        body-party-size-sum (reduce + (map :size sym-parts))
        target (rand body-party-size-sum)]
    (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]
      (if (> accumulated-size target)
        part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))
(hit asym-hobbit-body-parts)
