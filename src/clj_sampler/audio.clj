(ns clj-sampler.audio
  (:import (javax.sound.sampled AudioSystem
                                LineEvent
                                LineEvent$Type
                                LineListener)
           java.io.File))


(def sample1 (File. "/home/wise/data/AUDIO/clj-sampler/src/media/ut_radio/male/male91.wav"))

(def clip (atom nil))

(defn set-clip
  "set the clip from the File. (should be wav file)"
  [file]
  (let [new-clip (AudioSystem/getClip)
        aud-in-stream (AudioSystem/getAudioInputStream file)]
    (.open new-clip aud-in-stream)

    ;;this makes the clip really stop?
    (.addLineListener new-clip (proxy [LineListener] []
                             (update [evt]
                                     (if (= (.getType evt) LineEvent$Type/STOP)
                                       (.stop (.getLine evt))
                                       nil))))
    ;;1st try and close current clip...
    (try (.close @clip)
         (catch Exception e (prn "EXC"))
         (finally (prn "finally")))
    ;;swap in clip:
    (swap! clip (fn [_] new-clip))))


(defn set-default-clip
  "dev fn to easily load a fixed valid clip"
  []
  (set-clip sample1))


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
(defn play-clip [] (.start @clip))



