(ns todomvc.events
  (:require
   [re-frame.core :as re-frame :refer [reg-event-db reg-event-fx inject-cofx after path]]
   [todomvc.db :as db]
   [cljs.spec.alpha :as s]))

(defn throw-if-invalid [spec db]
  (when-not (s/valid? spec db)
    (throw (ex-info (str "spec check failed: " (s/explain-str spec db)) {}))))

(def ->localstore (after (fn [db] (db/tasks->localstorage (:tasks db)))))
(def spec-check (after (partial throw-if-invalid :todomvc.db/db)))
(def task-interceptors [->localstore spec-check (path :tasks)])

(defn next-id [tasks]
  ((fnil inc 0) (last (keys tasks))))

(reg-event-fx
 ::initialize-db
 [(inject-cofx :local-store-tasks) spec-check]
 (fn [{:keys [local-store-tasks]} _]
   {:db (assoc db/default-db :tasks local-store-tasks)}))

(reg-event-db
  :add-task
  task-interceptors
  (fn [tasks [_ new-task]]
    (let [id (next-id tasks)]
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
  :edit-task
  task-interceptors
  (fn [tasks [_ id description]]
    (assoc-in tasks [id :description] description)))

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