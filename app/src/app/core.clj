(ns app.core
  (:require [org.httpkit.server :refer [run-server]]
            [ring.util.response :refer [response]]
            [compojure.core :refer [defroutes GET]]
            [ring.middleware.json :refer [wrap-json-response]]))

(defn ping-handler []
  (response {:status "OK"}))

(defroutes unrestricted-routes
           (GET "/_ping" req (ping-handler)))

(defroutes app
           (wrap-json-response unrestricted-routes))
(def port 8000)
(defn -main []
  (run-server app {:port port})
  (println (str "server running on port " port))
  (println (str "ping url http://localhost:" port "/_ping")))
