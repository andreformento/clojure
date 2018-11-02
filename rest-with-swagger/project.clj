(defproject clojure-rest "0.1.0-SNAPSHOT"
  :description "REST service for documents"
  :url "http://github.com/andreformento/clojure-rest"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [metosin/compojure-api "2.0.0-alpha7"]
                 [environ "1.1.0"]
                 [ring/ring-json "0.4.0"]
                 [cheshire/cheshire "5.8.1"]
                 [com.novemberain/monger "3.1.0"]
                 [compojure "1.6.1"]]
  :ring {:handler my-app.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]
                        [midje "1.9.4"]]
         :plugins [[lein-ring "0.12.4"]
                   [lein-midje "3.2.1"]]}})
