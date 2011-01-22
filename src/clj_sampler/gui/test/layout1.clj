(ns clj-sampler.gui.test.layout1
  (:import
   (javax.swing JButton
                JFrame
                JFileChooser)
   (com.jgoodies.forms.layout CellConstraints
                              FormLayout)
   com.jgoodies.forms.factories.ButtonBarFactory
   com.jgoodies.forms.builder.PanelBuilder
   com.jgoodies.forms.debug.FormDebugPanel)
  (:use
   [clojure.contrib.swing-utils
    :only (do-swing do-swing* do-swing-and-wait add-action-listener)]))



(defn startup
  []
  (let [frame     (JFrame. "test")

        layout    (FormLayout. "center:pref:grow,3dlu,center:pref:grow,3dlu,center:pref:grow"
                               "pref,3dlu,pref")   ;; rows

        cc        (CellConstraints.)

        quit      (JButton. "quit")
        play      (JButton. "play")
        load-sample (JButton. "load")
        bbar (ButtonBarFactory/buildCenteredBar (into-array [load-sample play quit]))

        test1 (JButton. "test1")

        panel (-> (PanelBuilder. layout (FormDebugPanel.))  ;;rm the panel arg to use default "non-debug"
                  (doto (.setDefaultDialogBorder)
                    (.add bbar (.xy cc 1 3))
                    (.add test1 (.xy cc 1 3)))
                  .getPanel)]

    (doto frame
      (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)
      (-> .getContentPane (.add panel))
      (.pack)
      ;;(.setSize 341 300) ;;size needs set after .pack
      (.setVisible true))))

(defn main []
  (do-swing* :now startup))

