(ns fwpd.core)

; # https://www.braveclojure.com/core-functions-in-depth/#A_Vampire_Data_Analysis_Program_for_the_FWPD
(def my-test "b")
; $ (use 'fwpd.core :reload)

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [number-as-text]
  (Integer. number-as-text))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key raw-value-to-be-converted]
  ((get conversions vamp-key) raw-value-to-be-converted))

(convert :glitter-index "3")
; => 3

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(def read-suspects-csv (parse (slurp filename)))

; # https://clojuredocs.org/clojure.core/assoc
(assoc {} :key1 "value" :key2 "another value")
; => {:key2 "another value", :key1 "value"}

; # https://clojuredocs.org/clojure.core/reduce
(reduce + [1 2 3 4 5])
; => 15
(reduce (fn [accumulator current] (+ 10 current accumulator))
        20
        [1 2 3 4 5])
; => 85

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(def all-suspects (mapify read-suspects-csv))

(first all-suspects)
; => {:name "Edward Cullen", :glitter-index 10}

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(def glitter-result (glitter-filter 3 all-suspects))
(->> read-suspects-csv
     mapify
     (glitter-filter 3))
; => ({:name "Edward Cullen"  , :glitter-index 10}
; =>  {:name "Jacob Black"    , :glitter-index  3}
; =>  {:name "Carlisle Cullen", :glitter-index  6})

; # https://www.braveclojure.com/core-functions-in-depth/#Exercises
; 1. Turn the result of your glitter filter into a list of names.
(def glitter-names (map #(:name %) glitter-result))

; 2. Write a function, append, which will append a new suspect to your list of suspects.
(defn append-suspect
  [suspects new-suspect]
  (conj suspects new-suspect))

(append-suspect all-suspects {:name "a" :glitter-index 1})

; 3. Write a function, validate, which will check that :name and :glitter-index are present when you append.
;    The validate function should accept two arguments:
;       a map of keywords to validating functions, similar to conversions, and the record to be validated.

(def validators {:name          #(>= (count %) 3)
                 :glitter-index #(>= % 5)})

(defn validate
  [map-of-keywords-validations-fn record]
  (reduce (fn [invalids [field-name validation-fn]]
            (if (contains? record field-name)
              invalids
              (conj invalids field-name)))
          []
          map-of-keywords-validations-fn))

(validate validators {:name "a"})
; => [:glitter-index]

(defn is-valid?
  [map-of-keywords-validations-fn record]
  (empty? (validate map-of-keywords-validations-fn record)))

(is-valid? validators {:name "a"})
; => false

(defn append-validated-suspect
  [suspects new-suspect]
  (if (is-valid? validators new-suspect)
    (append-suspect suspects new-suspect)
    suspects))
(append-validated-suspect all-suspects {:name "INVALID"})
(append-validated-suspect all-suspects {:name "a new person" :glitter-index 5})

; 4. Write a function that will take your list of maps and convert it back to a CSV string.
;    You’ll need to use the clojure.string/join function.

(defn convert-suspect-to-row
  [suspect]
  (clojure.string/join "," (vals suspect)))
(convert-suspect-to-row {:name "a new person" :glitter-index 5})
; => "a new person,5"

(defn convert-to-csv
  [suspects]
  (clojure.string/join "\n" (map convert-suspect-to-row suspects)))
(convert-to-csv all-suspects)
