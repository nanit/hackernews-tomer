(ns app.io-staticweb-rate-limit-response)

(defn custom-response-builder
  [quota retry-after]
  {:body "{\"error\":\"rate-limit-exceeded-custom\"}"
   :headers {"Retry-After" (str (.getSeconds retry-after))
             "Quota" quota}
   :status 429})
