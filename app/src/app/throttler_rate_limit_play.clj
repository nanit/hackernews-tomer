; abandon doesn't support rate limit keys
(ns app.throttler_rate_limit_play
  (:require
    [throttler.core :refer [throttle-chan throttle-fn]]))

(def limiter (r/create
               (r/rate-limiter-factory :memory
                                       :interval 1000
                                       :max-in-interval 1)))

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