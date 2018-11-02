(ns my-app.repository
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [monger.json])
  (:import [org.bson.types ObjectId]))

(def mongo-host (or (System/getenv "MONGO_HOST")
                    "localhost"))

(def conn (mg/connect {:host mongo-host}))

(def db (mg/get-db conn "test"))

(def people-collection "people")

(defn objectId->str [e]
  (if-let [objectId (:_id e)]
    (-> e (dissoc :_id) (assoc :id (str objectId)))
    e))

(def objectIds->str (partial map objectId->str))

(defn get-all-people []
  (-> (mc/find-maps db people-collection)
      objectIds->str))

(defn get-person [id]
  (-> (mc/find-map-by-id db people-collection (ObjectId. id))
      objectId->str))

(defn delete-person! [id]
  (mc/remove-by-id db people-collection (ObjectId. id)))

(defn insert-person! [person]
  (-> (mc/insert-and-return db people-collection (assoc person :_id (ObjectId.)))
      objectId->str))

(defn update-person! [id person]
  (-> (mc/save-and-return db people-collection (assoc person :_id (ObjectId. id)))
      objectId->str))
