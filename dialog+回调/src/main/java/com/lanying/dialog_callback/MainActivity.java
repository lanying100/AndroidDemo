package com.lanying.dialog_callback;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MyDialog.ILoginListener{
    private MyDialog mMyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMyDialog = new MyDialog(MainActivity.this);
        mMyDialog.setILoginListener(this);// 不要忘记给MyDialog对象设置监听
    }

    public void myClick(View v){
        mMyDialog.show();
    }

    // 当用户点击Dialog的 OK 按钮时调用
    @Override
    public void onSuccess(String username, String pwd) {
        Toast.makeText(MainActivity.this, "Login Success!\nusername = "+username+"\npassword = "+pwd, Toast.LENGTH_SHORT).show();
    }

    // 当用户点击Dialog的 Cancel 按钮时调用
    @Override
    public void onFailed() {
        Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
    }
}
