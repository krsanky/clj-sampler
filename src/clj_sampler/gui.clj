(ns clj-sampler.gui
  (:import (javax.swing JFrame
                        JPanel
                        JButton
                        JOptionPane)
           (java.awt GridLayout)
           java.awt.event.ActionListener)
  (:require [clj-sampler.audio :as audio]
            clojure.contrib.swing-utils))



;; Hey, it's not there!  That's because Swing wasn't designed
;; with interactive development in mind.  To make your button
;; visible, call:
;;
;; (.revalidate button)
;;
;; The revalidate method is not something you ll read about in most Swing
;; tutorials, because in pre-compiled Java it s rarely necessary.  Basically
;; it tells Swing,  I just changed the layout, you need to redraw stuff.
;; Starting at our JButton, Swing searches up the containment hierarchy to
;; the top-level container, and redraws it.

;;GridLayout experimentLayout = new GridLayout(0,2);
(def grid-layout (GridLayout. 0 3))

(def button (JButton. "test sound"))
(def butt-0 (JButton. "0"))
(def butt-1 (JButton. "1"))
(def butt-2 (JButton. "2"))
(def butt-3 (JButton. "3"))
(def butt-4 (JButton. "4"))
(def butt-5 (JButton. "5"))
(def butt-6 (JButton. "6"))
(def butt-7 (JButton. "7"))
(def butt-8 (JButton. "8"))
(def butt-9 (JButton. "9"))

(def q-butt (JButton. "quit"))

(def panel (doto (JPanel.)
             (.add q-butt)
             (.add button)
             (.add butt-0)
             (.add butt-1)
             (.add butt-2)
             (.add butt-3)
             (.add butt-4)
             (.add butt-5)
             (.add butt-6)
             (.add butt-7)
             (.add butt-8)
             (.add butt-9)))

(.setLayout panel grid-layout)

(def frame (doto (JFrame. "frame-7")
             (.setSize 400 200)
             ;;(.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
             (.setContentPane panel)
             (.setVisible true)))

(defn say-hello []
  (JOptionPane/showMessageDialog
   nil "Hello, World!" "Greeting"
   JOptionPane/INFORMATION_MESSAGE))



;;   JButton quitButton = new JButton("Quit");
;;       quitButton.setBounds(50, 60, 80, 30);
;;       quitButton.addActionListener(new ActionListener() {
;;           public void actionPerformed(ActionEvent event) {
;;               System.exit(0);
;;          }
;;       });
;;
;;       panel.add(quitButton);
(def q-act (proxy [ActionListener] [] (actionPerformed [event] (System/exit 0))))
(.addActionListener q-butt q-act)

(def act (proxy [ActionListener] []
           ;;(actionPerformed [event] (say-hello))))
           (actionPerformed [event] (audio/play1))))
(.addActionListener button act)

