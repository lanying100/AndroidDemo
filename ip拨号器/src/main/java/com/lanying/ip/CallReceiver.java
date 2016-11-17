package com.lanying.ip;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by lanying on 2016/11/16.
 * IP拨号器的广播接收器
 * 监听拨号广播
 */
public class CallReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String phone_number = getResultData();
        setResultData("17951"+phone_number);
    }
}
