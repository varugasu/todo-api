(ns api.service
  (:require [api.utils :refer [generate-uuid]]
            [xtdb.api :as xt]))


(defn create-todo [node todo]
  (let [new-todo (assoc todo :completed false)]
    (xt/submit-tx node [[:put-docs :todos (assoc new-todo :xt/id (generate-uuid))]])))

(defn get-todo-by-id [node id]
  (first
   (xt/q node '(from :todos [{:xt/id $id} *])
         {:args {:id id}})))

(defn get-todos [node]
  (xt/q node '(from :todos [*])))

(defn delete-todo-by-id [node id]
  (xt/submit-tx node [[:delete-docs :todos id]]))
