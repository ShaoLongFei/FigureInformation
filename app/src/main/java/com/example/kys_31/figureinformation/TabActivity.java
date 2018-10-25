package com.example.kys_31.figureinformation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kys_31.figureinformation.BaseActivity;
import com.example.kys_31.figureinformation.R;

import java.util.Observable;

/**
 * Created by 张同心 on 2017/9/25.
 * @function 引导页
 */

public class TabActivity extends BaseActivity{

    private ImageView mIvPicture;
    private TextView mTvInfo;
    private int mPosition;

    @Override
    protected int getLayoutID() {
        return R.layout.tab1;
    }

    @Override
    protected void initControl() {
        mIvPicture = (ImageView)findViewById(R.id.tabBaseFragment_iv);
        mTvInfo = (TextView)findViewById(R.id.tv_go);
        mPosition = getIntent().getIntExtra("position",0);
        if (mPosition ==2){ mTvInfo.setVisibility(View.VISIBLE);}
    }

    @Override
    void setControlListener() {
        mTvInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_go:
                break;
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        mLoginState = (Boolean)o;
    }
}
