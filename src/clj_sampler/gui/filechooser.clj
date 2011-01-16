(ns clj-sampler.gui.filechooser
  (:import
   (javax.swing JFileChooser)
   (java.io File)))

(def last-dir (atom (File. "/home/wise/data/AUDIO/clj-sampler/src/media/ut_radio")))

(defn get-file-selection
  "returns a File or nil"
  []
  (let [fc (JFileChooser. @last-dir)
        retval (.showOpenDialog fc nil)]
    (if (= retval JFileChooser/APPROVE_OPTION)
      (do
        (println (.getSelectedFile fc))
        (.getSelectedFile fc))
      nil)))

