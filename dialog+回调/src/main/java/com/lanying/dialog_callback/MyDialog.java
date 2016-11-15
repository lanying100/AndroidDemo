package com.lanying.dialog_callback;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by lanying on 2016/11/12.
 */
public class MyDialog extends Dialog implements View.OnClickListener{

    // 1、定义接口（可以定义在外部）
    public interface ILoginListener {
        public abstract void onSuccess(String username,String pwd);// 点击OK时的回调方法（需要回传数据，所以有参）
        public abstract void onFailed();// 点击Cancel时的回调方法（不需要回传数据，所以无参）
    }

    // 2、持有接口引用（不久之后的数据接收方对象）
    private ILoginListener mListener;

    // 3、公开的赋值方法（用于给私有的接口引用赋值）
    public void setILoginListener(ILoginListener l){
        mListener = l;
    }


    private EditText etUsername,etPassword;
    private Button btnOk,btnCancel;

    public MyDialog(Context context) {
        super(context);
    }

    public MyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);

        init();

        setListener();
    }

    private void setListener() {
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void init() {
        etUsername = (EditText) findViewById(R.id.et_name);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
    }


    @Override
    public void onClick(View v) {
        // 确保用户已经给mListener赋值
        if(mListener != null) {

            switch (v.getId()) {

                case R.id.btn_ok:
                    String name = etUsername.getText().toString().trim();
                    String pwd = etPassword.getText().toString().trim();

                    // 回调数据接收方的onSuccess方法
                    mListener.onSuccess(name,pwd);
                    break;

                case R.id.btn_cancel:
                    etUsername.setText("");
                    etPassword.setText("");

                    // 回调数据接收方的onFailed方法
                    mListener.onFailed();
                    break;
            }
        }

        // 关闭对话框
        dismiss();
    }
}
