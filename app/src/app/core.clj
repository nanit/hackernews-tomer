(ns app.core
  (:require [org.httpkit.server :refer [run-server]]
            [ring.middleware.params :as params]
            [ring.util.response :refer [response]]))

(defn ping-handler [request]
  (response {:status 200
             :headers {"Content-Type" "application/json"}
             :body "{\"body\": \"pong\"}"}))

(defn -main []
  (run-server (params/wrap-params ping-handler) {:port 8080}))
