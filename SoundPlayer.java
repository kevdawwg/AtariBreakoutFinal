import java.net.URL;
import java.util.ArrayList;
// import  sun.audio.*; 
import java.io.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
    Clip clip;
    ArrayList<URL> sounds = new ArrayList<URL>();

    // public SoundPlayer() {
    // sounds.add(getClass().getResource(""));
    // System.out.println(sounds.size());
    // }

    // public void setFile(int i) {
    // try {
    // AudioInputStream music = AudioSystem.getAudioInputStream(sounds.get(i));
    // clip = AudioSystem.getClip();
    // clip.open(music);
    // }
    // catch(Exception e) {
    // e.printStackTrace();
    // }
    // }

    // public void playSound(String soundFile) {
    // File f = new File("./" + soundFile);
    // AudioInputStream audioIn =
    // AudioSystem.getAudioInputStream(f.toURI().toURL());
    // Clip clip = AudioSystem.getClip();
    // clip.open(audioIn);
    // clip.start();
    // }
    public void makeSound() {
        File test = new File("/sounds/sample.wav");

        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(test));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
