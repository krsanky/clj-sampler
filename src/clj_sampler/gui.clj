(ns clj-sampler.gui
  (:import (javax.swing JFrame
                        JPanel
                        JButton
                        JOptionPane)
           java.awt.event.ActionListener)
  (:require [clj-sampler.audio :as audio]))

(def button (JButton. "Click Me!"))

(def panel (doto (JPanel.)
             (.add button)))

(def frame (doto (JFrame. "Hello Frame")
             (.setSize 600 800)
             (.setContentPane panel)
             (.setVisible true)))

(defn say-hello []
  (JOptionPane/showMessageDialog
   nil "Hello, World!" "Greeting"
   JOptionPane/INFORMATION_MESSAGE))

(def act (proxy [ActionListener] []
           (actionPerformed [event] (say-hello))))
           ;;(actionPerformed [event] (audio/play1))))

(.addActionListener button act)

