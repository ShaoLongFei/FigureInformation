package com.example.kys_31.figureinformation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Observable;

import view.MyScrollView;

public class HotTopicsActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    //控件
    private MyScrollView sv_view;
    private LinearLayout ll_load;
    private LinearLayout ll_refresh;
    private RelativeLayout rl_information1;
    private RelativeLayout rl_information2;
    private RelativeLayout rl_information3;
    private RelativeLayout rl_information4;
    private RelativeLayout rl_information5;
    private RelativeLayout rl_information6;
    private RelativeLayout rl_information7;
    private RelativeLayout rl_information8;
    private RelativeLayout rl_information9;
    private RelativeLayout rl_information10;
    private RelativeLayout rl_information11;
    private RelativeLayout rl_information12;
    private RelativeLayout rl_information13;
    private RelativeLayout rl_information14;
    private RelativeLayout rl_information15;
    private RelativeLayout rl_information16;
    private RelativeLayout rl_information17;
    private RelativeLayout rl_information18;
    private RelativeLayout rl_information19;

    private Handler viewHandler;
    private String[] url;
    private boolean hasShoiw=false;



    @Override
    protected int getLayoutID() {
        return R.layout.hot_topics;
    }

    private void initURL() {
        url=new String[19];
        url[0]="http://portal.nstl.gov.cn/STMonitor/home/bianyi_recordshow.htm?id=45899&parentPageId=1505693215743&serverId=82";
        url[1]="http://portal.nstl.gov.cn/STMonitor/home/bianyi_recordshow.htm?id=45702&parentPageId=1505693215743&serverId=82";
        url[2]="http://portal.nstl.gov.cn/STMonitor/home/bianyi_recordshow.htm?id=45408&parentPageId=1505693215743&serverId=82";
        url[3]="连接的其他网站地址";
        url[4]="http://portal.nstl.gov.cn/STMonitor/home/bianyi_recordshow.htm?id=45396&parentPageId=1505693215743&serverId=82";
        url[5]="http://portal.nstl.gov.cn/STMonitor/home/bianyi_recordshow.htm?id=45392&parentPageId=1505693215743&serverId=82";
        url[6]="http://portal.nstl.gov.cn/STMonitor/home/bianyi_recordshow.htm?id=45391&parentPageId=1505693215743&serverId=82";
        url[7]="http://portal.nstl.gov.cn/STMonitor/home/bianyi_recordshow.htm?id=45389&parentPageId=1505693215743&serverId=82";
        url[8]="http://portal.nstl.gov.cn/STMonitor/home/bianyi_recordshow.htm?id=45139&parentPageId=1505693215743&serverId=82";
        url[9]="http://portal.nstl.gov.cn/STMonitor/home/bianyi_recordshow.htm?id=45135&parentPageId=1505693215743&serverId=82";
        url[10]="http://portal.nstl.gov.cn/STMonitor/home/bianyi_recordshow.htm?id=45131&parentPageId=1505693215743&serverId=82";
        url[11]="http://portal.nstl.gov.cn/STMonitor/home/bianyi_recordshow.htm?id=45127&parentPageId=1505693215743&serverId=82";
        url[12]="http://portal.nstl.gov.cn/STMonitor/home/bianyi_recordshow.htm?id=45126&parentPageId=1505693215743&serverId=82";
        url[13]="http://portal.nstl.gov.cn/STMonitor/home/bianyi_recordshow.htm?id=44956&parentPageId=1505693215743&serverId=82";
        url[14]="http://portal.nstl.gov.cn/STMonitor/home/bianyi_recordshow.htm?id=44950&parentPageId=1505693215743&serverId=82";
        url[15]="http://portal.nstl.gov.cn/STMonitor/home/bianyi_recordshow.htm?id=44938&parentPageId=1505693215743&serverId=82";
        url[16]="http://portal.nstl.gov.cn/STMonitor/home/bianyi_recordshow.htm?id=44928&parentPageId=1505693215743&serverId=82";
        url[17]="http://portal.nstl.gov.cn/STMonitor/home/bianyi_recordshow.htm?id=44793&parentPageId=1505693215743&serverId=82";
        url[18]="http://portal.nstl.gov.cn/STMonitor/home/bianyi_recordshow.htm?id=44317&parentPageId=1505693215743&serverId=82";
    }

     protected void initControl() {
        sv_view = (MyScrollView) findViewById(R.id.sv_view);
        ll_load = (LinearLayout) findViewById(R.id.ll_load);
        ll_refresh = (LinearLayout) findViewById(R.id.ll_refresh);
        rl_information1=(RelativeLayout)findViewById(R.id.rl_information1);
        rl_information2=(RelativeLayout)findViewById(R.id.rl_information2);
        rl_information3=(RelativeLayout)findViewById(R.id.rl_information3);
        rl_information4=(RelativeLayout)findViewById(R.id.rl_information4);
        rl_information5=(RelativeLayout)findViewById(R.id.rl_information5);
        rl_information6=(RelativeLayout)findViewById(R.id.rl_information6);
        rl_information7=(RelativeLayout)findViewById(R.id.rl_information7);
        rl_information8=(RelativeLayout)findViewById(R.id.rl_information8);
        rl_information9=(RelativeLayout)findViewById(R.id.rl_information9);
        rl_information10=(RelativeLayout)findViewById(R.id.rl_information10);
        rl_information11=(RelativeLayout)findViewById(R.id.rl_information11);
        rl_information12=(RelativeLayout)findViewById(R.id.rl_information12);
        rl_information13=(RelativeLayout)findViewById(R.id.rl_information13);
        rl_information14=(RelativeLayout)findViewById(R.id.rl_information14);
        rl_information15=(RelativeLayout)findViewById(R.id.rl_information15);
        rl_information16=(RelativeLayout)findViewById(R.id.rl_information16);
        rl_information17=(RelativeLayout)findViewById(R.id.rl_information17);
        rl_information18=(RelativeLayout)findViewById(R.id.rl_information18);
        rl_information19=(RelativeLayout)findViewById(R.id.rl_information19);
         initHandler();
         initURL();
    }

    @Override
    void setControlListener() {
        sv_view.setOnTouchListener(this);
        rl_information1.setOnClickListener(this);
        rl_information2.setOnClickListener(this);
        rl_information3.setOnClickListener(this);
        rl_information4.setOnClickListener(this);
        rl_information5.setOnClickListener(this);
        rl_information6.setOnClickListener(this);
        rl_information7.setOnClickListener(this);
        rl_information8.setOnClickListener(this);
        rl_information9.setOnClickListener(this);
        rl_information10.setOnClickListener(this);
        rl_information11.setOnClickListener(this);
        rl_information12.setOnClickListener(this);
        rl_information13.setOnClickListener(this);
        rl_information14.setOnClickListener(this);
        rl_information15.setOnClickListener(this);
        rl_information16.setOnClickListener(this);
        rl_information17.setOnClickListener(this);
        rl_information18.setOnClickListener(this);
        rl_information19.setOnClickListener(this);
    }


    private void initHandler() {

        viewHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        if (!hasShoiw){
                            showInformation();
                        }
                        ll_load.setVisibility(View.GONE);
                        break;
                    case 2:
                        ll_refresh.setVisibility(View.GONE);
                        break;
                }
            }
        };
    }

    private void showInformation() {
        rl_information13.setVisibility(View.VISIBLE);
        rl_information14.setVisibility(View.VISIBLE);
        rl_information15.setVisibility(View.VISIBLE);
        rl_information16.setVisibility(View.VISIBLE);
        rl_information17.setVisibility(View.VISIBLE);
        rl_information18.setVisibility(View.VISIBLE);
        rl_information19.setVisibility(View.VISIBLE);
       hasShoiw=true;
    }

    private void showToast(String str) {
        Toast.makeText(HotTopicsActivity.this, str, Toast.LENGTH_SHORT).show();
    }


    private void Jump(int i) {
        Intent intent=new Intent(HotTopicsActivity.this,MainActivity.class);
        intent.putExtra("url",url[i]);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_information1:
                Jump(0);
                break;
            case R.id.rl_information2:
                Jump(1);
                break;
            case R.id.rl_information3:
                Jump(2);
                break;
            case R.id.rl_information4:
                Jump(3);
                break;
            case R.id.rl_information5:
                Jump(4);
                break;
            case R.id.rl_information6:
                Jump(5);
                break;
            case R.id.rl_information7:
                Jump(6);
                break;
            case R.id.rl_information8:
                Jump(7);
                break;
            case R.id.rl_information9:
                Jump(8);
                break;
            case R.id.rl_information10:
                Jump(9);
                break;
            case R.id.rl_information11:
                Jump(10);
                break;
            case R.id.rl_information12:
                Jump(11);
                break;
            case R.id.rl_information13:
                Jump(12);
                break;
            case R.id.rl_information14:
                Jump(13);
                break;
            case R.id.rl_information15:
                Jump(14);
                break;
            case R.id.rl_information16:
                Jump(15);
                break;
            case R.id.rl_information17:
                Jump(16);
                break;
            case R.id.rl_information18:
                Jump(17);
                break;
            case R.id.rl_information19:
                Jump(18);
                break;

        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_MOVE: {

                if (sv_view.getScrollY() <= 0) {
                    ll_refresh.setVisibility(View.VISIBLE);
                    viewHandler.sendEmptyMessageDelayed(2, 2000);
                }

                int measuredHeight = sv_view.getChildAt(0)
                        .getMeasuredHeight();
                int scrollY = sv_view.getScrollY();
                int height = sv_view.getHeight();
                if (measuredHeight <= scrollY + height) {
                    ll_load.setVisibility(View.VISIBLE);
                    viewHandler.sendEmptyMessageDelayed(1, 2000);
                }
                break;
            }
            default:
                break;
        }

        return false;
    }

    @Override
    public void update(Observable observable, Object o) {
        mLoginState = (Boolean)o;
    }
}
