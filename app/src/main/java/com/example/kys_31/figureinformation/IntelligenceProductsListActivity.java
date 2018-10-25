package com.example.kys_31.figureinformation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Observable;

import view.MyScrollView;

public class IntelligenceProductsListActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    //控件
    private MyScrollView sv_view;
    private LinearLayout ll_load;
    private LinearLayout ll_refresh;
    private LinearLayout ll_information;


    private Handler viewHandler;
    private String[] time;
    private String[] title;
    private String[] url;
    private RelativeLayout.LayoutParams layoutParams;
    private RelativeLayout relativeLayout;
    private  TextView tv_title;
    private TextView tv_time;
    private RelativeLayout space;
    private String URL;

    @Override
    protected int getLayoutID() {
        return R.layout.main_layout;
    }

    private void initURL() {
        url=new String[15];
        url[0]="http://portal.nstl.gov.cn/STMonitor/home/specialqbcpdetail.htm?id=82&periodicalId=508&year=2017&parentPageId=1505725918830&serverId=82&controlType=";
        url[1]="http://portal.nstl.gov.cn/STMonitor/home/specialqbcpdetail.htm?id=82&periodicalId=489&year=2017&parentPageId=1505739155999&serverId=82&controlType=";
        url[2]="http://portal.nstl.gov.cn/STMonitor/home/specialqbcpdetail.htm?id=82&periodicalId=472&year=2017&parentPageId=1505740253398&serverId=82&controlType=";
        url[3]="http://portal.nstl.gov.cn/STMonitor/home/specialqbcpdetail.htm?id=82&periodicalId=470&year=2017&parentPageId=1505740376483&serverId=82&controlType=";
        url[4]="http://portal.nstl.gov.cn/STMonitor/home/specialqbcpdetail.htm?id=82&periodicalId=461&year=2017&parentPageId=1505740442705&serverId=82&controlType=";
        url[5]="http://portal.nstl.gov.cn/STMonitor/home/specialqbcpdetail.htm?id=82&periodicalId=433&year=2017&parentPageId=1505740510347&serverId=82&controlType=";
        url[6]="http://portal.nstl.gov.cn/STMonitor/home/specialqbcpdetail.htm?id=82&periodicalId=430&year=2017&parentPageId=1505740550205&serverId=82&controlType=";
        url[7]="http://portal.nstl.gov.cn/STMonitor/home/specialqbcpdetail.htm?id=82&periodicalId=412&year=2016&parentPageId=1505740582653&serverId=82&controlType=";
        url[8]="http://portal.nstl.gov.cn/STMonitor/home/specialqbcpdetail.htm?id=82&periodicalId=409&year=2016&parentPageId=1505740625366&serverId=82&controlType=";
        url[9]="http://portal.nstl.gov.cn/STMonitor/home/specialqbcpdetail.htm?id=82&periodicalId=266&year=2016&parentPageId=1505740667345&serverId=82&controlType=";
        url[10]="";
    }



     protected void initControl() {
        sv_view = (MyScrollView) findViewById(R.id.sv_view);
        ll_load = (LinearLayout) findViewById(R.id.ll_load);
        ll_refresh = (LinearLayout) findViewById(R.id.ll_refresh);
        ll_information = (LinearLayout) findViewById(R.id.ll_information);
         URL=getIntent().getStringExtra("url");
         initHandler();
         CreateThread();
         initURL();
    }

    @Override
    void setControlListener() {
        sv_view.setOnTouchListener(this);
    }

    private void initHandler() {

        viewHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        ll_load.setVisibility(View.GONE);
                        break;
                    case 2:
                        ll_refresh.setVisibility(View.GONE);
                        break;
                    case 3:
                        AddView(Integer.valueOf(msg.obj.toString()));
                        break;
                }
            }
        };
    }

    private void showToast(String str) {
        Toast.makeText(IntelligenceProductsListActivity.this, str, Toast.LENGTH_SHORT).show();
    }


    private void AddView(int line) {
        for (int i = 0; i < line; i++) {
            CreatView(Integer.valueOf(i+"1"),Integer.valueOf(i+"2"),Integer.valueOf(i+"3"),i);
            AddDate(i);
        }
    }

    private void AddDate(int i) {
        tv_title.setText(title[i]);
        tv_time.setText(time[i]);
    }

    private void CreatView(int relativeLayoutId,int titleId,int timeId,int i) {

        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 20);
        layoutParams.setMargins(10,20,10,0);
        space = new RelativeLayout(this);
        space.setLayoutParams(layoutParams);

        ll_information.addView(space);

        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,20,10,0);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(layoutParams);
        relativeLayout.setId(relativeLayoutId);
        relativeLayout.setBackgroundResource(R.drawable.information_background);
        relativeLayout.setElevation( 7.0f);
        final int number=i;
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Jump(number);
            }
        });
        ll_information.addView(relativeLayout);

        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,0,10,0);
        tv_title=new TextView(this);
        tv_title.setLayoutParams(layoutParams);
        tv_title.setId(titleId);
        tv_title.setTextColor(Color.parseColor("#FF000000"));
        tv_title.setTextSize(20.0f);

        relativeLayout.addView(tv_title);

        layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,0,0,10);
        layoutParams.addRule(RelativeLayout.BELOW,tv_title.getId());
        tv_time=new TextView(this);
        tv_time.setLayoutParams(layoutParams);
        tv_time.setId(timeId);

        relativeLayout.addView(tv_time);

    }

    private void Jump(int i) {
        Intent intent=new Intent(IntelligenceProductsListActivity.this,CatalogActivity.class);
        intent.putExtra("url",url[i]);
        startActivity(intent);
    }

    private void CreateThread() {
        Thread thread = new Thread() {

            @Override
            public void run() {
                super.run();

                try {
                    Document  doc = Jsoup.connect(URL).timeout(50000).get();
//                    Log.e("body",doc.body().text());
//                    Log.e("网页总标题", doc.title());
//                    Log.e("标题数量", doc.select("dd").select("a").size() + "");
//                    Log.e("标题", doc.select("dd").select("a").text());
//                    Log.e("标题超链接数量",doc.select("dd").select("a").size()+"");
//                    Log.e("标题超链接", doc.select("a").attr("href"));
//                    Log.e("时间数量", doc.select("span.date").size()+"");
//                    Log.e("发布时间", doc.select("span.date").text());
                    int lines=doc.select("dd").select("a").size();
                    title=new String[lines];
                    time=new String[lines];
                    for (int i=0;i<lines;i++){
                        Log.e("标题", doc.select("dd").select("a").get(i).text());
                        Log.e("标题超链接", doc.select("a").get(i).attr("href"));
                        Log.e("发布时间", doc.select("span.date").get(i).text());
                        title[i]=doc.select("dd").select("a").get(i).text();
                        time[i]=doc.select("span.date").get(i).text();
                     //   url[i]= "http://portal.nstl.gov.cn"+doc.select("a").get(i).attr("href");
                    }

                    Message message=new Message();
                    message.what=3;
                    message.obj=lines;
                    viewHandler.sendMessage(message);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

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

    }
}
