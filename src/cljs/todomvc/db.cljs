(ns todomvc.db
  (:require [cljs.spec.alpha :as s]
            [re-frame.core :as re-frame]))

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

;; Local storage

(def ls-key "todomvc-tasks")

(defn tasks->localstorage
  "Puts tasks into localStorage"
  [tasks]
  (.setItem js/localStorage ls-key (str tasks)))

;; register a coeffect handler to inject the tasks stored in localstore
(re-frame/reg-cofx
  :local-store-tasks
  (fn [cofx _]
    (assoc cofx :local-store-tasks
                (into (sorted-map)
                      (some->> (.getItem js/localStorage ls-key)
                               (cljs.reader/read-string))))))