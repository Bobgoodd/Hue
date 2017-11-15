package com.example.mijin.hue.ProjectTab.Tab1;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by mijin on 2017-11-14.
 */

public class ClientSend {
    private AudioRecord audioRecord;
    private int frequency = 8000;
    private int channelConfiguration = AudioFormat.CHANNEL_IN_MONO;
    private int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    private int bufferSize = 2*AudioRecord.getMinBufferSize(frequency,channelConfiguration,audioEncoding);
    private short[] mAudioBuffer;
    private OutputStream os;
    private File file;
    private BufferedOutputStream bos;
    private DataOutputStream dos;
    private int mSamplesRead;
    private byte[] bytes;
    private boolean isRecording = true;
    private BufferedOutputStream outputStreamAudio;


    private void record() {
        try {
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    frequency,
                    channelConfiguration,
                    audioEncoding,
                    bufferSize);
            mAudioBuffer = new short[80];
            audioRecord.setPositionNotificationPeriod(80); //Short buffer에 80이 차이면 mRecordListener 이벤트 실행
            audioRecord.setRecordPositionUpdateListener(mRecordListener);
            audioRecord.startRecording();

            os = new FileOutputStream(file);
            bos = new BufferedOutputStream(os);
            dos = new DataOutputStream(bos);

            mSamplesRead = audioRecord.read(mAudioBuffer, 0, 80); //처음 80 을 읽어옮 이벤트 실행을 위해

        } catch (Throwable t) {
            //btnAudio.setBackgroundColor(Color.RED);
            Log.d("Recording Error", t.getLocalizedMessage());
        }
    }

    private AudioRecord.OnRecordPositionUpdateListener mRecordListener =
            new AudioRecord.OnRecordPositionUpdateListener() {
                public void onMarkerReached(AudioRecord argRecorder) {

                }
                // 지정된 주기마다 호출
                public void onPeriodicNotification(AudioRecord argRecorder) {
                    try {
                        int mSamplesRead = argRecorder.read(mAudioBuffer, 0, 80);
                        Log.d("mSamplesRead", Integer.toString(mSamplesRead));
                        int short_index = 0;
                        int byte_index = 0;
                        short[] shorts = new short[80];

                        ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(mAudioBuffer); //Short를 Byte로 변환
                        ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shorts); //Byte를 Short로 변환 (변환이 잘 되는지 확인을 위해)후 raw.pcm파일로 저장
                        for(short_index = 0; short_index < mSamplesRead; short_index++) {
                            dos.writeShort(shorts[short_index]); //raw.pcm 파일로 저장
                        }
                        Log.d("byte_index", Integer.toString(byte_index));
                        if(isRecording){
                            isRecording = false;
                            Thread.sleep(20);  //처음에 한번 Delay를 줌
                        }
                        //new WriteToServerTaskAudio().execute(bytes); //Byte를 서버로 전송.

                    } catch (Exception e) {
                        Log.d("onPeriodicNotification", e.getLocalizedMessage());
                    }
                }
            };

    public void write(byte[] bytes){
        try{
            outputStreamAudio.write(bytes);
        } catch (IOException e) {}
    }
}
