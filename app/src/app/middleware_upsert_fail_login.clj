(ns app.middleware-upsert-fail-login
  (:require
    [clj-rate-limiter.core :as r]
    [org.httpkit.server :refer [run-server]]
    [compojure.core :refer [GET wrap-routes routes defroutes]]
    [taoensso.timbre :refer [info]]
    [ring.util.response :refer [response]]))

(defn in-cool-down-period?
  [req]
  (println "check cool down")
  ; Add your cool down logic here
  false)

(defn upsert-new-attempt
  [req]
  (println "upsert new attempt")
  ; Add logic to upsert new attempt here
  req)

(defn check-email-middleware [handler]
  (fn [request]
    (if (in-cool-down-period? request)
      (response "Cool down period, try again later.")
      (handler (upsert-new-attempt request)))))

(defn login [req]
  (let [user-param (-> req :query-params :user)]
    (if user-param
      (str "Hello, " user-param)
      "User parameter not found")))

(defroutes limit-route
           (wrap-routes
             (routes (GET "/login" [req] (login req)))
             check-email-middleware))

(defroutes app
           limit-route)

(defn -main []
  (run-server app {:port 3000})
  (info "Server is running" {:port 3000}))
