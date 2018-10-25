package com.example.kys_31.figureinformation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Observable;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import data.HandleUserMessage;
import data.UserMessage;
import observer.LoginStateObservable;
import util.AutoLoginUtil;
import util.SharedUtil;
import util.ThirdPartyLoginUtil;
import variable.UserMessageVariable;

/**
 * Created by 张同心 on 2017/9/18.
 * @function 登录
 */

public class LoginActivity extends BaseActivity {

    private EditText mEtAccount;
    private EditText mEtPassword;
    private Button mBtLogin;
    private TextView mTvForgetPassword;
    private TextView mTvRegister;
    private ImageView mIvQQ;
    private ImageView mIvVX;
    private ImageView mIvVB;
    private ImageView mIvClose;

    @Override
    protected int getLayoutID() {
        return R.layout.login_activity;
    }

    @Override
    protected void initControl() {
        mEtAccount = (EditText)findViewById(R.id.account_login_et);
        mEtPassword = (EditText)findViewById(R.id.password_login_bg);
        mBtLogin = (Button)findViewById(R.id.login_login_bt);
        mTvForgetPassword = (TextView)findViewById(R.id.forgetPassword_login_tv);
        mTvRegister = (TextView)findViewById(R.id.register_login_tv);
        mIvQQ = (ImageView)findViewById(R.id.qq_login_iv);
        mIvVX = (ImageView)findViewById(R.id.vx_login_iv);
        mIvVB = (ImageView)findViewById(R.id.vb_login_iv);
        mIvClose = (ImageView)findViewById(R.id.close_login_iv);
    }

    @Override
    void setControlListener() {
        mBtLogin.setOnClickListener(this);
        mTvForgetPassword.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mIvQQ.setOnClickListener(this);
        mIvVB.setOnClickListener(this);
        mIvVX.setOnClickListener(this);
        mIvClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_login_bt:
                login();
                break;
            case R.id.qq_login_iv:
                ThirdPartyLoginUtil.authPlatform_QQ(this);
                break;
            case R.id.vx_login_iv:
                showToast("微信登录系统正在维护，自动更换QQ登录方式",false);
                ThirdPartyLoginUtil.authPlatform_QQ(this);
                break;
            case R.id.vb_login_iv:
                ThirdPartyLoginUtil.authPlatform_XinLang(this);
                break;
            case R.id.close_login_iv:
                finish();
                break;
            case R.id.forgetPassword_login_tv:
                startActivity(new Intent(this,RegisterActivity.class));
                finish();
                break;
            case R.id.register_login_tv:
                startActivity(new Intent(this,RegisterActivity.class));
                finish();
                break;
            default:break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        String phoneNumber = mEtAccount.getText().toString();//账号
        String password = mEtPassword.getText().toString();//密码
        if (!TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(password)){ //判空
            if (HandleUserMessage.userExist(phoneNumber)){ //判断是否已经注册过
                UserMessageVariable.osUserMessage = HandleUserMessage.readUserMessage(phoneNumber);
                LoginStateObservable.getInstatnce().notificationOberver(true);
                AutoLoginUtil.autoLogin(this);//自动登录
                finish();//登录完成

            }else {
                showToast("请先注册", false);
                mEtAccount.setText("");
                mEtPassword.setText("");
            }
        }else {
            if (TextUtils.isEmpty(phoneNumber)){
                showToast("账号不能为空",false);
            }else {
                showToast("密码不能为空",false);
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        initActivitySize();
    }

    /**
     * 跳转Activity大小
     */
    private void initActivitySize() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void update(Observable observable, Object o) {
        mLoginState = (Boolean)o;
    }
}
