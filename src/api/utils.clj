(ns api.utils
  (:require [cheshire.core :refer [parse-string]]))

(defn generate-uuid []
  (str (clojure.core/random-uuid)))

(defn read-json [request]
  (parse-string (slurp (:body request)) true))

(defn remove-nil-values [m]
  (into {} (filter (comp some? val) m)))
