(ns api.service
  (:require [api.utils :refer [generate-uuid]]
            [xtdb.api :as xt]))


(defn create-todo [node todo]
  (let [new-todo (assoc todo :completed false)]
    (xt/submit-tx node [[:put-docs :todos (assoc new-todo :xt/id (generate-uuid))]])))
