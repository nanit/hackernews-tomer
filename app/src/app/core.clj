(ns app.core
  (:require [org.httpkit.server :refer [run-server]]
            [ring.util.response :refer [response]]
            [compojure.core :refer [defroutes GET]]
            [ring.middleware.json :refer [wrap-json-response]]
            [taoensso.timbre :refer [info]]))

(defn ping-handler []
  (response {:status "OK"}))

(defroutes unrestricted-routes
           (GET "/_ping" req (ping-handler)))

(defroutes app
           (wrap-json-response unrestricted-routes))
(def port 8000)
(def ping-url (str "http://localhost:" port "/_ping"))
(defn -main []
  (run-server app {:port port})
  (info "Server is running" {:port port})
  (info "Ping URL" {:url ping-url}))
