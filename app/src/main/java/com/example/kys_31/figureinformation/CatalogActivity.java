package com.example.kys_31.figureinformation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import java.util.Random;

import view.MyScrollView;

public class CatalogActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    //控件
    private MyScrollView sv_view;
    private LinearLayout ll_load;
    private LinearLayout ll_refresh;
    private LinearLayout ll_information;



    private Handler handler;
    private Handler viewHandler;
    private int lineNumber;
    private String[] from;
    private String[] time;
    private String[] clickNumber;
    private String[] title;
    private String[] content;
    private String[] url;
    private RelativeLayout.LayoutParams layoutParams;
    private RelativeLayout relativeLayout;
    private  TextView tv_title;
    private TextView tv_content;
    private TextView tv_time;
    private TextView tv_from;
    private TextView tv_clicknumber;
    private RelativeLayout space;
    private String URL;


    @Override
    protected int getLayoutID() {
        return R.layout.main_layout;
    }

    @Override
    protected void initControl() {
        sv_view = (MyScrollView) findViewById(R.id.sv_view);
        ll_load = (LinearLayout) findViewById(R.id.ll_load);
        ll_refresh = (LinearLayout) findViewById(R.id.ll_refresh);
        ll_information = (LinearLayout) findViewById(R.id.ll_information);
        URL=getIntent().getStringExtra("url");
        initHandler();
        CreateThread();
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
        Toast.makeText(CatalogActivity.this, str, Toast.LENGTH_SHORT).show();
    }


    private void AddView(int line) {
        for (int i = 0; i < line; i++) {
            CreatView(Integer.valueOf(i+"1"),Integer.valueOf(i+"2"),Integer.valueOf(i+"3"),Integer.valueOf(i+"4"),Integer.valueOf(i+"5"),Integer.valueOf(i+"6"),i);
            AddDate(title,content,time,from,clickNumber,i);
        }
    }

    private void AddDate(String[] title,String[] content,String[] time,String[] from,String[] clickNumber,int i) {
        tv_title.setText(title[i]);
        tv_content.setText("摘要：    "+content[i]);
        tv_time.setText("录入时间:"+time[i]);
        tv_clicknumber.setText("点击量：  "+clickNumber[i]);
    }

    private void CreatView(int relativeLayoutId,int titleId,int contentId,int timeId,int fromId,int clickNumberId,int i) {

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

        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,0,10,0);
        layoutParams.addRule(RelativeLayout.BELOW,tv_title.getId());
        tv_content=new TextView(this);
        tv_content.setLayoutParams(layoutParams);
        tv_content.setId(contentId);
        tv_content.setTextColor(Color.parseColor("#252323"));
        tv_content.setTextSize(13.0f);
        tv_content.setLines(4);
        tv_content.setEllipsize(TextUtils.TruncateAt.END);

        relativeLayout.addView(tv_content);

        layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,0,0,10);
        layoutParams.addRule(RelativeLayout.BELOW,tv_content.getId());
        tv_time=new TextView(this);
        tv_time.setLayoutParams(layoutParams);
        tv_time.setId(timeId);

        relativeLayout.addView(tv_time);

        layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30,0,20,10);
        layoutParams.addRule(RelativeLayout.RIGHT_OF,tv_time.getId());
        layoutParams.addRule(RelativeLayout.BELOW,tv_content.getId());
        tv_clicknumber=new TextView(this);
        tv_clicknumber.setLayoutParams(layoutParams);
        tv_clicknumber.setId(clickNumberId);

        relativeLayout.addView(tv_clicknumber);
    }

    private void Jump(int i) {
        Intent intent=new Intent(CatalogActivity.this,MainActivity.class);
        intent.putExtra("url","http://portal.nstl.gov.cn"+url[i]);
        startActivity(intent);
    }

    private void CreateThread() {
        Thread thread = new Thread() {

            @Override
            public void run() {
                super.run();

                try {
                    Document  doc = Jsoup.connect(URL).timeout(5000).get();
                    Log.e("body",doc.body().text());
                    Log.e("标题数量",doc.select("a.font13.aral.blue1").size()+"");
                    Log.e("标题",doc.select("a.font13.aral.blue1").text());
                    Log.e("时间数量",doc.select("span.date").size()+"");
                    Log.e("时间",doc.select("span.date").text());
                    Log.e("摘要数量",doc.select("div.desc").select("div").select("input").size()+"");
                    Log.e("摘要",doc.select("div.desc").select("div").select("input").attr("value").replace("<p>",""));
                    Log.e("超链接数量",doc.select("div.desc").select("div").select("a").size()+"");
                    Log.e("超链接",doc.select("div.desc").select("div").select("a").attr("href"));
                    Log.e("点击量",doc.select("dd").text());

                    int lines=doc.select("a.font13.aral.blue1").size();
                    title=new String[lines];
                    time=new String[lines];
                    content=new String[lines];
                    clickNumber=new String[lines];
                    url=new String[lines];
                    Random random=new Random();
                    for (int i=0;i<lines;i++){
                        title[i]=doc.select("a.font13.aral.blue1").get(i).text();
                        time[i]=doc.select("span.date").get(i).text();
                        content[i]=doc.select("div.desc").select("div").select("input").get(i).attr("value").replace("<p>","").replace("</p>","").replace("<strong>","").replace("</strong>","");
                        url[i]=doc.select("div.desc").select("div").select("a").get(i).attr("href");
                        clickNumber[i]=random.nextInt(100)+"";
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
        mLoginState = (Boolean)o;
    }
}
