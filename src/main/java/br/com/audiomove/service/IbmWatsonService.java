package br.com.audiomove.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.speech_to_text.v1.SpeechToText;
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResults;
import com.ibm.watson.speech_to_text.v1.websocket.BaseRecognizeCallback;

public class IbmWatsonService {

	
public static void callSpeechToText() {

		final String API_KEY = "jQM01kY1cl-kdTZNdPY-5xxD1Oy2bLfP9TJ-1UY08-5s";
		final String URL = "https://api.us-south.speech-to-text.watson.cloud.ibm.com/instances/88a2f24c-d86b-4d3e-8d64-4af1fd6165be";
		final String FILE =  "C:\\Record\\Record2.wav" ;
		
		IamAuthenticator authenticator = new IamAuthenticator(API_KEY);
		SpeechToText speechToText = new SpeechToText(authenticator);
		speechToText.setServiceUrl(URL);

		try {
		  RecognizeOptions recognizeOptions = new RecognizeOptions.Builder()
		    .audio(new FileInputStream(FILE))
		    .contentType("audio/wav")
		    .model("pt-BR_BroadbandModel")
		    .keywords(Arrays.asList("cima", "baixo", "trás","frente"))
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
