(ns todomvc.db
  (:require [cljs.spec.alpha :as s]))

(s/def ::id int?)
(s/def ::description string?)
(s/def ::done boolean?)
(s/def ::task (s/keys :req-un [::id ::description ::done]))
(s/def ::tasks (s/and
                 (s/map-of ::id ::task)
                 (s/every (fn [[k v]] (= (:id v) k)))))

(s/def ::showing #{:all :active :completed})

(s/def ::db (s/keys :req-un [::tasks ::showing]))

(def default-db {:tasks (sorted-map) :showing :all})
