package com.lanying.orderitem.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanying.orderitem.R;

/**
 * Created by lanying on 2016/11/14.
 * 淘宝订单项的图标
 * 明细：
 * 1、背景图片
 * 2、Title
 * 3、带圈数字（xml drawable）
 */
public class OrderIcon extends RelativeLayout{
    private ImageView mBackground;
    private TextView mTitle;
    private TextView mNum;


    public OrderIcon(Context context) {
        this(context,null);
    }

    public OrderIcon(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public OrderIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.ordericon,this,true);
        mBackground = (ImageView) view.findViewById(R.id.icon);
        mTitle = (TextView) view.findViewById(R.id.title);
        mNum = (TextView) view.findViewById(R.id.num);
    }


    public void setBackground(int resId){
        mBackground.setImageResource(resId);
    }


    public void setTitle(String title){
        mTitle.setText(title);
    }


    public void setNum(int num){
        mNum.setText(num+"");
    }
}
