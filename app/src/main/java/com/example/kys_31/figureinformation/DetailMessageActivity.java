package com.example.kys_31.figureinformation;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Observable;

import data.HandleWebDataMessage;
import data.WebDataMessage;
import database.DBUtil;
import util.SharedUtil;
import variable.LoginStateVariable;
import variable.UserMessageVariable;
import view.MyScrollView;

public class DetailMessageActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    //控件
    private LinearLayout ll_view;
    private TextView[] tv1;
    private TextView tv_title;
    private TextView tv_author;
    private TextView tv_time;
    private TextView tv_clickNumber;
    private MyScrollView sv_view;
    private LinearLayout ll_back;
    private ImageView iv_share;
    private ImageView mIvCollection;
    private LinearLayout ll_load;
    private LinearLayout ll_refresh;
    private TextView si_author;
    private TextView si_time;
    private TextView si_clickNumber;
    private ImageView iv_voice;
    private NetworkImageView networkImageView;


    private Handler viewHandler;
    private String author;
    private String time;
    private String clickNumber;
    private String title;
    private String content;
    private SpeechSynthesizer mySynthesizer;
    private boolean playing=false;
    private boolean played=false;
    private String URL;
    private String pictureURL;
    private WebDataMessage mWebDataMessage;
    private int mPosition;
    private TextView tv_content;
    private boolean isCollection=false;
    private boolean isSave=false;
    private boolean isJudge=true;


    @Override
    protected int getLayoutID() {
        return R.layout.main;
    }
    protected void initControl() {
        mIvCollection = (ImageView)findViewById(R.id.collection_DetailMessage_iv);
        tv_title=(TextView)findViewById(R.id.tv_title);
        tv_title=(TextView)findViewById(R.id.tv_title);
        tv_author=(TextView)findViewById(R.id.tv_author);
        tv_time=(TextView)findViewById(R.id.tv_time);
        tv_clickNumber=(TextView)findViewById(R.id.tv_clickNumber);
        sv_view=(MyScrollView)findViewById(R.id.sv_view);
        iv_share=(ImageView)findViewById(R.id.iv_share);
        ll_load=(LinearLayout)findViewById(R.id.ll_load);
        ll_back=(LinearLayout)findViewById(R.id.ll_back);
        ll_refresh=(LinearLayout)findViewById(R.id.ll_refresh);
        si_author=(TextView)findViewById(R.id.si_author);
        si_time=(TextView)findViewById(R.id.si_time);
        si_clickNumber=(TextView)findViewById(R.id.si_clickNumber);
        iv_voice=(ImageView)findViewById(R.id.iv_voice);
        networkImageView=(NetworkImageView)findViewById(R.id.network_img);
        tv_content=(TextView)findViewById(R.id.tv_content);

        initHandler();
        mPosition = getIntent().getIntExtra("position",-1);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        mWebDataMessage =(WebDataMessage)bundle.get("webDataMessage");
        pictureURL = mWebDataMessage.oPictureURL[mPosition];
        title = mWebDataMessage.oTitleName[mPosition];
        URL = mWebDataMessage.oURL[mPosition];
        content=mWebDataMessage.oContent[mPosition];
        title=mWebDataMessage.oTitleName[mPosition];
        author=mWebDataMessage.oStrAuthor[mPosition];
        time=mWebDataMessage.oTime[mPosition];
        clickNumber=mWebDataMessage.oLookCount[mPosition];
        if (mWebDataMessage.oStrAuthor[mPosition] == null){
            CreateThread();
        }
        showTitle();
    }

    private void collectionState() {
        if(LoginStateVariable.osLoginState){
            if (DBUtil.getInstance(DetailMessageActivity.this).selectCollectionFuhe(UserMessageVariable.osUserMessage.oStrPhoneNumber,title)>0){
                isCollection=true;
            }
        }
        if (isCollection){
            mIvCollection.setImageResource(R.drawable.collection);
            isSave=true;
            Log.e("收藏","改变图片");

        }else {
            mIvCollection.setImageResource(R.drawable.nocollection);
            Log.e("不收藏","改变图片");
        }
    }

    private void showPicture(){
        RequestQueue mQueue = Volley.newRequestQueue(DetailMessageActivity.this);
        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });

        networkImageView.setDefaultImageResId(R.drawable.picture_load);
        networkImageView.setErrorImageResId(R.drawable.picture_error);
        networkImageView.setImageUrl(pictureURL, imageLoader);
    }

    @Override
    void setControlListener() {
        iv_share.setOnClickListener(this);
        sv_view.setOnTouchListener(this);
        ll_back.setOnClickListener(this);
        iv_voice.setOnClickListener(this);
        mIvCollection.setOnClickListener(this);
    }


    private void initHandler() {
        viewHandler=new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        ll_load.setVisibility(View.GONE);
                        break;
                    case 2:
                        ll_refresh.setVisibility(View.GONE);
                        break;
                    case 3:
                        tv_author.setText(author);
                        break;
                }
            }
        };
    }

    private void showTitle(){
        showPicture();
        tv_title.setText(title);
        tv_author.setText(author);
        tv_time.setText(time);
        tv_clickNumber.setText(clickNumber);
        si_author.setText("编译者：");
        si_time.setText("编译时间：");
        si_clickNumber.setText("点击量：");
        tv_content.setText(content);
    }

    private void CreateThread() {
        Thread thread=new Thread(){

            @Override
            public void run() {
                super.run();

                try {
                    Document doc = Jsoup.connect(URL).timeout(5000).get();
                    author=doc.body().select("table").select("td").get(3).text();
                    Log.e("加载作者",author);
                    viewHandler.sendEmptyMessage(3);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                /*存入作者*/
                String[] authorString = new String[mWebDataMessage.oArticleCount];
                authorString[mPosition] = author;
                mWebDataMessage = new WebDataMessage(mWebDataMessage.oStrCategory, mWebDataMessage.oURL, mWebDataMessage.oTitleName, mWebDataMessage.oContent, mWebDataMessage.oTime, mWebDataMessage.oLookCount,mWebDataMessage.oPictureURL, mWebDataMessage.oStrFrom,authorString ,mWebDataMessage.oArticleCount);
                HandleWebDataMessage.saveWebDataMessageData(mWebDataMessage);

            }
        };
        thread.start();
    }

    private void speakTextMethod(String str) {
        SpeechUtility.createUtility(DetailMessageActivity.this, "appid=5760ba33");
        mySynthesizer = SpeechSynthesizer.createSynthesizer(this, myInitListener);
        mySynthesizer.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");
        mySynthesizer.setParameter(SpeechConstant.PITCH,"50");
        mySynthesizer.setParameter(SpeechConstant.VOLUME,"50");
        mySynthesizer.startSpeaking(str,mTtsListener);
    }


    private com.iflytek.cloud.SynthesizerListener mTtsListener = new com.iflytek.cloud.SynthesizerListener() {
        @Override
        public void onSpeakBegin() {
        }
        @Override
        public void onSpeakPaused() {
        }
        @Override
        public void onSpeakResumed() {
        }
        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
        }
        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        @Override
        public void onCompleted(SpeechError error) {
            if(error!=null)
            {
                Log.d("mySynthesiezer complete code:", error.getErrorCode()+"");
            }
            else
            {
                Log.d("mySynthesiezer complete code:", "0");
            }
        }
        @Override
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
            // TODO Auto-generated method stub

        }
    };


    private InitListener myInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d("mySynthesiezer:", "InitListener init() code = " + code);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.iv_voice:
                if (playing){
                    iv_voice.setBackgroundResource(R.drawable.play);
                    mySynthesizer.pauseSpeaking();
                    playing=false;
                }else {
                    if (!played){
                        speakTextMethod(content);
                        played=true;
                    }else {
                        mySynthesizer.resumeSpeaking();
                    }
                    iv_voice.setBackgroundResource(R.drawable.pause);
                    playing=true;
                }
                break;
            case R.id.iv_share:
                SharedUtil.showShare(DetailMessageActivity.this, pictureURL, title, URL, content, R.drawable.logo);
                break;
            case R.id.collection_DetailMessage_iv:
                if (LoginStateVariable.osLoginState==false){
                    startActivity(new Intent(DetailMessageActivity.this,LoginActivity.class));
                }else {
                    if (isCollection){
                        mIvCollection.setImageResource(R.drawable.nocollection);
                        isCollection=false;
                        isSave=false;
                        showToast("取消收藏",false);
                    }else {
                        mIvCollection.setImageResource(R.drawable.collection);
                        isCollection=true;
                        isSave=true;
                        showToast("已收藏",false);
                    }
                }

                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_MOVE: {

                if (sv_view.getScrollY() <= 0) {
                    ll_refresh.setVisibility(View.VISIBLE);
                    viewHandler.sendEmptyMessageDelayed(2,2000);
                }

                int measuredHeight = sv_view.getChildAt(0)
                        .getMeasuredHeight();
                int scrollY = sv_view.getScrollY();
                int height = sv_view.getHeight();
                if (measuredHeight <= scrollY + height) {
                    ll_load.setVisibility(View.VISIBLE);
                    viewHandler.sendEmptyMessageDelayed(1,2000);
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
        LoginStateVariable.osLoginState = (Boolean)o;
        if (isJudge){
            collectionState();
            isJudge=false;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mySynthesizer!=null){
            mySynthesizer.destroy();
        }


        if (UserMessageVariable.osUserMessage!=null){
            if (isSave){
                if (DBUtil.getInstance(DetailMessageActivity.this).selectCollectionFuhe(UserMessageVariable.osUserMessage.oStrPhoneNumber,title)==0) {
                    ContentValues values = new ContentValues();
                    values.put("phoneNumber", UserMessageVariable.osUserMessage.oStrPhoneNumber);
                    values.put("author", author);
                    values.put("time", time);
                    values.put("clickNumber", clickNumber);
                    values.put("title", title);
                    values.put("content", content);
                    values.put("pictureURL", pictureURL);
                    DBUtil.getInstance(DetailMessageActivity.this).insertCollection(values);
                    Log.e("结束界面", "收藏了");
                }
            }else {
                if (DBUtil.getInstance(DetailMessageActivity.this).selectCollectionFuhe(UserMessageVariable.osUserMessage.oStrPhoneNumber,title)>0){
                    DBUtil.getInstance(DetailMessageActivity.this).deleteCollection(UserMessageVariable.osUserMessage.oStrPhoneNumber,title);
                    Log.e("数据库删除成功","没了");
                }
            }
        }
    }
}
