package com.lanying.phoneeavesdropper;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * 电话窃听器
 * 思路分析：
 * 1）开机自启（静态注册广播接收器，注意添加权限）
 * 2）监听电话状态（权限）
 * 3）MediaRecorder录制通话音频（SDK帮助文档，权限）
 * 4）保存到SD卡（注意权限）——> Android 6.0比较坑，建议保存在Context.getExternalFilesDir() ： SDCard/Android/data/应用包名/files/
 */
public class MainActivity extends AppCompatActivity {
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 动态申请写SD卡权限
         */
        writeSDcard();

    }
    private void writeSDcard() {
        if(Build.VERSION.SDK_INT >= 23) {
            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_CONTACTS);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECEIVE_BOOT_COMPLETED},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }else{
            // 低版本直接开启录音服务
            startRecordService();
        }
    }

    private void startRecordService() {
        // 动态授权成功，开启录音服务
        Intent service = new Intent(this,PhoneEavesdropService.class);
        startService(service);
    }


    /**
     * 授权结果
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_CODE_ASK_PERMISSIONS:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "SD卡授权成功\nActivity 打开电话窃听服务", Toast.LENGTH_SHORT).show();
                    startRecordService();
                }else{
                    Toast.makeText(MainActivity.this, "写SD卡动态授权失败", Toast.LENGTH_SHORT).show();
                }

                if(grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "开机自启授权成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "开机自启授权失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
