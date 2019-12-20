(ns todomvc.css
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.units :refer [px]]
            [garden.color :refer [rgba]]
            [garden.selectors :as gs]))

(gs/defpseudoelement placeholder)

;; Color
(def standard-text-color "#000")
(def task-entry-text-color "#bfbfbf")
(def task-active-color "#4d4d4d")
(def task-inactive-color "#d9d9d9")
(def controls-color "#777")
  (def footer-text-color "#bfbfbf")
(def delete-btn-color "#cc9a9a")
(def delete-btn-color-hover "#af5b5e")
(def border-selected-filter (rgba 175 47 47 0.2))

;; Padding
(def standard-padding (px 16))

;; Font Size
(def footer-font-size (px 10))
(def task-font-size (px 24))

(def unchecked-background-image "url('data:image/svg+xml;utf8,%3Csvg%20xmlns%3D%22http%3A//www.w3.org/2000/svg%22%20width%3D%2240%22%20height%3D%2240%22%20viewBox%3D%22-10%20-18%20100%20135%22%3E%3Ccircle%20cx%3D%2250%22%20cy%3D%2250%22%20r%3D%2250%22%20fill%3D%22none%22%20stroke%3D%22%23ededed%22%20stroke-width%3D%223%22/%3E%3C/svg%3E')")
(def checked-background-image "url('data:image/svg+xml;utf8,%3Csvg%20xmlns%3D%22http%3A//www.w3.org/2000/svg%22%20width%3D%2240%22%20height%3D%2240%22%20viewBox%3D%22-10%20-18%20100%20135%22%3E%3Ccircle%20cx%3D%2250%22%20cy%3D%2250%22%20r%3D%2250%22%20fill%3D%22none%22%20stroke%3D%22%23bddad5%22%20stroke-width%3D%223%22/%3E%3Cpath%20fill%3D%22%235dc2af%22%20d%3D%22M72%2025L42%2071%2027%2056l-4%204%2020%2020%2034-52z%22/%3E%3C/svg%3E')")

(defn all* []
  [:* {:margin 0 :padding 0}])

(defn focus []
  [(gs/focus) {:outline "none"}])

(defn body []
  [:body {:background "#f5f5f5"
          :color      standard-text-color
          :font       "14px 'Helvetica Neue', Helvetica, Arial, sans-serif"}])

(defn container []
  [:.container {:width                 "75%"
                :max-width             (px 550)
                :margin                "20px auto"
                :display               "grid"
                :grid-template-columns "1fr"
                :grid-template-rows    "repeat(3, auto)"
                :grid-template-areas   "\"header\"
                                       \"content\"
                                       \"footer\""}])

(defn header []
  [:.container
   [:.header {:grid-area       "header"
              :font-size       (px 50)
              :color           (rgba 175 47 47 0.15)
              :display         "flex"
              :justify-content "center"}]])

(defn content []
  [:.container
   [:.content {:grid-area      "content"
               :display        "flex"
               :flex-direction "column"
               :margin-bottom "30px"}]])

(defn task-entry []
  [:.task-entry {:padding     "16px 16px 16px 60px"
                 :font-size   task-font-size
                 :font-family "inherit"
                 :border      "none"
                 :box-shadow  "inset 0 -2px 1px rgba(0, 0, 0, 0.2)"}
   [(gs/& placeholder) {:color      task-entry-text-color
                        :font-style "italic"}]])

(defn task-list []
  [:.task-list {:list-style "none"}])

(defn task []
  [:.task {:display "flex"
           :justify-content "space-between"
           :background "#fff"
           :border-bottom "1px solid #ededed"}
   [:.toggle {:appearance         "none"
              :-webkit-appearance "none"
              :background-image   unchecked-background-image
              :background-repeat "no-repeat"
              :background-position "center"
              :width "40px"}]
   [:.task-content {:flex-grow  1
                    :padding    "16px 16px 16px 16px"}
    [:input {:box-shadow "none"
             :padding 0}]
    [:label {:color      task-active-color
             :font-size  task-font-size
             :word-break "break-all"}]]
   [(gs/& gs/hover) [:.delete-btn {:display "block"}]]
   [:.delete-btn {:display "none"
                  :color delete-btn-color
                  :transition "color 0.2s ease-out"
                  :background "none"
                  :align-self "center"
                  :width (px 40)
                  :height (px 40)
                  :font-size (px 30)}
    [(gs/& gs/hover) {:color delete-btn-color-hover}]
    ]])

(defn completed-task []
  [:.completed
   [:label {:text-decoration "line-through"
            :color task-inactive-color}]
   [:.toggle {:background-image checked-background-image}]])

(defn controls []
  [:.controls {:display "flex"
               :justify-content "space-between"
               :background "#fff"
               :line-height 3
               :padding-left standard-padding
               :padding-right standard-padding
               :color controls-color
               :box-shadow "0 1px 1px rgba(0, 0, 0, 0.2),
                            0 8px 0 -3px #f6f6f6,
                            0 9px 11px -3px rgba(0, 0, 0, 0.2),
                            0 16px 0 -6px #f6f6f6,
                            0 17px 2px -6px rgba(0, 0, 0, 0.2)"}
   [:label {:width (px 90)}]
   [:.clear-completed {:width (px 123)}
    [:button {:cursor "pointer"
              :color controls-color}
     [(gs/& gs/hover) {:text-decoration "underline"}]]]
   [:.filters {:list-style "none"
         :flex-grow 1
         :display "flex"
         :justify-content "center"}
    [:a {:text-decoration "none"
         :padding "3px 7px"
         :color "inherit"
         :border "1px solid transparent"
         :border-radius (px 3)}]
    [:a.selected {:border-color border-selected-filter}]]])

(defn buttons []
  [:button {:border "none"
            :font-weight "inherit"
            :font-family "inherit"}])

(defn footer []
  [:.container
   [:.footer {:grid-area      "footer"
              :display        "flex"
              :flex-direction "column"
              :align-items    "center"}
    [:p {:font-size "10px"
         :color footer-text-color
         :line-height 2}]]])

(defstyles screen
           [(all*)
            (focus)
            (body)
            (buttons)
            (header)
            (content)
            (container)
            (task-list)
            (task)
            (task-entry)
            (completed-task)
            (controls)
            (footer)])
