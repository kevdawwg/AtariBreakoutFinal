import java.util.ArrayList;
import java.io.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
    Clip clip;
    ArrayList<String> sounds = new ArrayList<String>();
    AudioInputStream audio;
    private String path = "./sounds/";

    public SoundPlayer() {
        sounds.add(path + "doom.wav");
        sounds.add(path + "wilhelmScream.wav");
        sounds.add(path + "bounce.wav");
        sounds.add(path + "powerUp.wav");
    }

    public void play(int index, int start) {
        try {
            audio = AudioSystem.getAudioInputStream(new File(sounds.get(index)));
            clip = AudioSystem.getClip();
            clip.open(audio);
            this.clip.setMicrosecondPosition(start);
            clip.start();
          } 
          catch(Exception e) {
            e.printStackTrace();
          } 
    }

    public void makeSound(int i) {
        File test = new File(sounds.get(i));

        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(test));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
