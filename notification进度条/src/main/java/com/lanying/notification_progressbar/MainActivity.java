package com.lanying.notification_progressbar;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

/**
 * 在Notification中显示进度条
 */
public class MainActivity extends AppCompatActivity {
    private int notificationId = 12345;
    private NotificationManager mNotiManager;
    private Notification.Builder mBuilder;
    private Notification mNotification;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int progress = msg.what;
            Log.d("lanying","notification progressBar ... "+progress);
            mNotification.contentView.setProgressBar(R.id.noti_progressbar,100,progress,false);
            mNotification.contentView.setTextViewText(R.id.tv_percent,progress+"%");
            mNotiManager.notify(notificationId,mNotification);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mNotification = new Notification.Builder(this)
//                .setContentTitle("Title")
//                .setContentText("ContentText")
//                .setSubText("subText")
//                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .build();


        mNotification.contentView = new RemoteViews(getPackageName(),
                R.layout.notification_progressbar);

        mNotification.contentView.setImageViewResource(R.id.image_head,R.mipmap.ic_launcher);
        // 设置进度条，最大值 为100,当前值为0，最后一个参数为true时显示条纹
        mNotification.contentView.setProgressBar(R.id.noti_progressbar, 100, 0, false);
        mNotification.contentView.setTextViewText(R.id.tv_percent, "...");

    }

    public void myClick(View v){
        //mNotiManager.notify(notificationId,mNotification);
        new Thread(){
            @Override
            public void run() {
                int progress = 0;
                while(progress < 100){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 不可以直接在子线程中更新Notification进度条的进度，木有效果
                    mHandler.sendEmptyMessage(progress+=10);

                }

                // 执行完成
                mNotiManager.cancel(notificationId);

            }
        }.start();
    }
}
