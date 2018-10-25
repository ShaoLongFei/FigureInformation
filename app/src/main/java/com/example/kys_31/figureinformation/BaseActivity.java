package com.example.kys_31.figureinformation;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.util.Observer;

import data.HandleUserMessage;
import data.UserMessage;
import observer.LoginStateObservable;
import util.AutoLoginUtil;
import util.BitmapUtil;
import util.TimeUtil;
import util.ViewUtil;
import variable.SystemSetVariable;
import variable.UserMessageVariable;


/**
 * Created by 张同心 on 2017/8/18.
 * @function Activity 基类
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, Observer{

    public boolean mLoginState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutID = getLayoutID();
        if (layoutID != 0){
            setContentView(layoutID);
        }
        /*隐藏标题栏*/
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }

        /*设置状态栏颜色*/
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#EA403C"));

        LoginStateObservable.getInstatnce().addObserver(this);//注册观察者
        initControl();
        setControlListener();
    }

    /**
     * Toast提示
     * @param content 提示内容
     * @param which true:是长提示 ；false:是短提示
     */
    public void showToast(String content, boolean which){
        Toast.makeText(this,content,which?Toast.LENGTH_LONG:Toast.LENGTH_SHORT).show();
    }

    /**
     * 获得布局ID
     * @return 布局ID
     */
     protected abstract int getLayoutID();

    /**
     * 初始化控件
     */
     protected abstract void initControl();

    /**
     * 设置控件的监听
     */
     abstract void setControlListener();

    @Override
    public void onStart(){
        super.onStart();
    /*屏幕亮度*/
        SystemSetVariable.osScreenBrightValue = ViewUtil.getScreenBrightness(this);
        if (SystemSetVariable.osNightModel){
            ViewUtil.setScreenBrightness(this, 10);
        }else {
            ViewUtil.setScreenBrightness(this, SystemSetVariable.osScreenBrightValue);
        }
        /*自动登录*/
        AutoLoginUtil.startAutoLogin(this);
    }

}
