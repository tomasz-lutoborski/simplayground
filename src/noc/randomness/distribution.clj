(ns noc.chapter0.distribution
  (:require [clojure2d.core :as c2d]
            [clojure2d.color :as c]
            [fastmath.random :as r]))

(def WIDTH 500)
(def HEIGHT 500)

(def canvas (c2d/canvas WIDTH HEIGHT))

(defn draw [canvas _window _frame _state]
  (c2d/set-color canvas (c/random-color) 40)
  (c2d/ellipse canvas (r/grand 250 30) (r/grand 250 30) 10 10))

(def window (c2d/show-window
             {:canvas canvas
              :draw-fn draw
              :fps 120
              :draw-state {:x (/ WIDTH 2) :y (/ HEIGHT 2)}}))

(c2d/with-canvas-> canvas (c2d/set-background 30 30 30))