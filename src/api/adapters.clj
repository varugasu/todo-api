(ns api.adapters
  (:require [api.utils :refer [read-json]]))

(defn todo-from-request [request]
  (let [{:keys [title, description, completed]} (read-json request)]
    {:title title :description description, :completed completed}))

