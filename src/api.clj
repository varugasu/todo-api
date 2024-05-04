(ns api  (:require [compojure.core :refer  [GET  routes]]
                   [compojure.route :as route]
                   [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
                   [ring.adapter.jetty :refer [run-jetty]]
                   [cheshire.core :refer [generate-string]]
                   [xtdb.client :as xtc]
                   [xtdb.api :as xt]))

(defn make-routes [node]
  (routes (GET "/todos" [] (generate-string {:todos (xt/q node '(from :todos [*]))}))
          (route/not-found "Not Found")))

(defn make-app [node]
  (wrap-defaults (make-routes node) site-defaults))

(def jetty-opts {:port 3000 :join? false})

(defn -main []
  (with-open [node (xtc/start-client "http://localhost:6543")]
    (run-jetty (make-app node) jetty-opts)))
