import java.net.URL;
import java.util.ArrayList;
import java.io.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
    ArrayList<String> sounds = new ArrayList<String>();
    private String path = "./sounds/";

    public SoundPlayer() {
        sounds.add(path + "doom.wav");
        sounds.add(path + "wilhelmScream.wav");
        sounds.add(path + "bounce.wav");
        sounds.add(path + "slayergates.wav");
    }

    public void play(int index, long start) {
        play(index, start, false);
    }

    public void play(int index, long start, boolean loop) {
        try {
            AudioInputStream aio = AudioSystem.getAudioInputStream(new File(sounds.get(index)));
            Clip clip = AudioSystem.getClip();
            clip.open(aio);
            clip.setMicrosecondPosition(start);
            clip.start();
            if (loop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeSound(int i) {
        File test = new File(sounds.get(i));
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(test));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loop(Clip clip) {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(Clip clip) {
        clip.stop();
    }
}
