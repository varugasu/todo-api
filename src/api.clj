(ns api  (:require [compojure.core :refer  [GET POST routes]]
                   [compojure.route :as route]
                   [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
                   [ring.adapter.jetty :refer [run-jetty]]
                   [cheshire.core :refer [generate-string parse-string]]
                   [xtdb.client :as xtc]
                   [xtdb.api :as xt]))
(defn- read-json [request]
  (parse-string (slurp (:body request))))

(defn make-routes [node]
  (routes (GET "/todos" [] (generate-string {:todos (xt/q node '(from :todos [*]))}))
          (POST "/todos" request (println (read-json request)) "OK!!")
          (route/not-found "Not Found")))

(defn make-app [node]
  (wrap-defaults (make-routes node) api-defaults))

(def jetty-opts {:port 3000 :join? false})

(defn -main []
  (with-open [node (xtc/start-client "http://localhost:6543")]
    (run-jetty (make-app node) jetty-opts)))
