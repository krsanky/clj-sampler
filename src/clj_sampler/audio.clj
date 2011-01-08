(ns clj-sampler.audio
  (:import (javax.sound.sampled AudioSystem
                                LineEvent
                                LineEvent$Type
                                LineListener)
           java.io.File))


;; (import javax.sound.sampled.LineEvent$Type)
;; javax.sound.sampled.LineEvent$Type
;; user=> (LineEvent$Type/STOP)
;; #<Type Stop>


;; javax.sound.sampled
;; Interface LineListener
;; javax.sound.sampled.LineEvent

(def snd-file (File. "/home/wise/data/AUDIO/clj-sampler/ut_radio/male/male91.wav"))

;;AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
(def aud-in (AudioSystem/getAudioInputStream snd-file))


;; // at this point, there is a native thread created 'behind the scenes'
;; // unless I added this, it never goes away:
;; clip.addLineListener( new LineListener() {
;;   public void update(LineEvent evt) {
;;     if (evt.getType() == LineEvent.Type.STOP) {
;;       evt.getLine().close();
;;     }
;;   }
;; });
(defn get-clip []
  (let [clip (AudioSystem/getClip)]
    (.open clip aud-in)
    (.addLineListener clip (proxy [LineListener] []
                             (update [evt]
                                     ;;(print (str "[" (.getType evt) "--"))
                                     ;;(println (str LineEvent$Type/STOP "] "))
                                     (if (= (.getType evt) LineEvent$Type/STOP)
                                       ;;(println "eq")
                                       (do
                                         (.stop (.getLine evt))
                                         (println "op"))
                                       (println "no-op")))))
    clip))

                                     ;;(if (= (.getType evt) LineEvent$Type/STOP)
                                     ;;  (.close (.getLine evt))
                                     ;;  (print "no-op")))))
;;clip))


;;(if (< number 100) "yes" "no"))


;;(doto (javax.swing.JFrame.)
;;  (addKeyListener (proxy [java.awt.event.KeyListener] []
;;       (keyPressed [e] (println (.getKeyChar e) " key pressed"))
;;       (keyReleased [e] (println (.getKeyChar e) " key released"))
;;       (keyTyped [e] (println (.getKeyChar e) " key typed"))))
;;  (setVisible true))

;;  ; Clojure
;;  (import '(javax.swing JFrame JButton JOptionPane))
;;  (import '(java.awt.event ActionListener))
;;
;;  (let [frame (JFrame. "Hello Swing")
;;       button (JButton. "Click Me")]
;;   (.addActionListener button
;;     (proxy [ActionListener] []
;;       (actionPerformed [evt]
;;         (JOptionPane/showMessageDialog  nil
;;            (str "<html>Hello from <b>Clojure</b>. Button "
;;                 (.getActionCommand evt) " clicked.")))))


;;(defn listen-temp [source target f]
;;  (.. source getDocument
;;      (addDocumentListener
;;       (proxy [DocumentListener] []
;;         (insertUpdate [e] (update-temp source target f))
;;         (removeUpdate [e] (update-temp source target f))
;;         (changedUpdate [e] )))))



;;(defn play1 [clip]
;;  (.start clip))
;;(.drain clip)
;;(.stop clip))


;;(defn play-sound [in-path]
;;  (let [the-file (File. in-path)
;;        the-stream (AudioSystem/getAudioInputStream the-file)
;;        the-source-line (AudioSystem/getSourceDataLine (.getFormat the-stream))
;;        the-buffer-size (.getBufferSize the-source-line)
;;        the-buffer (make-array Byte/TYPE the-buffer-size)]
;;    (doto the-source-line (.open) (.start))
;;    (loop [num-read (.read the-stream the-buffer 0 the-buffer-size)]
;;      (loop [offset 0]
;;        (when (< offset num-read)
;;          (recur (.write the-source-line the-buffer offset (- num-read offset)))))
;;      (let [next-read (.read the-stream the-buffer 0 the-buffer-size)]
;;        (when (>= next-read 0)
;;          (recur next-read))))
;;    (doto the-source-line (.drain) (.stop))
;;    (.close the-stream)))
;;
;; (play-sound "./farm.wav")

