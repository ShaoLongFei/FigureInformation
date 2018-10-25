package com.example.kys_31.figureinformation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myfirstjar.Concrete.PhoneNumberStart;
import com.example.myfirstjar.Utils.NoteVerify;
import com.example.myfirstjar.intefaces.NoteVerifyInterface;

import java.util.Observable;

import data.HandleUserMessage;

/**
 * Created by 张同心 on 2017/9/18.
 * function 注册
 */

public class RegisterActivity extends BaseActivity {

    private EditText mEtPhoneNumber;
    private EditText mEtVerificationCode;
    private Button mBtGetVerificationCode;
    private Button mBtNext;
    private int mNoteCode;
    private NoteVerifyInterface mNoteVerify;
    private ImageView mIvClose;

    @Override
    protected int getLayoutID() {
        return R.layout.register_activity;
    }

    @Override
    protected void initControl() {
        mEtPhoneNumber = (EditText)findViewById(R.id.phoneNumber_register_et);
        mBtGetVerificationCode = (Button)findViewById(R.id.getVerificationCode_register_bt);
        mEtVerificationCode = (EditText)findViewById(R.id.verificationCode_register_et);
        mBtNext = (Button)findViewById(R.id.next_register_bt);
        mIvClose = (ImageView)findViewById(R.id.close_register_iv);
        requestPermission();
    }

    /**
     * 请求权限
     */
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
        }
    }

    @Override
    void setControlListener() {
        mIvClose.setOnClickListener(this);
        mBtNext.setOnClickListener(this);
        mBtGetVerificationCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.close_register_iv:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.next_register_bt:
                nextStep();
                break;
            case R.id.getVerificationCode_register_bt:
                getVerificationCode();
                break;
            default:break;
        }
    }

    /**
     * 下一步
     */
    private void nextStep() {
        String phoneNumber = mEtPhoneNumber.getText().toString();
        if (mNoteVerify.MatePhoneNumberAndVerifyNumber(phoneNumber, mNoteCode)){
            Intent intent = new Intent(this, SetPasswrodActivity.class);
            intent.putExtra("phoneNumber", phoneNumber);
            startActivity(intent);
            finish();
        }else {
            showToast("请输入正确的验证码",false);
        }
    }

    /**
     * 获取验证码
     */
    public void getVerificationCode() {
        String phoneNumber = mEtPhoneNumber.getText().toString();
        if (!TextUtils.isEmpty(phoneNumber)){
            if (!HandleUserMessage.userExist(phoneNumber)){
                mNoteVerify =  NoteVerify.getInitialize(RegisterActivity.this);
                mNoteCode = mNoteVerify.createVerifyNumber(phoneNumber, "图情资讯");
                if (mNoteCode != 0 ){
                    mEtVerificationCode.setText(String.valueOf(mNoteCode));
                }
            }else {
                showToast("该手机号已经注册", false);
            }
        }else {
            showToast("手机号不能为空", false);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        initActivitySize();
    }

    /**
     * 调整Activity大小
     */
    private void initActivitySize() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void update(Observable observable, Object o) {
        mLoginState = (Boolean)o;
    }
}
