(ns noc.vectors.vector-walker
  (:require [clojure2d.core :as c2d]
            [fastmath.vector :as v]
            [fastmath.random :as r]
            [fastmath.core :as m])
  (:import fastmath.vector.Vec2))

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(defn draw [canvas _window frame pos]
  (let [^Vec2 new-pos (v/vec2 (m/norm (r/noise (/ frame 1000.0)) 0 1 0 500) (m/norm (r/noise (+ (/ frame 1000.0) 100.0)) 0 1 0 500))]

    (-> canvas
        (c2d/set-background 200 200 200)
        (c2d/set-color 50 150 50)
        (c2d/rect (.x new-pos) (.y new-pos) 10 10))))

(def window (c2d/show-window (c2d/canvas 500 500) "vector perlin walker" draw))