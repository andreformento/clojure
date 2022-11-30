(ns clojure-noob.first_steps)

(defn run-payroll
  [employee-id]
  (println employee-id))

(def andre {:first-name  "André"
            :last-name   "Formento"
            :employee-id 123})

(:first-name andre)

(-> andre
    (:employee-id)
    (run-payroll))

(map :employee-id [andre,,,])

; Using records
(defrecord Employee [first-name
                     last-name
                     employee-id])

(def andre-employee (->Employee "André"
                                "Formento"
                                321))
(def andre-from-map (map->Employee {:first-name  "André"
                                    :last-name   "Formento"
                                    :employee-id 123}))
(println andre-employee)
