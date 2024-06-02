(ns api  (:require [api.adapters :refer [todo-from-request]]
                   [api.service :refer [create-todo delete-todo-by-id get-todo-by-id
                                        get-todos]]
                   [cheshire.core :refer [generate-string]]
                   [clojure.java.io]
                   [compojure.core :refer  [DELETE GET POST routes]]
                   [compojure.route :as route]
                   [ring.adapter.jetty :refer [run-jetty]]
                   [ring.middleware.defaults :refer [api-defaults
                                                     wrap-defaults]]
                   [xtdb.client :as xtc]))

(defn make-response [body]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (generate-string body)})

(defn make-routes [node]
  (routes (GET "/todos" [] (make-response {:todos (get-todos node)}))
          (GET  "/todos/:id" [id] (make-response (get-todo-by-id node id)))
          (POST "/todos" request (create-todo node (todo-from-request request)) {:status 201})
          (DELETE "/todos/:id" [id] (delete-todo-by-id node id) {:status 204})
          (route/not-found "Not Found")))

(defn make-app [node]
  (wrap-defaults (make-routes node) api-defaults))

(def jetty-opts {:port 3000 :join? false})

(defn -main []
  (with-open [node (xtc/start-client "http://localhost:6543")]
    (run-jetty (make-app node) jetty-opts)))
