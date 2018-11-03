(ns person.handler
  (:require
   [compojure.api.sweet :refer :all]
   [compojure.route :as route]
   [ring.util.http-response :refer :all]
   [ring.util.http-status :as status]
   [schema.core :as s]
   [person.repository :refer :all]))

(s/defschema NewPerson {:name String})

(s/defschema Person (assoc NewPerson :id String))

(s/defschema UpdatedPerson NewPerson)

(defn get-all-people-handler []
  (ok (get-all-people)))

(defn insert-person-handler! [person]
  (let
   [createdPerson (insert-person! person)]
    (created (:id createdPerson) createdPerson)))

(defn get-person-handler [id]
  (if-let [person (get-person id)]
    (ok person)
    (not-found (str "No person with id " id))))

(defn update-person-handler! [id person]
  (if-let [person (update-person! id person)]
    (accepted person)
    (not-found (str "No person with id " id))))

(defn delete-person-handler! [id]
  (fn [_]
    (delete-person! id)
    (no-content)))
