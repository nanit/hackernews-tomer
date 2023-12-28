(ns app.io-staticweb-rate-limit-response)

(defn custom-response-builder
  [quota retry-after]
  {:body "{\"error\":\"rate-limit-exceeded-custom\"}"
   :headers {"Retry-After" (str (.getSeconds retry-after))
             "Quota" quota}
   :status 429})

; TODO figure this out
;(testing "with custom response"
;         (let [custom-rsp {:headers {"Content-Type" "text/plain"}
;                           :body "Hello, World!"
;                           :status 418}
;               rsp (r/too-many-requests-response custom-rsp (Duration/ofSeconds 5))
;               headers (:headers rsp)]
;           (is (= (:status rsp) 418))
;           (is (= (:body rsp) "Hello, World!"))
;           (is (= (headers "Content-Type") "text/plain"))
;           (is (= (headers "Retry-After") "5"))))))
