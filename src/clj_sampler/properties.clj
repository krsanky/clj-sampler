(ns clj-sampler.properties
  (:import
   (java.util Properties)
   (java.io File
            FileInputStream
            FileOutputStream
            FileNotFoundException)))

(def props-filename (str  (System/getProperty "user.home") "/.clj-sampler.properties"))
(def props (Properties.))

(defn write-props
  "assume props is not nil"
  []
  (let [file (File. props-filename)
        file-out-stream (FileOutputStream. file)]
    (.store props file-out-stream "blah blah blah ...")
    (.close file-out-stream)))

(defn load-props
  "if no props file exists set a var, and write one"
  []
  (try
    (let [file (File. props-filename)]
      (.load props (FileInputStream. file))
      (.setProperty props "prev-props-file" "true"))
    (catch FileNotFoundException e
      (.setProperty props "prev-props-file" "false")
      (write-props))))

