(ns todomvc.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :tasks
 (fn [db]
   (:tasks db)))

(re-frame/reg-sub
  :task-count
  (fn [{:keys [tasks]}]
    (let [done (filter (fn [[k v]] (:done v)) tasks)]
      [(- (count tasks) (count done)) (count done)])))

(re-frame/reg-sub
  :showing
  (fn [{:keys [showing]}]
    showing))