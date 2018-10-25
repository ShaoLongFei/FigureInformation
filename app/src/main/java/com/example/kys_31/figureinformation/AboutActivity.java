package com.example.kys_31.figureinformation;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Observable;

/**
 * Created by 张同心 on 2017/9/14.
 * @function 关于APP介绍
 */

public class AboutActivity extends BaseActivity {

    private LinearLayout mLlBack;
    private TextView mTvTitleName;

    @Override
    protected int getLayoutID() {
        return R.layout.aboutapp_view;
    }

    @Override
    protected void initControl() {
        mLlBack = (LinearLayout)findViewById(R.id.titleBack_persionMessage_ll);
        mTvTitleName = (TextView)findViewById(R.id.titleName_tv);
        mTvTitleName.setText("关于图情资讯");
    }

    @Override
    void setControlListener() {
        mLlBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void update(Observable observable, Object o) {
        mLoginState = (Boolean)o;
    }
}
