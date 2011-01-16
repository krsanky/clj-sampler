(ns clj-sampler.gui.filechooser
  (:import
   (javax.swing JFileChooser)))

(def fc (JFileChooser.))

(defn get-file-selection
  "returns a File or nil"
  []
  (let [fc (JFileChooser.)
        retval (.showOpenDialog fc nil)]
    (if (= retval JFileChooser/APPROVE_OPTION)
      (do
        (println (.getSelectedFile fc))
        (.getSelectedFile fc))
      nil)))
;; #<File /home/wise/scripts/clj>

