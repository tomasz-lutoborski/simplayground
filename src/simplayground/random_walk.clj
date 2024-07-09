(ns simplayground.random-walk
  (:require [clojure2d.core :as c2d]
            [clojure2d.color :as c]
            [fastmath.random :as r]))

(def WIDTH 1000)
(def HEIGHT 1000)

(def N-AGENTS 150)

(defn create-agent []
  {:x 250 :y 250 :color (c/color (r/irand 150 255) (r/irand 70 140) (r/irand 60))})

(defn create-random-agent []
  {:x (r/irand WIDTH) :y (r/irand HEIGHT) :color (c/color (r/irand 150 255) (r/irand 70 140) (r/irand 60))})

(def canvas (c2d/canvas WIDTH HEIGHT))

(defn draw [canvas window _ agents]
  (doseq [agent agents]
    (c2d/set-color canvas (:color agent) 20)
    (c2d/rect canvas (:x agent) (:y agent) 1 1))
  (mapv (fn [agent]
          (assoc agent
                 :x (unchecked-add (r/irand -1 2) (long (:x agent)))
                 :y (unchecked-add (r/irand -1 2) (long (:y agent)))))
        agents))

(def window (c2d/show-window
             {:canvas canvas
              :draw-fn draw
              :fps 120
              :draw-state (vec (repeatedly N-AGENTS create-random-agent))}))

(defn take-walk []
  (reduce + (vec (repeatedly 1000 #(rand-nth [-1 1])))))

(comment
  (c2d/window-active? window))