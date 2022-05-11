import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;


public class NewSoundPlayer {
	ArrayList<Clip> clipList;// this is the Clip that will be played
	ArrayList<String> fileList;
	public NewSoundPlayer(){
		clipList = new ArrayList<Clip>();
		fileList = new ArrayList<String>();
	}
	public void loadFiles(){
		loadFiles(fileList);
	}
	public void loadFiles(ArrayList<String>fileList){
		Clip clip= null;
		for(String fn:fileList){
			try {
				clip = null;
		        // From file that has been placed in the project folder
					//String fname = "ding.wav", music = "spacemusic.au";
				AudioInputStream stream = AudioSystem.getAudioInputStream(new File(fn));
					//AudioInputStream stream2 = AudioSystem.getAudioInputStream(new File("ding"));
		 
				AudioFormat format = stream.getFormat();
				if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
					format = new AudioFormat(
	                    AudioFormat.Encoding.PCM_SIGNED,
	                    format.getSampleRate(),
	                    format.getSampleSizeInBits()*2,
	                    format.getChannels(),
	                    format.getFrameSize()*2,
	                    format.getFrameRate(),
	                    true);        // big endian
					stream = AudioSystem.getAudioInputStream(format, stream);
				}
	    
	        // Create the clip
				DataLine.Info info = new DataLine.Info(
						Clip.class, stream.getFormat(), ((int)stream.getFrameLength()*format.getFrameSize()));
				System.out.println(info);
				clip = (Clip) AudioSystem.getLine(info);
	    
	        // This method does not return until the audio file is completely loaded
				clip.open(stream);
				System.out.println(fn+" clip is: " + clip);
				clipList.add(clip);

		    }catch (Exception e) {
		    	System.out.println("Problem with sound loading \n"+ e);
			}
		}
	}
	
	public  void play(int i) {
		
		//if(i<clipList.size()){
		Clip c = clipList.get(i);
		if(c !=null){// && !c.isRunning()){
			c.stop();
			c.setFramePosition(0); 
			c.start();
		}
	}
	public void loop(int i) {
        Clip c = clipList.get(i);
		c.loop(Clip.LOOP_CONTINUOUSLY);
    }
	
	

}