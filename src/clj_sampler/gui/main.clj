(ns clj-sampler.gui.main
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
    :only (do-swing do-swing* do-swing-and-wait add-action-listener)])
  (:require [clj-sampler.audio :as audio]
            [clj-sampler.gui.filechooser :as filechooser]))



(defn startup
  []
  (let [frame     (JFrame. "clj sampler")

        layout    (FormLayout. "fill:default:grow" ;; cols
                               "pref,3dlu,pref")   ;; rows

        cc        (CellConstraints.)
        done?     (atom false)
        quit      (JButton. "quit")
        play      (JButton. "play")

        load-sample (JButton. "load")


        bbar      (ButtonBarFactory/buildCenteredBar (into-array [load-sample play quit]))

        panel     (-> (PanelBuilder. layout (FormDebugPanel.))  ;;rm the panel arg to use default "non-debug"
                      (doto (.setDefaultDialogBorder)
                        ;;(.add pbar (.xy cc 1 1))
                        (.add bbar (.xy cc 1 3)))

                      .getPanel)]


    ;;(.setEnabled play false)

    ;; Wire up the quit button.
    (add-action-listener quit
                         (fn [_]
                           (do-swing
                            (doto frame
                              (.setVisible false)
                              (.dispose)))))


    (defn load-sample-do
      [_]
      (do-swing
       (let [file (filechooser/get-file-selection)]
         (prn file "<<<here>>>")
         (audio/set-clip file))))

    (add-action-listener load-sample load-sample-do)


    ;; PLAY
    ;; (def act (proxy [ActionListener] []
    ;;            ;;(actionPerformed [event] (say-hello))))
    ;;            (actionPerformed [event] (audio/play1))))
    ;; (.addActionListener button act)
    (add-action-listener play (fn [_] (do-swing (audio/play-clip))))


    ;; I find that setPreferredSize tends to work more often than setSize
    ;; although as jwentig has mentioned it is not guaranteed. A subsequent
    ;; call to pack(), validate() or invalidate() often overrides any
    ;; previous call to setting the size anyway.
    (doto frame
      (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)
      (-> .getContentPane (.add panel))

      (.pack)
      ;;(.setSize 341 300) ;;size needs set after .pack
      (.setVisible true))))


;; --MAIN--
(defn main []
  (do-swing* :now startup))

