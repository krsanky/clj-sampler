(ns clj-sampler.test
  (:gen-class)
  (:import (javax.swing JFrame JTextField JButton JPanel BoxLayout BorderFactory)
           (java.awt.event ActionListener)
           (java.awt GridLayout BorderLayout Dimension)))

(def numbers (ref []))
(def times-clicked (ref 0))

(defn calc
  "Gets the numbers from the vector and calculates them"
  [[n1 n2] op]
    (cond
      (= op "+") (+ n1 n2)
      (= op "*") (* n1 n2)
      (= op "-") (- n1 n2)
      (= op "/") (try (double (/ n1 n2))
                 (catch ArithmeticException _ "Cannot divide by zero."))))

(defn proof-number
  "Returns n if n is a number. Returns 0 otherwise"
  [n]
  (if (number? n) n 0))

(defn add-op-button
  "Creates a new button and adds the works too it."
  [op text button]
  (.addActionListener button
      (proxy [ActionListener] []
        (actionPerformed [e]
        (dosync
          (ref-set numbers (conj @numbers (proof-number (read-string (.getText text)))))
          (ref-set times-clicked (inc @times-clicked))
          (.grabFocus text)
          (if (= @times-clicked 2)
            (do
              (let [result (.toString (calc @numbers op))]
                (.setText text result)
                (ref-set numbers [(read-string result)])
                (ref-set times-clicked 1)))
            (do
              (.setText text ""))))))))

(defn clear-button
  "Special case clear button"
  [button text]
  (.addActionListener button
    (proxy [ActionListener] []
      (actionPerformed [e]
        (dosync
          (ref-set times-clicked 0)
          (ref-set numbers [])
          (.setText text ""))))))

;;(defn -main
(defn run
  "Main function, sets up the frame and glues everything together"
  []
  (let [frame (JFrame. "Calculator")
        add-button (JButton. "+")
        sub-button (JButton. "-")
        mul-button (JButton. "*")
        div-button (JButton. "/")
        clr-button (JButton. "Clear!")
        text-field (JTextField.)
        outter-pane (JPanel. (BorderLayout. 0 1))
        pane (JPanel. (GridLayout. 1 0))]
    (doto pane
      (.add add-button (add-op-button "+" text-field add-button))
      (.add sub-button (add-op-button "-" text-field sub-button))
      (.add mul-button (add-op-button "*" text-field mul-button))
      (.add div-button (add-op-button "/" text-field div-button))
      (.add clr-button (clear-button clr-button text-field)))
    (doto outter-pane
      (.add text-field BorderLayout/NORTH)
      (.add pane BorderLayout/EAST))
    (doto frame
      (.add outter-pane)
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
      (.setResizable false)
      (.setSize 341 100)
      (.setVisible true))))