(ns clj-sampler.gui.test.layout2
  (:import
   java.awt.Component
   (javax.swing JButton
                JComponent
                JFrame
                UIManager
                WindowConstants)
   (com.jgoodies.forms.builder ButtonBarBuilder
                               DefaultFormBuilder)
   com.jgoodies.forms.layout.FormLayout)
  (:use
   [clojure.contrib.swing-utils
    :only (do-swing do-swing* do-swing-and-wait add-action-listener)]))

(defn build-button-sequence
  ""
  [builder]
  (.addGriddedButtons builder (into-array [(JButton. "<html><font color=green>1</font></html>")
                                           (JButton. "2")
                                           (JButton. "3")]))
  (.getPanel builder))

(defn build-panel
  ""
  []
  (let [layout (FormLayout. "right:pref:grow, 4dlu, pref")
        row-builder (DefaultFormBuilder. layout)]
    (doto row-builder
      (.setDefaultDialogBorder)
      (.appendSeparator)
      (.append (build-button-sequence (ButtonBarBuilder/createLeftToRightBuilder))))
    (.getPanel row-builder)))

(defn startup
  ""
  []
  (let [frame (JFrame.)
        panel (build-panel)]
    (doto frame
      (.setTitle "Forms Tutorial :: Button Order")

      ;;(.setDefaultCloseOperation WindowConstants/EXIT_ON_CLOSE)
      (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)

      (-> .getContentPane (.add panel))
      ;;(.add (.getContentPane frame) panel)

      (.pack)
      ;;(.setSize 341 300) ;;size needs set after .pack
      (.setVisible true))))

(defn main []
  (do-swing* :now startup))

