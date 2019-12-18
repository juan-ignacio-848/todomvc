(ns todomvc.events
  (:require
   [re-frame.core :as re-frame :refer [reg-event-db after path]]
   [todomvc.db :as db]
   [cljs.spec.alpha :as s]))

(defn throw-if-invalid [spec db]
  (when-not (s/valid? spec db)
    (throw (ex-info (str "spec check failed: " (s/explain-str spec db)) {}))))

(def spec-check (after (partial throw-if-invalid :todomvc.db/db)))
(def task-interceptors [spec-check (path :tasks)])

;; TODO: Local storage interceptor

;; TODO: Review this.
(def id (atom 0))
(defn next-id! []
  (let [current-id @id]
    (swap! id inc)
    current-id))

(reg-event-db
 ::initialize-db
 (fn [_ _]
   task-interceptors
   db/default-db))

(reg-event-db
  :add-task
  task-interceptors
  (fn [tasks [_ new-task]]
    (let [id (next-id!)]
      (assoc tasks id {:id id :description new-task :done false}))))

(reg-event-db
  :toggle-done
  task-interceptors
  (fn [tasks [_ id]]
    (update-in tasks [id :done] not)))

(reg-event-db
  :delete-task
  task-interceptors
  (fn [tasks [_ id]]
    (dissoc tasks id)))

(reg-event-db
  :update-showing
  spec-check
  (fn [db [_ showing]]
    (assoc db :showing showing)))

(reg-event-db
  :clear-completed
  task-interceptors
  (fn [tasks [_ _]]
    (reduce-kv (fn [m k v]
                 (if (not (:done v))
                   (assoc m k v)
                   m))
               {}
               tasks)))