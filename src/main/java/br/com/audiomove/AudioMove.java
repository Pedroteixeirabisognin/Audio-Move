package br.com.audiomove;

import br.com.audiomove.service.IbmWatsonService;
import br.com.audiomove.service.RecordService;

public class AudioMove {

	public static void main(String[] args) {

		RecordService.takeVoice();

		IbmWatsonService.callSpeechToText();

	}

}
