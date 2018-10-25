package com.example.kys_31.figureinformation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Observable;

import data.HandleUserMessage;
import data.UserMessage;
import fragment.FirstPageFragment;
import fragment.InterestFragment;
import fragment.PersionCenterFragment;
import util.TimeUtil;
import variable.UserMessageVariable;
import view.MetaballMenu;

public class MainActivity extends BaseActivity implements MetaballMenu.MetaballMenuClickListener{

    private FrameLayout mFlContent;
    private Fragment mFirstPageFragment;
    private Fragment mInterestFragment;
    private Fragment mPersionCenterFragment;
    private long mFirstTime = 0;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initControl() {
        mFlContent = (FrameLayout)findViewById(R.id.fragment_content);
        ((MetaballMenu)findViewById(R.id.metaball_menu)).setMenuClickListener(this);
        defaultShow();
    }

    @Override
    void setControlListener() {
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menuitem1:
                setBackground(0);
                break;
            case R.id.menuitem2:
                setBackground(1);
                break;
            case R.id.menuitem3:
                setBackground(2);
                break;
        }
    }


    private void setBackground(int i) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        switch (i){
            case 0:
                if (mFirstPageFragment == null){
                    mFirstPageFragment = new FirstPageFragment();
                }
                ft.replace(R.id.fragment_content, mFirstPageFragment);
                break;
            case 1:
                if (mInterestFragment == null){
                    mInterestFragment = new InterestFragment();
                }
                ft.replace(R.id.fragment_content, mInterestFragment);
                break;
            case 2:
                if (mPersionCenterFragment == null){
                    mPersionCenterFragment = new PersionCenterFragment();
                }
                ft.replace(R.id.fragment_content, mPersionCenterFragment);

                break;
             default:break;
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * 默认显示首页
     */
    private void defaultShow() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (mFirstPageFragment == null){
            mFirstPageFragment = new FirstPageFragment();
        }
        ft.replace(R.id.fragment_content, mFirstPageFragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void update(Observable observable, Object o) {
        mLoginState = (Boolean)o;
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime > 2000){
                    showToast("再按一次退出程序", false);
                    mFirstTime = secondTime;
                }else {
                    UserMessage userMessage = new UserMessage(UserMessageVariable.osUserMessage.oStrPhoneNumber,
                            UserMessageVariable.osUserMessage.oStrPassword, UserMessageVariable.osUserMessage.oStrHead,
                            UserMessageVariable.osUserMessage.oStrName, UserMessageVariable.osUserMessage.oStrBirsday, UserMessageVariable.osUserMessage.oStrEmail, UserMessageVariable.osUserMessage.oIntSex,
                            UserMessageVariable.osUserMessage.oIntLookCount+1, TimeUtil.getSystemTime());
                    HandleUserMessage.saveData(userMessage);
                    System.exit(0);
                }
                break;
            default:break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
