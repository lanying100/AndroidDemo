package com.lanying.phoneeavesdropper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by lanying on 2016/11/17.
 * 开机广播接收器
 * 任务：开机时开启Service
 */
public class BootReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        // 开机后立刻开启Service
        Intent service = new Intent(context,PhoneEavesdropService.class);
        context.startService(service);
        Toast.makeText(context, "Broadcast Receiver 打开电话窃听服务", Toast.LENGTH_SHORT).show();
    }
}
