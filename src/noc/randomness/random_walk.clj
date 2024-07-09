(ns noc.chapter0.random-walk
  (:require [clojure2d.core :as c2d]
            [fastmath.random :as r]
            [fastmath.core :as m]))

(def WIDTH 500)
(def HEIGHT 500)

(def canvas (c2d/canvas 500 500))

; Moves in 8 directions
#_(defn draw [canvas _window _frame {:keys [x y]}]
    (c2d/set-background canvas 150 150 150)
    (c2d/rect canvas x y 10 10)
    (let [new-x (+ (r/irand -1 2) x)
          new-y (+ (r/irand -1 2) y)]
      {:x new-x :y new-y}))

; Tends to go down and right
#_(defn draw [canvas _window _frame {:keys [x y]}]
    (c2d/set-background canvas 150 150 150)
    (c2d/rect canvas x y 10 10)
    (let [new-x (let [fr (r/frand)]
                  (cond
                    (< fr 0.7) 1
                    :else -1))
          new-y (let [fr (r/frand)]
                  (cond
                    (< fr 0.7) 1
                    :else -1))]
      {:x (+ x new-x) :y (+ y new-y)}))

; Tends to move towards mouse
#_(defn draw [canvas window _frame {:keys [x y]}]
    (c2d/set-background canvas 150 150 150)
    (c2d/rect canvas x y 10 10)
    (let [mouse-x (c2d/mouse-x window)
          mouse-y (c2d/mouse-y window)
          mouse-right? (> mouse-x x)
          mouse-below? (> mouse-y y)
          new-x (case mouse-right?
                  true (let [rf (r/frand)]
                         (cond
                           (< rf 0.7) 1
                           :else (r/irand -1 2)))
                  (r/irand -1 2))
          new-y (case mouse-below?
                  true (let [rf (r/frand)]
                         (cond
                           (< rf 0.7) 1
                           :else (r/irand -1 2)))
                  (r/irand -1 2))]
      {:x (+ x new-x) :y (+ y new-y)}))


; Perlin noise - position
#_(defn draw [canvas _window _frame {:keys [tx ty]}]
    (let [x (un/map-range (r/noise tx) 0 1 0 WIDTH)
          y (un/map-range (r/noise ty) 0 1 0 HEIGHT)]
      (c2d/rect canvas x y 10 10))
    {:tx (+ tx 0.01) :ty (+ ty 0.01)})

; Perlin noise - step
(defn draw [canvas _window _frame {:keys [tx ty x y]}]
  (let [step-x (m/norm (r/noise tx) 0 1 -5 5)
        step-y (m/norm (r/noise ty) 0 1 -5 5)
        new-x (+ x step-x)
        new-y (+ y step-y)]
    (c2d/rect canvas new-x new-y 10 10)
    {:tx (+ tx 0.01) :ty (+ ty 0.01) :x new-x :y new-y}))

#_(defn draw [canvas window _frame {:keys [x y]}]
    (c2d/set-background canvas 150 150 150)
    (let [mouse-x (c2d/mouse-x window)
          mouse-y (c2d/mouse-y window)]
      (c2d/rect canvas mouse-x mouse-y 10 10)))

(defn setup [canvas _window]
  (c2d/set-background canvas 220 220 220)
  (c2d/set-color canvas 20 20 20)
  {:tx 0 :ty 1000 :x (/ WIDTH 2) :y (/ HEIGHT 2)})

(def window (c2d/show-window
             {:canvas canvas
              :draw-fn draw
              :fps 120
              :setup setup}))

(comment
  (c2d/mouse-x window))