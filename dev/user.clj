(ns user
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [api :refer [app]]
            [ring.middleware.reload :refer [wrap-reload]]))

(def dev-app (wrap-reload #'app))

(defonce server (atom nil))

(defn stop []
  (when-not (nil? @server)
    (.stop @server)
    (reset! server nil)))

(defn start []
  (when (nil? @server)
    (reset! server (run-jetty dev-app {:port 3000 :join? false}))))
