(ns clj-sampler.gui
  (:import
   javax.swing.JButton
   javax.swing.JFrame
   com.jgoodies.forms.layout.CellConstraints
   com.jgoodies.forms.layout.FormLayout
   com.jgoodies.forms.factories.ButtonBarFactory
   com.jgoodies.forms.builder.PanelBuilder
   com.jgoodies.forms.debug.FormDebugPanel)
  (:use
   [clojure.contrib.swing-utils
    :only (do-swing do-swing* add-action-listener)]))



(defn startup
  []
  (let [items     (take 5 (iterate inc 0))
        frame     (JFrame. "Example GUI")
        layout    (FormLayout. "fill:default:grow" ;; cols
                               "pref,3dlu,pref")   ;; rows
        cc        (CellConstraints.)
        done?     (atom false)
        quit      (JButton. "quit")
        dummy     (JButton. "dummy")
        bbar      (ButtonBarFactory/buildCenteredBar (into-array [quit dummy]))

        panel     (-> (PanelBuilder. layout (FormDebugPanel.))  ;;rm the panel arg to use default "non-debug"
                      (doto (.setDefaultDialogBorder)
                        ;;(.add pbar (.xy cc 1 1))
                        (.add bbar (.xy cc 1 3)))

                      .getPanel)]


    (.setEnabled dummy false)

    ;; Wire up the quit button.
    (add-action-listener quit
                         (fn [_]
                           (do-swing
                            (doto frame
                              (.setVisible false)
                              (.dispose)))))

    (doto frame
      (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)
      (-> .getContentPane (.add panel))
      (.pack)
      (.setVisible true))))


;; --MAIN--
(defn main []
  (do-swing* :now startup))

