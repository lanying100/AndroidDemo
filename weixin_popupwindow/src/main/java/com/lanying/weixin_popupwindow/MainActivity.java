package com.lanying.weixin_popupwindow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.lanying.weixin_popupwindow.customview.MyPopupWindow;

/**
 * 微信效果 PopupWindow
 */
public class MainActivity extends Activity {
    private ImageView mImageView;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//必须继承于Activity

        setContentView(R.layout.activity_main);

        init();


        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.showAsDropDown(v);
            }
        });

    }

    private void init() {
        mImageView = (ImageView) findViewById(R.id.image_title);
        mPopupWindow = new MyPopupWindow(MainActivity.this);
    }
}
