(ns app.io-staticweb-rate-limit-util)
(require '[io.staticweb.rate-limit.limits :as limits])


(defn- get-client-ip [req]
  (if-let [ips (get-in req [:headers "x-forwarded-for"])]
    (-> ips (clojure.string/split #",") first)
    (:remote-addr req)))

(defrecord IpRateLimit [id quota ^java.time.Duration ttl]
  limits/RateLimit
  (get-key [self req]
    (let [key (get-client-ip req)]
      (println key)
      (str (.getName ^Class (type self)) id "-" key)))

  (get-quota [self req]
    quota)

  (get-ttl [self req]
    ttl))

(defn ip-rate-custom-limit
  [id quota ttl]
  (->IpRateLimit id quota ttl))