(ns app.clj-rate-limiter-play
  (:require
    [clj-rate-limiter.core :as r]))

(def limiter (r/create
               (r/rate-limiter-factory :memory
                                       :interval 1000
                                       :max-in-interval 1)))





(defn -main []
  (println (r/allow? limiter "key1"))
  (println (r/allow? limiter "key1")))