(ns app.io-staticweb-rate-limit-play)
(use 'compojure.core)
(require '[io.staticweb.rate-limit.middleware :refer [ip-rate-limit wrap-rate-limit]])
(require '[io.staticweb.rate-limit.storage :as storage])
(require '[io.staticweb.rate-limit.redis :as redis])
(require '[org.httpkit.server :refer [run-server]])
(require '[app.io-staticweb-rate-limit-util :refer [ip-rate-custom-limit]])

; Define redis connection
(def redis-conn-pw
  {:pool {}
   :spec {:host "localhost"
          :port 6379
          :password "foo"
          :user "user"}})

; Define redis storage
(def redis-storage (redis/redis-storage redis-conn-pw))

; Define redis limit
(def r-limit (ip-rate-limit :limit-id 1 (java.time.Duration/ofSeconds 10)))

; Define redis middleware
(def r-rate-limit-config {:storage redis-storage :limit r-limit})

  ;; Wrap the /limit route in the rate limiting middleware
(def app (routes
           (GET "/no-limit" [] "no-limit")
           (wrap-rate-limit
             (GET "/limit" [] "limit")
             r-rate-limit-config)))

(defn -main []
  (run-server app {:port 3000})
  (println "Server is running" {:port 3000}))
