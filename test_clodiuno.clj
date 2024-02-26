(ns test-clodiuno
  (:require [clodiuno.core :refer :all]
            [clodiuno.firmata :refer :all]))

(def board (arduino :firmata "/dev/cu.usbserial-2110"))