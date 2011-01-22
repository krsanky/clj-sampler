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

;; private Component buildButtonSequence(ButtonBarBuilder builder) {
;;     builder.addGriddedButtons(new JButton[] {
;;             new JButton("One")
;;             new JButton("Two")
;;             new JButton("Three")
;;     });
;;     return builder.getPanel();
;; }
(defn build-button-sequence
  ""
  [builder]
  (.addGriddedButtons builder (into-array [(JButton. "One")
                                           (JButton. "Two")
                                           (JButton. "Three")]))
  (.getPanel builder))

;; public JComponent buildPanel() {
;;     FormLayout layout = new FormLayout("right:pref:grow, 4dlu, pref");
;;     DefaultFormBuilder rowBuilder = new DefaultFormBuilder(layout);
;;     rowBuilder.setDefaultDialogBorder();
;;
;;     rowBuilder.appendSeparator("Left to Right");
;;     rowBuilder.append("Ordered", buildButtonSequence(ButtonBarBuilder.createLeftToRightBuilder()));
;;     //rowBuilder.append("Fixed",   buildIndividualButtons(ButtonBarBuilder.createLeftToRightBuilder()));
;;
;;     return rowBuilder.getPanel();
;; }
(defn build-panel
  ""
  []
  (let [layout (FormLayout. "right:pref:grow, 4dlu, pref")
        row-builder (DefaultFormBuilder. layout)]
    (doto row-builder
      (.setDefaultDialogBorder)
      (.appendSeparator "Left to Right")
      (.append "Ordered"  (build-button-sequence (ButtonBarBuilder/createLeftToRightBuilder))))
    (.getPanel row-builder)))

(defn startup
  "
  JFrame frame = new JFrame();
  frame.setTitle('Forms Tutorial :: Button Order');
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  JComponent panel = new ButtonOrderExample().buildPanel();
  frame.getContentPane().add(panel);
  frame.pack();
  frame.setVisible(true);
  "
  []
  (let [frame (JFrame.)
        panel (build-panel)]
    (.setTitle frame "Forms Tutorial :: Button Order")
    (.setDefaultCloseOperation frame WindowConstants/EXIT_ON_CLOSE)
    ;;(.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)

    ;;(-> .getContentPane (.add panel))
    (.add (.getContentPane frame) panel)
    (.pack frame)
    ;;(.setSize 341 300) ;;size needs set after .pack
    (.setVisible frame true)))

(defn main []
  (do-swing* :now startup))

