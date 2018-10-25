package com.example.kys_31.figureinformation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import custom.ProgressDialogCustom;
import view.MyScrollView;

public class InformatiinListActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

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
    private ImageLoader imageLoader;
    private RequestQueue mQueue;
    private NetworkImageView netpicture;
    private List<String> pictureUrl=new ArrayList<String>();
    private ProgressDialogCustom dialog;

    @Override
    protected int getLayoutID() {
        return R.layout.main_layout;
    }

    private void showmeidialog(){
        dialog =new ProgressDialogCustom(this, "您的网络较慢，请耐心等待",R.anim.frame);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

     protected void initControl() {
        sv_view = (MyScrollView) findViewById(R.id.sv_view);
        ll_load = (LinearLayout) findViewById(R.id.ll_load);
        ll_refresh = (LinearLayout) findViewById(R.id.ll_refresh);
        ll_information = (LinearLayout) findViewById(R.id.ll_information);
         URL=getIntent().getStringExtra("url");
         showmeidialog();
         initHandler();
         pictureSetting();
         CreateThread();
    }

    @Override
    void setControlListener() {
        sv_view.setOnTouchListener(this);
    }

    private void pictureSetting() {
        mQueue = Volley.newRequestQueue(InformatiinListActivity.this);
        imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
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
        Toast.makeText(InformatiinListActivity.this, str, Toast.LENGTH_SHORT).show();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void AddView(int line) {
        for (int i = 0; i < line; i++) {
            CreatView(Integer.valueOf(i+"1"),Integer.valueOf(i+"2"),Integer.valueOf(i+"3"),Integer.valueOf(i+"4"),Integer.valueOf(i+"5"),Integer.valueOf(i+"6"),Integer.valueOf(i+"7"),i);
            AddDate(title,content,time,from,clickNumber,i);
        }
        dialog.dismiss();
    }

    private void AddDate(String[] title,String[] content,String[] time,String[] from,String[] clickNumber,int i) {
        tv_title.setText(title[i]);
        tv_content.setText(content[i]);
        tv_time.setText(time[i]);
        if (i>=tv_from.length()){
            tv_from.setText("科技文献共享平台");
        }else {
            tv_from.setText(from[i]);
        }
        tv_time.setText(time[i]);
        tv_clicknumber.setText(clickNumber[i]);
        netpicture.setDefaultImageResId(R.drawable.picture_load);
        netpicture.setErrorImageResId(R.drawable.picture_error);
        netpicture.setImageUrl(pictureUrl.get(i), imageLoader);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void CreatView(int relativeLayoutId, int netpictureId, int titleId, int contentId, int timeId, int fromId, int clickNumberId, int i) {

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

        layoutParams=new RelativeLayout.LayoutParams(200,250);
        layoutParams.setMargins(10,10,10,10);
        netpicture=new NetworkImageView(this);
        netpicture.setLayoutParams(layoutParams);
        netpicture.setId(netpictureId);

        relativeLayout.addView(netpicture);

        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,0,10,0);
        layoutParams.addRule(RelativeLayout.RIGHT_OF,netpicture.getId());
        tv_title=new TextView(this);
        tv_title.setLayoutParams(layoutParams);
        tv_title.setId(titleId);
        tv_title.setLines(2);
        tv_title.setEllipsize(TextUtils.TruncateAt.END);
        tv_title.setTextColor(Color.parseColor("#FF000000"));
        tv_title.setTextSize(20.0f);

        relativeLayout.addView(tv_title);

        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,0,10,0);
        layoutParams.addRule(RelativeLayout.BELOW,tv_title.getId());
        layoutParams.addRule(RelativeLayout.RIGHT_OF,netpicture.getId());
        tv_content=new TextView(this);
        tv_content.setLayoutParams(layoutParams);
        tv_content.setId(contentId);
        tv_content.setTextColor(Color.parseColor("#252323"));
        tv_content.setTextSize(13.0f);
        tv_content.setLines(3);
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
        layoutParams.setMargins(10,0,0,10);
        layoutParams.addRule(RelativeLayout.BELOW,tv_content.getId());
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tv_from=new TextView(this);
        tv_from.setLayoutParams(layoutParams);
        tv_from.setId(fromId);

        relativeLayout.addView(tv_from);

        layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,0,20,10);
        layoutParams.addRule(RelativeLayout.BELOW,tv_content.getId());
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        tv_clicknumber=new TextView(this);
        tv_clicknumber.setLayoutParams(layoutParams);
        tv_clicknumber.setId(clickNumberId);

        relativeLayout.addView(tv_clicknumber);
    }

    private void Jump(int i) {
        Intent intent=new Intent(InformatiinListActivity.this,MainActivity.class);
        intent.putExtra("url",url[i]);
        startActivity(intent);
    }

    private void CreateThread() {
        Thread thread = new Thread() {

            @Override
            public void run() {
                super.run();

                try {
                    Document  doc = Jsoup.connect(URL).timeout(5000).get();
                    Document doc2;
                    Log.e("网页总标题", doc.title());
                    Log.e("数据行数", doc.body().select("p").size() + "");
                    int lines=doc.body().select("a.font13.aral.blue1").size();
                    title=new String[lines];
                    content=new String[lines];
                    time=new String[lines];
                    from=new String[lines];
                    clickNumber=new String[lines];
                    url=new String[lines];
                    for (int i=0;i<lines;i++){
                        title[i]=doc.body().select("a.font13.aral.blue1").get(i).text();
                        content[i]=doc.body().select("div.zxx_text_overflow_5").get(i).text();
                        time[i]=doc.body().select("span.date").get(i).text();
                        from[i]=doc.body().select("span.fl").select("a").get(i).text();
                        Log.e("超链接",doc.body().select("a.font13.aral.blue1").get(i).attr("href"));
                        url[i]="http://portal.nstl.gov.cn"+doc.body().select("a.font13.aral.blue1").get(i).attr("href");
                        doc2 = Jsoup.connect("http://portal.nstl.gov.cn"+doc.body().select("a.font13.aral.blue1").get(i).attr("href")).timeout(5000).get();
                        if (doc2.select("div.zoom.mt-15").select("img").attr("src").length()<30){
                            pictureUrl.add("http://bpic.588ku.com/element_origin_min_pic/17/04/14/61a6df84d26a81f05c9e22dbbebe4ef1.jpg");
                        }else {
                            pictureUrl.add("http://portal.nstl.gov.cn"+doc2.select("div.zoom.mt-15").select("img").attr("src").substring(0,doc2.select("div.zoom.mt-15").select("img").attr("src").indexOf(".jpg")+4));
                            Log.e("添加的图片的网址","http://portal.nstl.gov.cn"+doc2.select("div.zoom.mt-15").select("img").attr("src").substring(0,doc2.select("div.zoom.mt-15").select("img").attr("src").indexOf(".jpg")+4));
                        }
                    }
                    Log.e("点击量数据行数",doc.body().select("span.fl").size()+"");
                    for (int j=4;j<doc.body().select("span.fl").size();j++){
                       clickNumber[j-4]=doc.body().select("span.fl").get(j).text().substring( doc.body().select("span.fl").get(j).text().indexOf("点击量：")+4);
                    }

                    Log.e("网页数据", doc.body().select("p").text());
                    Log.e("日期", doc.body().select("span.date").text());
                    Log.e("标题", doc.body().select("a.font13.aral.blue1").text());

                    for (int i = 0; i < lines; i++) {
                        Log.e("摘要" + i, doc.body().select("div.zxx_text_overflow_5").get(i).text());
                    }
                    Log.e("来源机构", doc.body().select("span.fl").select("a").text());
                    for (int j=0;j<doc.body().select("span.fl").size();j++){
                        Log.e("点击量", doc.body().select("span.fl").get(j).text());
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
