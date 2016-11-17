package lesson9_custom_ht1633.com.lanying.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * 前台Service
 * PendingIntent
 * Notification
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void myClick(View v){
        Intent intent = new Intent(this,MyFrontService.class);
        startService(intent);
    }
}
