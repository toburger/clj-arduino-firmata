(ns test
  (:require [clojure.core.async :refer [<! <!! chan go sub]]
            [firmata.core :as firmata]))

(comment
  #_(def board (firmata/open-serial-board :auto-detect))
  (def board (firmata/open-serial-board "/dev/cu.usbserial-2110"))

  #_(firmata/reset-board board)

  board

  (firmata/close! board))


(def board (firmata/open-serial-board "/dev/cu.usbserial-2130"))

(defn query* [board query-f]
  (let [ch (firmata/event-channel board)
        _ (query-f board)
        event (<!! ch)]
    (firmata/release-event-channel board ch)
    event))

(defn query-protocol-version [board]
  (:version (query* board firmata/query-version)))

(comment
  (query-protocol-version board))

(defn query-firmware [board]
  (select-keys
   (query* board firmata/query-firmware)
   [:version :name]))

(comment
  (query-firmware board))

(defn query-analog-mappings [board]
  (:mappings (query* board firmata/query-analog-mappings)))

(comment
  (query-analog-mappings board))

(defn query-capabilities [board]
  (:modes (query* board firmata/query-capabilities)))

(comment
  (query-capabilities board))

(defn query-pin-state
  "pin: 0-127"
  [board pin]
  (select-keys
   (query* board #(firmata/query-pin-state % pin))
   [:pin :mode :value]))

(comment

  (sort (keys (query-capabilities board)))

  (into
   {}
   (let [pins (count (query-capabilities board))]
     (for [pin (range 0 pins)]
       (let [{:keys [pin mode value]} (query-pin-state board pin)]
         [pin (utils/nmap mode value)])))))

; CONSTANTS
  (def ^cons RED 3)
  (def ^cons GREEN 5)
  (def ^cons BLUE 6)

(comment

; SETUP
  (firmata/set-pin-mode board RED :pwm)
  (firmata/set-pin-mode board GREEN :pwm)
  (firmata/set-pin-mode board BLUE :pwm)

; FIDDLE
  (firmata/set-analog board RED 129)
  (firmata/set-analog board BLUE 100)
  (firmata/set-analog board GREEN 150))

(defn set-color! [board r g b]
  (firmata/set-analog board RED r)
  (firmata/set-analog board GREEN g)
  (firmata/set-analog board BLUE b))

(comment
; BLACK (off)
  (set-color! board 0 0 0)
; RED
  (set-color! board 255 0 0)
; GREEN
  (set-color! board 0 255 0)
; BLUE
  (set-color! board 0 0 255)
; YELLOW
  (set-color! board 255 255 0)
; LIME
  (set-color! board 192 255 0)
; PINK
  (set-color! board 255 102 178)
; PINK (corrected?) 
  (defn half [x] (/ x 2))
  (set-color! board 255 (half 102) (half 178)))

(comment
  (firmata/set-pin-mode board 5 :output)
  (firmata/set-digital board 5 :high)
  (firmata/set-digital board 5 :low))

(comment
; Read the output

  (-> board
      (firmata/set-pin-mode 8 :input)
      (firmata/enable-digital-port-reporting 8 true))

  (let [sub-ch (chan)]
    (sub (firmata/event-publisher board) [:digital-msg 8] sub-ch)
    (go (loop []
          (when-let [{:keys [pin value] :as event} (<! sub-ch)]
            (println event)
            (case [pin value]
              [8 :high] (firmata/set-digital board 5 :high)
              :default)
            (recur)))))

  (let [ch (firmata/event-channel board)
        event (<!! ch)]
    (println event))

  (firmata/enable-digital-port-reporting board 8 false))
