(ns app.core
  (:require [org.httpkit.server :refer [run-server]]
            [ring.middleware.params :as params]
            [ring.util.response :refer [response]]))

(defn ping-handler [request]
  (response {:status 200
             :headers {"Content-Type" "application/json"}
             :body "{\"body\": \"pong\"}"}))
(def port 8000)
(defn -main []
  (println (str "server running on port " port))
  (println (str "ping url http://localhost:" port))
  (run-server (params/wrap-params ping-handler) {:port port}))
