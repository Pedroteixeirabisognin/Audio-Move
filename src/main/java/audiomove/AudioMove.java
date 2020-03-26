package audiomove;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.speech_to_text.v1.SpeechToText;
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResults;
import com.ibm.watson.speech_to_text.v1.websocket.BaseRecognizeCallback;

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
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			targetDataLine.stop();
			
			
			targetDataLine.close();
			
			System.out.println("Ended Record");
			
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	
	
		IamAuthenticator authenticator = new IamAuthenticator("jQM01kY1cl-kdTZNdPY-5xxD1Oy2bLfP9TJ-1UY08-5s");
		SpeechToText speechToText = new SpeechToText(authenticator);
		speechToText.setServiceUrl("https://api.us-south.speech-to-text.watson.cloud.ibm.com/instances/88a2f24c-d86b-4d3e-8d64-4af1fd6165be");

		try {
		  RecognizeOptions recognizeOptions = new RecognizeOptions.Builder()
		    .audio(new FileInputStream("C:\\Record\\Record2.wav"))
		    .contentType("audio/wav")
		    .model("pt-BR_BroadbandModel")
		    .keywords(Arrays.asList("rato", "roeu", "roupa"))
		    .keywordsThreshold((float) 0.5)
		    .maxAlternatives(7)
		    .build();

		  BaseRecognizeCallback baseRecognizeCallback =
		    new BaseRecognizeCallback() {

		      @Override
		      public void onTranscription
		        (SpeechRecognitionResults speechRecognitionResults) {
		          System.out.println(speechRecognitionResults);
		      }

		      @Override
		      public void onDisconnected() {
		        System.exit(0);
		      }

		    };

		  speechToText.recognizeUsingWebSocket(recognizeOptions,
		    baseRecognizeCallback);
		} catch (FileNotFoundException e) {
		  e.printStackTrace();
		}

		
	
	}

}
