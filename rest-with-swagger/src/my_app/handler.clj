(ns my-app.handler
  (:require
   [compojure.api.sweet :refer :all]
   [compojure.route :as route]
   [ring.util.http-response :refer :all]
   [ring.util.http-status :as status]
   [schema.core :as s]
   [person.handler :as person])
  (:import [org.joda.time LocalDate]))

(s/defschema Pizza
  {:name s/Str
   (s/optional-key :description) s/Str
   :size (s/enum :L :M :S)
   :origin {:country (s/enum :FI :PO)
            :city s/Str}})

(def app
  (api
   {:swagger
    {:ui "/"
     :spec "/swagger.json"
     :data {:info {:title "My Api"
                   :description "Some description"}
            :tags [{:name "Person API", :description "Some description for tag"}]}}}

   (context "/api" []
     :tags ["api"]

     (GET "/param" []
         :query-params [{y :- Long 0}
                        {text :- String "abc"}
                        {start-date :- LocalDate (LocalDate/now)}
                        {tags :- [String] []}]
         :return String
         :summary "PARAMS"
         (str "y=" y "; text=" text "; start-date=" start-date "; tags=" tags))

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
       :tags ["people"]

       (GET "/" []
         :return [person/Person]
         :summary "List all people"
         (person/get-all-people-handler))

       (POST "/" []
         :body [person person/NewPerson]
         :return person/Person
         :summary "Create a person record"
         (person/insert-person-handler! person))

       (context "/:id" [id]
         (GET "/" []
           :return person/Person
           :summary "Return person with provided id"
           (person/get-person-handler id))

         (PUT "/" []
           :body [person person/UpdatedPerson]
           :return person/Person
           :summary "Update a person with provided id"
           (person/update-person-handler! id person))

         (DELETE "/" []
           :return String
           :summary "Delete a person with provided id"
           (person/delete-person-handler! id)))))

   (undocumented
    (route/not-found (not-found {:error "Oops! Cannot found :("})))))
