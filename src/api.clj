(ns api  (:require [cheshire.core :refer [generate-string parse-string]]
                   [clojure.java.io]
                   [compojure.core :refer  [GET POST routes]]
                   [compojure.route :as route]
                   [ring.adapter.jetty :refer [run-jetty]]
                   [ring.middleware.defaults :refer [api-defaults
                                                     wrap-defaults]]
                   [xtdb.api :as xt]
                   [xtdb.client :as xtc]))

(defn generate-uuid []
  (str (clojure.core/random-uuid)))

(defn- read-json [request]
  (parse-string (slurp (:body request)) true))


(defn todo-from-request [request]
  (let [{:keys [title, description]} (read-json request)]
    {:title title :description description}))

(defn make-routes [node]
  (routes (GET "/todos" [] (generate-string {:todos (xt/q node '(from :todos [*]))}))
          (POST "/todos" request (let [todo (assoc (todo-from-request request) :completed false)]
                                   (xt/submit-tx node [[:put-docs :todos (assoc todo :xt/id (generate-uuid))]])) {})
          (route/not-found "Not Found")))

(defn make-app [node]
  (wrap-defaults (make-routes node) api-defaults))

(def jetty-opts {:port 3000 :join? false})

(defn -main []
  (with-open [node (xtc/start-client "http://localhost:6543")]
    (run-jetty (make-app node) jetty-opts)))
