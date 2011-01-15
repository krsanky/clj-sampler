(ns clj-sampler.gui2
  (:import
   javax.swing.JButton
   javax.swing.JFrame
   javax.swing.JProgressBar
   com.jgoodies.forms.layout.CellConstraints
   com.jgoodies.forms.layout.FormLayout
   com.jgoodies.forms.factories.ButtonBarFactory
   com.jgoodies.forms.builder.PanelBuilder)
  (:use
   [clojure.contrib.swing-utils
    :only (do-swing do-swing* add-action-listener)]))

(defn processing
  [items done? canceled? progress]
  (doseq [item (take-while (fn [_] (not @canceled?)) items)]
    (println item)
    (Thread/sleep 5000)
    (swap! progress inc))
  (reset! done? true))

(defn startup
  []
  (let [items     (take 5 (iterate inc 0))
        frame     (JFrame. "Example GUI")
        layout    (FormLayout. "fill:default:grow" "pref,3dlu,pref")
        cc        (CellConstraints.)
        progress  (atom 0)
        pbar      (JProgressBar. @progress (count items))
        done?     (atom false)
        done      (JButton. "Done")
        canceled? (atom false)
        cancel    (JButton. "Cancel")
        bbar      (ButtonBarFactory/buildCenteredBar (into-array [done cancel]))
        panel     (-> (PanelBuilder. layout)
                    (doto
                      (.setDefaultDialogBorder)
                      (.add pbar (.xy cc 1 1))
                      (.add bbar (.xy cc 1 3)))
                    .getPanel)]
    ; Wire up the done button.
    (.setEnabled done false)
    (add-action-listener
      done
      (fn [_]
        (do-swing
          (doto frame
            (.setVisible false)
            (.dispose)))))
    (add-watch
      done?
      ::toggle-buttons-on-done
      (fn [_ _ _ done?]
        (do-swing
          (when done?
            (.setEnabled cancel false)
            (.setEnabled done true)))))
    ; Wire up cancel button.
    (add-action-listener
      cancel
      (fn [_]
        (reset! canceled? true)))
    ; Wire up progress bar.
    (add-watch
      progress
      ::update-progress-bar
      (fn [_ _ _ v]
        (do-swing
          (.setValue pbar v))))
    (doto frame
      (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)
      (-> .getContentPane (.add panel))
      (.pack)
      (.setVisible true))
    (-> #(processing items done? canceled? progress) Thread. .start)))

(defn main
  []
  (do-swing* :now startup))

