package com.lanying.coolhorizontalprogressbarwithprogress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lanying.coolhorizontalprogressbarwithprogress.customview.CoolHorizontalProgressBarWithProgress;

/**
 * 水平炫酷进度条
 */
public class MainActivity extends AppCompatActivity {
    private CoolHorizontalProgressBarWithProgress mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (CoolHorizontalProgressBarWithProgress) findViewById(R.id.progressbar);

        new Thread(){
            @Override
            public void run() {
                for(int i = 0; i < 101; i ++){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mProgressBar.setProgress(i);
                }
            }
        }.start();
    }



}
