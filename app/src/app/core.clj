(ns app.core
  (:require [org.httpkit.server :refer [run-server]]
            [ring.util.response :refer [response]]
            [compojure.core :refer [defroutes GET]]))

(defn ping-handler []
  (response {:status  200
             :headers {"Content-Type" "application/json"}
             :body    "{\"body\": \"pong\"}"}))

(defroutes unrestricted-routes
           (GET "/_ping" req (ping-handler)))

(defroutes app
           unrestricted-routes)
(def port 8000)
(defn -main []
  (run-server app {:port port})
  (println (str "server running on port " port))
  (println (str "ping url http://localhost:" port "/_ping")))
