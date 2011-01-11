(ns clj-sampler.audio
  (:import (javax.sound.sampled AudioSystem
                                LineEvent
                                LineEvent$Type
                                LineListener)
           java.io.File))


(def snd-file (File. "/home/wise/data/AUDIO/clj-sampler/ut_radio/male/male91.wav"))
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
                                       (println "no-op"))))) ;;nil
    clip))


;;;; Here's code to stop and replay for successive plays:
;;  // Play or Re-play the sound effect from the beginning, by rewinding.
;;    public void play() {
;;       if (volume != Volume.MUTE) {
;;          if (clip.isRunning())
;;             clip.stop();   // Stop the player if it is still running
;;          clip.setFramePosition(0); // rewind to the beginning
;;          clip.start();     // Start playing
;;       }
;;    }



;;(defn listen-temp [source target f]
;;  (.. source getDocument
;;      (addDocumentListener
;;       (proxy [DocumentListener] []
;;         (insertUpdate [e] (update-temp source target f))
;;         (removeUpdate [e] (update-temp source target f))
;;         (changedUpdate [e] )))))


(def clip (get-clip))

(defn play1 []
  (.start clip))
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

