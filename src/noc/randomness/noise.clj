(ns noc.chapter0.noise
  (:require [clojure2d.core :as c2d]
            [clojure2d.pixels :as p]
            [fastmath.random :as r]
            [fastmath.core :as m]
            [clojure2d.color :as c]))

(def WIDTH 500)
(def HEIGHT 500)

(def canvas (c2d/canvas WIDTH HEIGHT))

(def window (c2d/show-window canvas "noise"))

(let [pixels (p/to-pixels canvas)]
  (dotimes [x WIDTH]
    (dotimes [y HEIGHT]
      (let [xoff (m/norm x 0 WIDTH 0 (* 0.002 WIDTH))
            yoff (m/norm y 0 HEIGHT 0 (* 0.2 HEIGHT))
            b (* 255 (r/noise xoff yoff))]
        (p/set-color! pixels x y (c/color b b (r/irand 255))))))
  (p/set-canvas-pixels! canvas pixels))

(defn draw [canvas _window frame _state]
  (let [pixels (p/to-pixels canvas)]
    (dotimes [x WIDTH]
      (dotimes [y HEIGHT]
        (let [xoff (m/norm x 0 WIDTH 0 (* 0.002 WIDTH))
              yoff (m/norm y 0 HEIGHT 0 (* 0.2 HEIGHT))
              b (* 255 (r/noise xoff yoff))]
          (p/set-color! pixels x y (c/color b b (m/norm (r/noise (/ frame 1000)) 0 1 0 255))))))
    (p/set-canvas-pixels! canvas pixels)))

(def canvas2 (c2d/canvas WIDTH HEIGHT))

(def window2 (c2d/show-window
              {:canvas canvas2
               :draw-fn draw}))