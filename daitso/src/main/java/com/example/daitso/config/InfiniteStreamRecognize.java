package com.example.daitso.config;

import com.amazonaws.auth.policy.Resource;
import com.google.api.gax.core.FixedCredentialsProvider;

import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.cloud.speech.v1p1beta1.RecognitionConfig;
import com.google.cloud.speech.v1p1beta1.SpeechClient;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1p1beta1.StreamingRecognitionConfig;
import com.google.cloud.speech.v1p1beta1.StreamingRecognitionResult;
import com.google.cloud.speech.v1p1beta1.StreamingRecognizeRequest;
import com.google.cloud.speech.v1p1beta1.StreamingRecognizeResponse;


import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1p1beta1.SpeechSettings;

import com.google.protobuf.ByteString;
import com.google.protobuf.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.TargetDataLine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class InfiniteStreamRecognize {
	
	//넘으면 종료가 아닌 재요청을 보냄
  //private static final int STREAMING_LIMIT = 290000; // ~5 minute
  private static final int STREAMING_LIMIT = 10000; // 10초만 스트리밍 가능
  public static final String RED = "\033[0;31m";
  public static final String GREEN = "\033[0;32m";
  public static final String YELLOW = "\033[0;33m";

  // Creating shared object
  private static volatile BlockingQueue<byte[]> sharedQueue = new LinkedBlockingQueue<byte[]>();
  private static TargetDataLine targetDataLine;
  private static int BYTES_PER_BUFFER = 6400; // buffer size in bytes

  private static int restartCounter = 0;
  private static ArrayList<ByteString> audioInput = new ArrayList<ByteString>();
  private static ArrayList<ByteString> lastAudioInput = new ArrayList<ByteString>();
  private static int resultEndTimeInMS = 0;
  private static int isFinalEndTime = 0;
  private static int finalRequestEndTime = 0;
  private static boolean newStream = true;
  private static double bridgingOffset = 0;
  private static boolean lastTranscriptWasFinal = false;
  private static StreamController referenceToStreamController;
  private static ByteString tempByteString;
  private static String str;

  public static String doThat(String... args) {
	  
    InfiniteStreamRecognizeOptions options = InfiniteStreamRecognizeOptions.fromFlags(args);
    str = "";
    if (options == null) {
      // Could not parse.
      System.out.println("Failed to parse options.");
      System.exit(1);
    }

    try {
    	if(targetDataLine != null) {
    		//기존 오디오 닫아주기
    		targetDataLine = null;
    		//targetDataLine.stop(); // 녹음을 멈춥니다.
    		//targetDataLine.close(); // 오디오 라인을 닫습니다.    		
    	}
      return infiniteStreamingRecognize(options.langCode);
    } catch (Exception e) {
      System.out.println("Exception caught: " + e);
      return null;
    }
  }
  public static void doThatShit() {
	  if(referenceToStreamController != null) {
		  str = "";
		  referenceToStreamController.cancel(); // remove Observer
		  targetDataLine.stop(); // 녹음을 멈춥니다.
		  targetDataLine.close(); // 오디오 라인을 닫습니다.		  
	  }
  }

  public static String convertMillisToDate(double milliSeconds) {
    long millis = (long) milliSeconds;
    DecimalFormat format = new DecimalFormat();
    format.setMinimumIntegerDigits(2);
    return String.format(
        "%s:%s /",
        format.format(TimeUnit.MILLISECONDS.toMinutes(millis)),
        format.format(
            TimeUnit.MILLISECONDS.toSeconds(millis)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
  }

  /** Performs infinite streaming speech recognition */
  public static String infiniteStreamingRecognize(String languageCode) throws Exception {

    // Microphone Input buffering
    class MicBuffer implements Runnable {

      @Override
      public void run() {
        System.out.println(YELLOW);
        System.out.println("Start speaking...Press Ctrl-C to stop");
        targetDataLine.start();
        byte[] data = new byte[BYTES_PER_BUFFER];
        while (targetDataLine.isOpen()) {
          try {
            int numBytesRead = targetDataLine.read(data, 0, data.length);
            if ((numBytesRead <= 0) && (targetDataLine.isOpen())) {
              continue;
            }
            sharedQueue.put(data.clone());
          } catch (InterruptedException e) {
            System.out.println("Microphone input buffering interrupted : " + e.getMessage());
          }
        }
      }
    }
    
    String filePath = "keys/kcc-final-prj-c26abc6adbe3.json";

    ClassPathResource resource = new ClassPathResource(filePath);
   
    InputStream inputStream = resource.getInputStream();
    Credentials credentials = GoogleCredentials.fromStream(inputStream);
    SpeechSettings settings = SpeechSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
    // Creating microphone input buffer thread
    MicBuffer micrunnable = new MicBuffer();
    Thread micThread = new Thread(micrunnable);
    ResponseObserver<StreamingRecognizeResponse> responseObserver = null;
    try (SpeechClient client = SpeechClient.create(settings)) {
      ClientStream<StreamingRecognizeRequest> clientStream;
      responseObserver =
          new ResponseObserver<StreamingRecognizeResponse>() {

            ArrayList<StreamingRecognizeResponse> responses = new ArrayList<>();

            public void onStart(StreamController controller) {
              referenceToStreamController = controller;
            }

            public void onResponse(StreamingRecognizeResponse response) {
              responses.add(response);
              StreamingRecognitionResult result = response.getResultsList().get(0);
              Duration resultEndTime = result.getResultEndTime();
              resultEndTimeInMS =
                  (int)
                      ((resultEndTime.getSeconds() * 1000) + (resultEndTime.getNanos() / 1000000));
              double correctedTime =
                  resultEndTimeInMS - bridgingOffset + (STREAMING_LIMIT * restartCounter);

              SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
              if (result.getIsFinal()) {
                System.out.print(GREEN);
                System.out.println("그린티");
                System.out.print("\033[2K\r");
                System.out.printf(
                    "%s: %s [confidence: %.2f]\n",
                    convertMillisToDate(correctedTime),
                    alternative.getTranscript(),
                    alternative.getConfidence());
                isFinalEndTime = resultEndTimeInMS;
                str = alternative.getTranscript();
                lastTranscriptWasFinal = true;
              } else {
                System.out.print(RED);
                System.out.println("레드티");
                System.out.print("\033[2K\r");
                System.out.printf(
                    "%s: %s", convertMillisToDate(correctedTime), alternative.getTranscript());
                lastTranscriptWasFinal = false;
              }
            }

            public void onComplete() {}

            public void onError(Throwable t) {}
          };
      clientStream = client.streamingRecognizeCallable().splitCall(responseObserver);
      System.out.println("flag3");

      RecognitionConfig recognitionConfig =
          RecognitionConfig.newBuilder()
              .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
              .setLanguageCode(languageCode)
              .setSampleRateHertz(16000)
              .build();
      System.out.println("flag4");

      StreamingRecognitionConfig streamingRecognitionConfig =
          StreamingRecognitionConfig.newBuilder()
              .setConfig(recognitionConfig)
              .setInterimResults(true)
              .build();

      StreamingRecognizeRequest request =
          StreamingRecognizeRequest.newBuilder()
              .setStreamingConfig(streamingRecognitionConfig)
              .build(); // The first request in a streaming call has to be a config

      clientStream.send(request);

      try {
        // SampleRate:16000Hz, SampleSizeInBits: 16, Number of channels: 1, Signed: true,
        // bigEndian: false
        AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);
        DataLine.Info targetInfo =
            new Info(
                TargetDataLine.class,
                audioFormat); // Set the system information to read from the microphone audio
        // stream

        if (!AudioSystem.isLineSupported(targetInfo)) {
          System.out.println("Microphone not supported");
			/* doThatShit(); */
          System.out.println(targetInfo);
        }
        // Target data line captures the audio stream the microphone produces.
        targetDataLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
        if (targetDataLine != null && targetDataLine.isOpen()) {
            targetDataLine.stop(); // 녹음을 멈춥니다.
            targetDataLine.close(); // 오디오 라인을 닫습니다.
        }

        System.out.println("flag9");
        targetDataLine.open(audioFormat);
        System.out.println("flag10");
        micThread.start();

        long startTime = System.currentTimeMillis();
        //여기서 재요청
        while (true) {

          long estimatedTime = System.currentTimeMillis() - startTime;

          if (estimatedTime >= STREAMING_LIMIT) {
        	  System.out.println("게임");
        	  	
            clientStream.closeSend();
            referenceToStreamController.cancel(); // remove Observer
            targetDataLine.stop(); // 녹음을 멈춥니다.
            targetDataLine.close(); // 오디오 라인을 닫습니다.
            System.out.println("오버");

			/*
			 * if (resultEndTimeInMS > 0) { finalRequestEndTime = isFinalEndTime; }
			 * resultEndTimeInMS = 0;
			 * 
			 * lastAudioInput = null; lastAudioInput = audioInput; audioInput = new
			 * ArrayList<ByteString>();
			 * 
			 * restartCounter++;
			 * 
			 * if (!lastTranscriptWasFinal) { System.out.print('\n'); break; }
			 * 
			 * newStream = true;
			 * 
			 * clientStream =
			 * client.streamingRecognizeCallable().splitCall(responseObserver);
			 * 
			 * request = StreamingRecognizeRequest.newBuilder()
			 * .setStreamingConfig(streamingRecognitionConfig) .build();
			 * 
			 * System.out.println(YELLOW); System.out.printf("%d: RESTARTING REQUEST\n",
			 * restartCounter * STREAMING_LIMIT);
			 * 
			 * startTime = System.currentTimeMillis();
			 */

          } else {

            if ((newStream) && (lastAudioInput.size() > 0)) {
              // if this is the first audio from a new request
              // calculate amount of unfinalized audio from last request
              // resend the audio to the speech client before incoming audio
              double chunkTime = STREAMING_LIMIT / lastAudioInput.size();
              // ms length of each chunk in previous request audio arrayList
              if (chunkTime != 0) {
                if (bridgingOffset < 0) {
                  // bridging Offset accounts for time of resent audio
                  // calculated from last request
                  bridgingOffset = 0;
                }
                if (bridgingOffset > finalRequestEndTime) {
                  bridgingOffset = finalRequestEndTime;
                }
                int chunksFromMs =
                    (int) Math.floor((finalRequestEndTime - bridgingOffset) / chunkTime);
                // chunks from MS is number of chunks to resend
                bridgingOffset =
                    (int) Math.floor((lastAudioInput.size() - chunksFromMs) * chunkTime);
                // set bridging offset for next request
                for (int i = chunksFromMs; i < lastAudioInput.size(); i++) {
                  request =
                      StreamingRecognizeRequest.newBuilder()
                          .setAudioContent(lastAudioInput.get(i))
                          .build();
                  clientStream.send(request);
                }
              }
              newStream = false;
            }

            tempByteString = ByteString.copyFrom(sharedQueue.take());

            request =
                StreamingRecognizeRequest.newBuilder().setAudioContent(tempByteString).build();

            audioInput.add(tempByteString);
          }
          //시작과 동시에 아무 말 안해도 계속 돔
          if (lastTranscriptWasFinal) {
        	  clientStream.closeSend();
              referenceToStreamController.cancel(); // remove Observer
            System.out.print('\n');
            targetDataLine.stop(); // 녹음을 멈춥니다.
            targetDataLine.close(); // 오디오 라인을 닫습니다.
            lastTranscriptWasFinal = false;
			return str; 

          }
          clientStream.send(request);
        }
      } catch (Exception e) {
        System.out.println(e);
      }
    }
    //speech의 while문이 종료되도 여기로 안오면 위에서 return 해주자
    return str;
  }
}
// [END speech_transcribe_infinite_streaming]