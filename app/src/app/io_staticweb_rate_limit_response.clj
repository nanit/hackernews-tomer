(ns app.io-staticweb-rate-limit-response)
(require '[io.staticweb.rate-limit.responses :as responses])

(defn custom-rate-limit-response-body
  [retry-after]
  (let [ret (str (.getSeconds retry-after))
        msg (str "rate-limit has been exceeded (please retry in: " ret ")")]
    {:body msg}))

(defn custom-rate-limit-response
  [quota retry-after]
  (responses/too-many-requests-response (custom-rate-limit-response-body retry-after) retry-after))
