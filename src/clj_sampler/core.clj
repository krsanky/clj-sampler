(ns clj-sampler.core)


;;(def frame (JFrame. "Hello Frame"))
;;(.setSize frame 200 200)
;;(.setVisible frame true)


(import '(javax.swing JFrame JPanel JButton))

(def button (JButton. "Click Me!"))

(def panel (doto (JPanel.)
             (.add button)))

(def frame (doto (JFrame. "Hello Frame")
             (.setSize 200 200)
             (.setContentPane panel)
             (.setVisible true)))
