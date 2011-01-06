(ns clj-sampler.audio
  (:import (javax.sound.sampled AudioSystem)
           java.io.File))

;;File soundFile = new File("eatfood.wav");
(def snd-file (File. "/home/wise/data/AUDIO/clj-sampler/ut_radio/male/male91.wav"))

;;AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
;;(System/getProperty "java.vm.version")
(def aud-in (AudioSystem/getAudioInputStream snd-file))

;;Clip clip = AudioSystem.getClip();
(def clip (AudioSystem/getClip))

;;clip.open(audioIn);
(.open clip aud-in)


(defn play1 []
  (.start clip))


