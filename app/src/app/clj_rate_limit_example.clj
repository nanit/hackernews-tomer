(ns app.clj_rate_limit_example
  (:require
    [clj-rate-limiter.core :as r]))

(def redis-conn-pw
  {:pool {}
   :spec {:host "localhost"
          :port 6379
          :password "foo"
          :user "user"}})

(def limiter (r/create
               (r/rate-limiter-factory :redis
                                       :redis redis-conn-pw
                                       :namespace "APIs"
                                       :interval 1000
                                       :max-in-interval 100)))

(def limiter-mem (r/create
                   (r/rate-limiter-factory :memory
                                           :interval 1000
                                           :max-in-interval 100)))

(defn -main []
  (println "Check rate limit: Mem")
  (println (r/allow? limiter-mem "key1"))
  (println "Done - check rate limit: Mem")

  (println "Check rate limit: Redis")
  (println (r/permit? limiter "key1"))
  (println "Done - check rate limit: Redis"))
