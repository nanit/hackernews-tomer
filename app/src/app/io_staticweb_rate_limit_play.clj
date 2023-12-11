(ns app.io-staticweb-rate-limit-play
  (:require [org.httpkit.server :refer [run-server]]
            [compojure.core :refer [GET]]
            [taoensso.timbre :refer [info]]
            [io.staticweb.rate-limit.middleware :refer [wrap-rate-limit ip-rate-limit]]
            [io.staticweb.rate-limit.storage :as storage]
            [io.staticweb.rate-limit.redis :as r-storage]))
(use 'compojure.core)
(def storage (storage/local-storage))
;(def rstr (r-storage/redis-storage))
(def limit (ip-rate-limit :limit-id 1 (java.time.Duration/ofSeconds 1)))
(def rate-limit-config {:storage storage :limit limit})
(def app (routes
           (GET "/no-limit" [] "no-limit")
           (wrap-rate-limit
             (GET "/limit" [] "limit")
             rate-limit-config)))

(defn -main []
  (run-server app {:port 3000})
  (info "Server is running" {:port 3000}))
