(ns app.clj-rate-limiter-play-redis
  (:require
    [clj-rate-limiter.core :as r]
    [org.httpkit.server :refer [run-server]]
    [compojure.core :refer [GET wrap-routes routes defroutes]]
    [taoensso.timbre :refer [info]]
    [ring.util.response :refer [response]]))

(def redis {:pool {}
            :spec {:uri ""}})

(def limiter (r/create
               (r/rate-limiter-factory :redis
                                       :redis redis
                                       :namespace "APIs"
                                       :interval 1000
                                       :min-difference 1
                                       :flood-threshold 5
                                       :max-in-interval 100)))

(defn check-email-middleware [handler]
  (fn [request]
    (let [allow (r/allow? limiter "key1")]
      (if (= allow false)
        (response {:status 429})
        (handler request)))))

(defroutes limit-route
           (wrap-routes
             (routes (GET "/limit" [] "limit"))
             check-email-middleware))

(defroutes no-limit-route
           (GET "/no-limit" [] "no-limit"))

(defroutes app
           limit-route
           no-limit-route)

(defn -main []
  (run-server app {:port 3000})
  (info "Server is running" {:port 3000}))