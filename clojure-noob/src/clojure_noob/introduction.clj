(ns clojure-noob.introduction)

(defn hello-world
  "doc"
  [{first-name :first-name middle-name :middle-name last-name :last-name}]
  (let [strings (remove nil? ["Hello" first-name middle-name last-name])]
    (clojure.string/join " " strings)))

(hello-world {:first-name "André" :last-name "Formento"})
; "Hello André Formento"

(defn hello-world-with-defaults
  "Given a map representing an employee, return
   a Hello World string to greet that employee."
  [person]
  (let [defaults {:first-name "something" :middle-name "something" :last-name "something"}
        merged (merge defaults person)
        strings ["Hello" (:first-name merged) (:middle-name merged) (:last-name merged)]]
    (clojure.string/join " " strings)))

(defn hello-world-with-keys
  "Given a map representing an employee, return a Hello World string to greet that employee."
  [{:keys [first-name middle-name last-name] :as person}]
  (println "Person map with" (count person) "keys")
  (let [strings (remove nil? ["Hello" first-name middle-name last-name])]
    (clojure.string/join " " strings)))
(hello-world-with-keys {:first-name "André" :last-name "Formento"})
; Person map with 2 keys
; "Hello André Formento"
