package com.example.kys_31.figureinformation;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Observable;

import data.HandleUserMessage;
import data.UserMessage;
import observer.LoginStateObservable;
import util.BitmapUtil;
import variable.UserMessageVariable;

/**
 * Created by 张同心 on 2017/9/18.
 * @function 设置密码
 */

public class SetPasswrodActivity extends BaseActivity {

    private TextView mTvBackRegister;
    private EditText mEtSetPassword;
    private EditText mEtSurePassword;
    private Button mBtSuccess;

    private String mStrPhoneNumber;

    @Override
    protected int getLayoutID() {
        return R.layout.setpassword_activity;
    }

    @Override
    protected void initControl() {
        mTvBackRegister = (TextView)findViewById(R.id.backRegister_setPassword_tv);
        mEtSetPassword = (EditText)findViewById(R.id.setPassword_setPassword_et);
        mEtSurePassword = (EditText)findViewById(R.id.surePassword_setPassword_et);
        mBtSuccess = (Button)findViewById(R.id.success_setPassword_bt);
        mStrPhoneNumber = getIntent().getStringExtra("phoneNumber");
    }

    @Override
    void setControlListener() {
        mTvBackRegister.setOnClickListener(this);
        mBtSuccess.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backRegister_setPassword_tv:
                startActivity(new Intent(this,RegisterActivity.class));
                finish();
                break;
            case R.id.success_setPassword_bt:
                String password = mEtSetPassword.getText().toString();
                if (password.equals(mEtSurePassword.getText().toString())){
                    UserMessageVariable.osUserMessage = new UserMessage(mStrPhoneNumber, password, BitmapUtil.bitmapToString(BitmapFactory.decodeResource(getResources(), R.drawable.logo)), "未设置姓名", "0", "qwertyuioasdfghjklzxcvbnmqweadg", 0, 0,"首次使用");
                    HandleUserMessage.saveData(UserMessageVariable.osUserMessage);
                    LoginStateObservable.getInstatnce().notificationOberver(true);
                    finish();//注册完毕
                }else {
                    showToast("两次输入密码不同", false);
                    mEtSetPassword.setText("");
                    mEtSurePassword.setText("");
                }
                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        initActivitySize();
    }

    /**
     * 初始化Activity的大小
     */
    private void initActivitySize() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void update(Observable observable, Object o) {
        Log.e("TAG", "登录");
        mLoginState = (Boolean)o;
    }
}
