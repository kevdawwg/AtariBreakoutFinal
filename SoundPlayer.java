import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class SoundPlayer {
    Clip clip;
    ArrayList<URL> sounds = new ArrayList();

    public SoundPlayer() {
        sounds.add(getClass().getResource("/sound/doomBackgroundMusic.wav"));
        System.out.println(sounds.size());
    }
    
    public void setFile(int i) {
        try {
            AudioInputStream music = AudioSystem.getAudioInputStream(sounds.get(i));
            clip = AudioSystem.getClip();
            clip.open(music);
        }
        catch(Exception e) {
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
