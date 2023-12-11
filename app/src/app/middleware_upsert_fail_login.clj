(ns app.middleware-upsert-fail-login
  (:require
    [clj-rate-limiter.core :as r]
    [org.httpkit.server :refer [run-server]]
    [compojure.core :refer [GET wrap-routes routes defroutes]]
    [taoensso.timbre :refer [info]]
    [ring.util.response :refer [response]]))

(defn in-cool-down-period?
  []
  (println "check cool down"))

(defn upsert-new-attempt
  []
  (println "upsert new attempt"))

(defn check-email-middleware [handler]
  (fn [request]
    (in-cool-down-period?)
    (upsert-new-attempt)
    (handler request)))

(defn login [req]
  "hello")

(defroutes limit-route
           (wrap-routes
             (routes (GET "/login" req (login req)))
             check-email-middleware))

(defroutes app
           limit-route)

(defn -main []
  (run-server app {:port 3000})
  (info "Server is running" {:port 3000}))