(ns app.io-staticweb-rate-limit-util)
(require '[io.staticweb.rate-limit.limits :as limits])


(defn- get-client-ip [req]
  (if-let [ips (get-in req [:headers "x-forwarded-for"])]
    (-> ips (clojure.string/split #",") first)
    (:remote-addr req)))

(defrecord IpRateCustomLimit [id quota ttl]
  limits/RateLimit
  (get-key [self req]
    (str (.getName (type self)) id "-" (get-client-ip req)))

  (get-quota [self req]
    (:user-rate-limit-quota req))

  (get-ttl [self req]
    (:user-rate-limit-ttl req)))

(defn ip-rate-custom-limit
  [id quota ttl]
  (->IpRateCustomLimit id quota ttl))