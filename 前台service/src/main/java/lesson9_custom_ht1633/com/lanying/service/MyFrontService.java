package lesson9_custom_ht1633.com.lanying.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by lanying on 2016/11/16.
 */
public class MyFrontService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        Notification notification = new Notification.Builder(MyFrontService.this)
                .setContentTitle("Title")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("text")
                .setContentIntent(pendingIntent)
                .build();

//        NotificationManager notiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notiManager.notify(0,notification);

        // 不需要NotificationManager来通知
        startForeground(1,notification);// 将服务置为前台Service



        Log.d("lanying","onCreate executed !");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
