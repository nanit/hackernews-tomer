(ns app.io-staticweb-rate-limit-util)
(require '[io.staticweb.rate-limit.limits :as limits])


(defn- get-client-ip [req]
  (if-let [ips (get-in req [:headers "x-forwarded-for"])]
    (-> ips (clojure.string/split #",") first)
    (:remote-addr req)))

(defrecord IpEmailRateLimit [id quota ^java.time.Duration ttl]
  limits/RateLimit
  (get-key [self req]
    (let [ip (get-client-ip req)
          key (str "john@email.com:" ip)]
      (println key)
      (str (.getName ^Class (type self)) id "-" key)))

  (get-quota [self req]
    quota)

  (get-ttl [self req]
    ttl))

(defn ip-email-rate-limit
  [id quota ttl]
  (->IpEmailRateLimit id quota ttl))