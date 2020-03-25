package audiomove;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class AudioMove {

	public static void main(String[] args) {

		AudioFormat format = new AudioFormat(16000, 8, 2, true, true);
		
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		
		if(!AudioSystem.isLineSupported(info)) {
			System.out.println("Line is not suported");
		}
		
		try {
			AudioSystem.getLine(info);
			final TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(info);

			targetDataLine.open();
			
			System.out.println("Starting Recording");
			
			targetDataLine.start();
			
			
			Thread stoper = new Thread(new Runnable() {
				
				public void run() {
					
					AudioInputStream audioStream = new AudioInputStream(targetDataLine);
					
					File wavFile = new File("C://Record//Record2.wav");
					
						try {
							AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, wavFile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	
					
				}
			});
			stoper.start();

		
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			targetDataLine.stop();
			
			
			targetDataLine.close();
			
			System.out.println("Ended Record");
			
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	
	
		
	
	}

}
