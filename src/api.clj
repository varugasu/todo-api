(ns api  (:require [compojure.core :refer [GET defroutes]]
                   [compojure.route :as route]
                   [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
                   [ring.adapter.jetty :refer [run-jetty]]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))


(defn -main []
  (run-jetty app {:port 3000 :join? false}))
