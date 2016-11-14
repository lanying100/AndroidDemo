package com.lanying.orderitem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lanying.orderitem.customview.OrderIcon;

public class MainActivity extends AppCompatActivity {
    private OrderIcon mOrderIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOrderIcon = (OrderIcon) findViewById(R.id.order_icon);

        mOrderIcon.setTitle("朋友圈");
        mOrderIcon.setBackground(R.mipmap.ic_launcher);
        mOrderIcon.setNum(6);
    }
}
