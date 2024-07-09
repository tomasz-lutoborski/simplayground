(ns noc.chapter1.bouncing-ball
  (:require [clojure2d.core :as c2d]
            [fastmath.core :as m]
            [fastmath.vector :as v]
            [clojure.pprint :as pp])
  (:import fastmath.vector.Vec2))

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(def WIDTH 500)
(def HEIGHT 500)

; No vectors
#_(defn boundary-check
    ^double [^double mx ^double pos]
    (if (< 0 pos mx) 1.0 -1.0))

#_(defn draw [canvas _ _ state]
    (let [[^double x ^double y ^double xspeed ^double yspeed] (or state [250 250 5 7])
          nx (+ x xspeed)
          ny (+ y yspeed)]

      (-> canvas
          (c2d/set-background 220 220 220)
          (c2d/set-color 20 20 20)
          (c2d/ellipse nx ny 10 10))

      [nx ny
       (* xspeed (boundary-check WIDTH nx))
       (* yspeed (boundary-check HEIGHT ny))]))

; With vectors
(defn boundary-check
  [^double maxw ^double maxh ^Vec2 v]
  (v/vec2 (if (< 0 (.x v) maxw) 1.0 -1.0)
          (if (< 0 (.y v) maxh) 1.0 -1.0)))

#_(defn draw [canvas _ _ state]
    (let [[position velocity] (or state [(v/vec2 100 100) (v/vec2 2.3 5.3)])
          ^Vec2 nposition (v/add position velocity)]

      (-> canvas
          (c2d/set-background 220 220 220)
          (c2d/set-color 20 20 20)
          (c2d/ellipse (.x nposition) (.y nposition) 10 10))

      [nposition (v/emult velocity (boundary-check WIDTH HEIGHT nposition))]))

; With velocity and acceleration
(defn create-mover [^Vec2 pos ^double mass]
  {:pos pos :vel (v/vec2 0 0) :acc (v/vec2 0 0) :mass mass})

(defn apply-force [mover f]
  (update mover :acc #(v/add % (v/div f (:mass mover)))))

(defn update-mover [mover]
  (let [new-pos (v/add (:pos mover) (:vel mover))
        new-vel (v/emult (v/add (:vel mover) (:acc mover)) (boundary-check WIDTH HEIGHT new-pos))
        new-acc (v/emult (:acc mover) (boundary-check WIDTH HEIGHT new-pos))]
    {:pos new-pos :vel new-vel :acc new-acc :mass (:mass mover)}))

#_(defn draw [canvas _ _ state]
    (let [{:keys [pos] :as mover} (or state (apply-force (create-mover (v/vec2 250 250) 10) (v/vec2 1 2)))
          updated-mover (update-mover mover)]

      (-> canvas
          (c2d/set-background 220 220 220)
          (c2d/set-color 20 20 20)
          (c2d/ellipse (.x pos) (.y pos) 10 10))
      updated-mover))

; Accelerating towards mouse
(defn draw [canvas window _ state]
  (let [{:keys [pos] :as mover} (or state (create-mover (v/vec2 250 250) 10))
        mouse-pos (c2d/mouse-pos window)
        dir (v/sub mouse-pos pos)
        dir-norm (v/mult (v/normalize dir) (/ 10 (v/mag dir)))
        updated-mover (assoc (update-mover mover) :acc dir-norm)]

    (-> canvas
        (c2d/set-background 220 220 220)
        (c2d/set-color 20 20 20)
        (c2d/ellipse (.x pos) (.y pos) 10 10))
    updated-mover))

(def window (c2d/show-window (c2d/canvas WIDTH HEIGHT) "bouncing" draw))