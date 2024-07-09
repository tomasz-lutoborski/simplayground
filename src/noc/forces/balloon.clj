(ns noc.forces.balloon
  (:require [clojure2d.core :as c2d]
            [fastmath.vector :as v]
            [fastmath.random :as r]
            [fastmath.core :as m]
            [clojure.pprint :as pp])
  (:import fastmath.vector.Vec2))

(defn create-mover [pos radius]
  {:pos pos :vel (v/vec2 0 0) :radius radius})

(defn check-bounds [^Vec2 pos ^Vec2 vel ^double radius]
  [(v/vec2 (m/constrain (.x pos) 0 500) (m/constrain (.y pos) 0 500))
   (v/emult vel (v/vec2 (if (< radius (.x pos) (- 500 radius)) 1 -1) (if (< radius (.y pos) (- 500 radius)) 1 -1)))])

(defn update-mover [mover [& forces]]
  (let [mass (* 4 m/PI (m/sq (:radius mover)))
        acc (v/div (reduce v/add forces) mass)
        [new-pos new-vel] (check-bounds (v/add (:pos mover) (:vel mover)) (v/add (:vel mover) acc) (:radius mover))]
    (assoc mover :pos new-pos :vel new-vel)))

(defn draw-mover [canvas mover]
  (let [radius (* (:radius mover) 2)]
    (c2d/ellipse canvas (get-in mover [:pos 0]) (get-in mover [:pos 1]) radius radius)))

(defn draw [canvas window frame state]
  (let [mover (or state (create-mover (v/vec2 250 250) 10))
        ;wind (v/vec2 (m/norm (r/noise (/ frame 1000)) 0 1 -200 200) 0)
        mouse (c2d/mouse-pos window)
        sub (v/sub (:pos mover) mouse)
        wind (v/mult (v/normalize sub) (/ 10000 (v/mag sub)))
        helium (v/vec2 0 -300)
        updated-mover (update-mover mover [wind helium])]
    #_(pp/pprint updated-mover)
    (-> canvas
        (c2d/set-background 220 220 220)
        (c2d/set-color 10 10 10)
        (draw-mover updated-mover))
    updated-mover))

(c2d/show-window (c2d/canvas 500 500) "balloon" draw)