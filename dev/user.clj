(ns user
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [api :refer [make-app]]
            [ring.middleware.reload :as ring-reload]
            [xtdb.client :as xtc]
            [xtdb.api :as xt]))

(def reloader #'ring-reload/reloader)

(def node (xtc/start-client "http://localhost:6543"))

(defn make-reloading-app [settings]
  (let [reload! (reloader ["src"] true)]
    (fn [request]
      (reload!)
      ((make-app settings) request))))

(defonce server (atom nil))

(defn stop []
  (when-not (nil? @server)
    (.stop @server)
    (reset! server nil)))

(defn start []
  (when (nil? @server)
    (reset! server (run-jetty (make-reloading-app node) {:port 3000 :join? false}))))

