(ns clojure-noob.threading-operators)


(def employees
  [{:first-name "A" :last-name "B" :email "A@nubank.com.br" :hired-at #inst "2022-05-20"}
   {:first-name "C" :last-name "D" :email "C@nubank.com.br" :hired-at #inst "2022-05-21"}
   {:first-name "E" :last-name "F" :email "E@nubank.com.br" :hired-at #inst "2022-05-21"}

   ;; Seu fix foi implementado e feito o deploy a partir daqui

   {:first-name "G" :last-name "H" :email "G.H@nubank.com.br" :hired-at #inst "2022-05-21"}
   {:first-name "I" :last-name "J" :email "I.J@nubank.com.br" :hired-at #inst "2022-05-22"}])

(defn email-address
  [employee]
  (format "$s.%s@gmail.com"
          (:first-name employee)
          (:last-name employee)))

(defn old-email-format?
  [employee]
  (not= (:email employee) (email-address employee)))

(defn hired-day
  [employee]
  (.getDay (:hired-at employee)))

(defn ugly-report
  [employees]
  (vals (group-by hired-day (map #(assoc % :email (email-address %))
                                 (filter old-email-format? employees)))))

(defn thread-last-report
  [employees]
  (->> employees
       (filter old-email-format?)
       (map #(assoc % :email (email-address %)))
       (group-by hired-day)
       vals))

(println (thread-last-report employees))
