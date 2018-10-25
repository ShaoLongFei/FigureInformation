package com.example.kys_31.figureinformation;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Observable;

import data.HandleWebDataMessage;
import data.WebDataMessage;

/**
 * Created by 幽蓝丶流月 on 2017/9/14.
 * @function 展示页 3秒
 */

public class SplashActivity extends BaseActivity {

    private String[] from;
    private String[] time;
    private String[] clickNumber;
    private String[] title;
    private String[] content;
    private String[] url;
    private String[] pictureUrl;
    private int lines;
    private int position=0;
    private String StringUrl="http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm;jsessionid=C6E1686738E860D7CF9A735C35FEB6CA?parentPageId=1505810791964&directionId=4&controlType=openhome";
    private String[] text = new String[]{"可再生能源", "纳米科技", "食物营养", "油气类", "水体污染治理",
            "大气污染防治", "集成电路装备", "编译报道", "重要报告", "重大传染病防治", "新药创制",
            "宽带移动通信", "农业污染防治", "转基因生物", "数控机床"};
    private String[] URL = new String[]{"http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm;jsessionid=C6E1686738E860D7CF9A735C35FEB6CA?parentPageId=1505810791964&directionId=4&controlType=openhome",
            "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505811585475&directionId=1&controlType=openhome",
            "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505811793424&directionId=5&controlType=openhome",
            "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813148957&directionId=19&controlType=openhome",
            "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813167069&directionId=8&controlType=openhome",
            "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813183979&directionId=20&controlType=openhome",
            "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813203183&directionId=7&controlType=openhome",
            "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendspecial.htm?parentPageId=1505653299236&serverId=82",
            "http://portal.nstl.gov.cn/STMonitor/home/bg.htm?serverId=82",
            "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813348341&directionId=3&controlType=openhome",
            "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813335018&directionId=10&controlType=openhome",
            "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813316922&directionId=2&controlType=openhome",
            "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813301166&directionId=6&controlType=openhome",
            "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813284131&directionId=12&controlType=openhome",
            "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813218549&directionId=9&controlType=openhome"};
    private WebDataMessage mWebDataMessage;
    private Handler handler;
    private  Thread thread;



    @Override
    protected int getLayoutID() {
        return R.layout.splash_screen;
    }

    @Override
    protected void initControl() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initHandler();
        for (int s=0;s<13;s++){
            if (!HandleWebDataMessage.webDataMessageExist(text[s])){
                Log.e("闪屏开始加载",text[s]);
                position=s;
                 CreatThread();
                break;
            }
        }

        if (thread==null){
            handler.sendEmptyMessage(1);
        }

    }

    private void initHandler() {
        handler=new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        };
    }

    private void CreatThread() {

         thread = new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    Document doc = Jsoup.connect(StringUrl).timeout(10000).get();
                    Document doc2;
                    Log.e("网页总标题", doc.title());
                    Log.e("数据行数", doc.body().select("p").size() + "");
                    lines = doc.body().select("a.font13.aral.blue1").size();
                    title = new String[lines];
                    content = new String[lines];
                    time = new String[lines];
                    from = new String[lines];
                    clickNumber = new String[lines];
                    url = new String[lines];
                    pictureUrl = new String[lines];
                    for (int i = 0; i < lines; i++) {
                        title[i] = doc.body().select("a.font13.aral.blue1").get(i).text();
                        content[i] = doc.body().select("div.zxx_text_overflow_5").get(i).text();
                        time[i] = doc.body().select("span.date").get(i).text();
                        from[i] = doc.body().select("span.fl").select("a").get(i).text();
                        Log.e("超链接", doc.body().select("a.font13.aral.blue1").get(i).attr("href"));
                        url[i] = "http://portal.nstl.gov.cn" + doc.body().select("a.font13.aral.blue1").get(i).attr("href");
                        doc2 = Jsoup.connect("http://portal.nstl.gov.cn" + doc.body().select("a.font13.aral.blue1").get(i).attr("href")).timeout(5000).get();
                        if (doc2.select("div.zoom.mt-15").select("img").attr("src").length() < 30) {
                            pictureUrl[i] = "http://bpic.588ku.com/element_origin_min_pic/17/04/14/61a6df84d26a81f05c9e22dbbebe4ef1.jpg";
                        } else {
                            pictureUrl[i] = "http://portal.nstl.gov.cn" + doc2.select("div.zoom.mt-15").select("img").attr("src").substring(0, doc2.select("div.zoom.mt-15").select("img").attr("src").indexOf(".jpg") + 4);
                            Log.e("添加的图片的网址", "http://portal.nstl.gov.cn" + doc2.select("div.zoom.mt-15").select("img").attr("src").substring(0, doc2.select("div.zoom.mt-15").select("img").attr("src").indexOf(".jpg") + 4));
                        }
                    }
                    Log.e("点击量数据行数", doc.body().select("span.fl").size() + "");
                    for (int j = 4; j < doc.body().select("span.fl").size(); j++) {
                        clickNumber[j - 4] = doc.body().select("span.fl").get(j).text().substring(doc.body().select("span.fl").get(j).text().indexOf("点击量：") + 4);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mWebDataMessage = new WebDataMessage(text[position], url, title, content, time, clickNumber, pictureUrl, from, new String[lines], lines);
                HandleWebDataMessage.saveWebDataMessageData(mWebDataMessage);

                Log.e("加载完成","我觉得两个比较好");
                handler.sendEmptyMessage(1);

            }
            };

            thread.start();

    }

    @Override
    void setControlListener() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void update(Observable observable, Object o) {
        mLoginState = (Boolean)o;
    }
}
