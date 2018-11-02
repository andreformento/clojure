(ns my-app.handler
  (:require
   [compojure.api.sweet :refer :all]
   [compojure.route :as route]
   [ring.util.http-response :refer :all]
   [schema.core :as s]
   [environ.core :refer [env]] ; https://github.com/weavejester/environ
   [my-app.repository :refer [get-all-people get-person insert-person delete-person update-person]]))

(s/defschema Pizza
  {:name s/Str
   (s/optional-key :description) s/Str
   :size (s/enum :L :M :S)
   :origin {:country (s/enum :FI :PO)
            :city s/Str}})

(s/defschema NewPerson {:name String})

(s/defschema Person (assoc NewPerson :id String))

(s/defschema UpdatedPerson NewPerson)

(def app
  (api
   {:swagger
    {:ui "/"
     :spec "/swagger.json"
     :data {:info {:title "Finances Service"
                   :description "Accounts and transactions"}
            :tags [{:name "Finances API", :description "Finances Service"}]}}}

   (context "/api" []
     :tags ["api"]

     (GET "/plus" []
       :return {:result Long}
       :query-params [x :- Long, y :- Long]
       :summary "adds two numbers together"
       (ok {:result (+ x y)}))

     (POST "/echo" []
       :return Pizza
       :body [pizza Pizza]
       :summary "echoes a Pizza"
       (ok pizza))

     (context "/people" []

       (GET "/" []
         :return [Person]
         :summary "List all people"
         (ok (get-all-people)))

       (POST "/" []
         :body [person NewPerson]
         :return Person
         :summary "Create a person record"
         (ok (insert-person person)))

       (context "/:id" [id]
         (GET "/" []
           :return Person
           :summary "Return person with provided id"
           (if-let [person (get-person id)]
             (ok person)
             (not-found (str "No person with id " id))))

         (PUT "/" []
           :body [person UpdatedPerson]
           :return Person
           :summary "Update a person with provided id"
           (if-let [person (update-person id person)]
             (ok person)
             (not-found (str "No person with id " id))))

         (DELETE "/" []
           :return String
           :summary "Delete a person with provided id"
           (fn [_]
             (delete-person id)
             (no-content))))))
   (undocumented
    (route/not-found (not-found {:error "Oops! Cannot found :("})))
   ))
