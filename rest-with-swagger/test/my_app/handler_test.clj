(ns my-app.handler-test
  (:require [cheshire.core :as cheshire]
            [clojure.test :refer :all]
            [my-app.handler :refer :all]
            [ring.mock.request :as mock]))

(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))

(deftest test-app
  (testing "Test GET request to /api/plus?x=1&y=2 returns expected response with total"
    (let [response (app (-> (mock/request :get  "/api/plus?x=1&y=2")))
          body     (parse-body (:body response))]
      (is (= (:status response) 200))
      (is (= (:result body) 3))))

  (testing "Test POST request to /echo returns expected response"
    (let [pizza {:name "Turtle Pizza"
                 :description "Pepperoni pizza"
                 :size :L
                 :origin {:country :FI
                          :city "MyCity"}}
          response (app (-> (mock/request :post "/api/echo")
                            (mock/content-type "application/json")
                            (mock/body  (cheshire/generate-string pizza))))
          body     (parse-body (:body response))]
      (is (= (:status response) 200))
      (is (= body pizza))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))