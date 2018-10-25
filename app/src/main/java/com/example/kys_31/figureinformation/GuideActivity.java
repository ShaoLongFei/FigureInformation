package com.example.kys_31.figureinformation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 幽蓝丶流月 on 2017/9/14.
 * @modify 张同心 优化
 */

public class GuideActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

    private ViewPager vPager = null;
    private boolean mFirstUse = true;
    private TextView tv_open;
    private Handler handler;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_main);
        vPager = (ViewPager) findViewById(R.id.viewpager);
        tv_open=(TextView)findViewById(R.id.tv_open);
        tv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (handler!=null){
                    handler.removeMessages(1);
                }
                startActivity(new Intent(GuideActivity.this,SplashActivity.class));
                finish();
            }
        });
        initHandler();
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.guide_1);
        list.add(R.mipmap.guide_2);
        list.add(R.mipmap.guide_3);
        MyVpAdater adater = new MyVpAdater(this, list);
        vPager.setAdapter(adater);
        vPager.setOffscreenPageLimit(3);
        vPager.setPageTransformer(true, new ScaleTransformer());
        vPager.setOnPageChangeListener(this);
    }

    private void initHandler() {
        handler=new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                startActivity(new Intent(GuideActivity.this,SplashActivity.class));
                finish();
            }
        };
    }


    @Override
    public void onResume(){
        super.onResume();
        /*全屏显示*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*判断是否首次使用*/
        SharedPreferences firstUse = getSharedPreferences("firstUse", 0);
        mFirstUse = firstUse.getBoolean("firstUse", true);
        if (mFirstUse){
            SharedPreferences.Editor firstUseEditor = firstUse.edit();
            firstUseEditor.putBoolean("firstUse", false);
            firstUseEditor.commit();
        }else {
            startActivity(new Intent(GuideActivity.this,SplashActivity.class));
            finish();
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.e("转到哪了",""+position);
        if (position==2){
            tv_open.setVisibility(View.VISIBLE);
           handler.sendEmptyMessageDelayed(1,3000);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
