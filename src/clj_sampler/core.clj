(ns clj-sampler.core
  (:import (javax.swing JFrame
                        JPanel
                        JButton
                        JOptionPane)
           (java.awt.event.ActionListener)))

;;(import 'java.awt.event.ActionListener)
;;(import 'javax.swing.JOptionPane)

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

(import 'java.awt.event.ActionListener)
(def act (proxy [ActionListener] []
           (actionPerformed [event] (say-hello))))

(.addActionListener button act)
