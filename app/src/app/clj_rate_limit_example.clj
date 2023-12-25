(ns app.clj_rate_limit_example
  (:require
    [clj-rate-limiter.core :as r]))

(def redis {:pool {}
            :spec {:uri "redis://:password@localhost:6379"}})

(def limiter (r/create
               (r/rate-limiter-factory :redis
                                       :redis redis
                                       :namespace "APIs"
                                       :interval 1000
                                       :min-difference 1
                                       :max-in-interval 100)))

(defn -main []
  (println "Check rate limit")
  (println (r/allow? limiter "key1"))
  (println "Done - check rate limit"))
