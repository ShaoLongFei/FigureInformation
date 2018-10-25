package fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.kys_31.figureinformation.DetailMessageActivity;
import com.example.kys_31.figureinformation.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import data.HandleWebDataMessage;
import data.WebDataMessage;
import variable.FancySelectVariable;
import view.CatLoadingView;
import view.MyScrollView;

/**
 * Created 张同心 on 2017/9/20.
 * @function 兴趣推荐
 */

public class InterestFragment extends BaseFragment implements View.OnTouchListener{

    //控件
    private MyScrollView sv_view;
    private LinearLayout ll_load;
    private LinearLayout ll_refresh;
    private LinearLayout ll_information;

    private String[] from;
    private String[] time;
    private String[] clickNumber;
    private String[] title;
    private String[] content;
    private String[] url;
    private String[] pictureUrl;
    private RelativeLayout.LayoutParams layoutParams;
    private RelativeLayout relativeLayout;
    private TextView tv_title;
    private TextView tv_content;
    private TextView tv_time;
    private TextView tv_from;
    private TextView tv_clicknumber;
    private NetworkImageView netpicture;
    private RelativeLayout space;
    private int lineMax=0;
    private ImageLoader imageLoader;
    private RequestQueue mQueue;
    private CatLoadingView mView;
    private int lines;
    private String mStrUrl;
    private String mLoadThreadLock = "loadThreadLock";
    private WebDataMessage mWebDataMessage;
    private List<WebDataMessage> mListWebDataMessage = new ArrayList<>();
    @Override
    protected int getLayoutID() {
        return R.layout.main_layout;
    }

    protected void initControl() {
        sv_view = (MyScrollView)view.findViewById(R.id.sv_view);
        ll_load = (LinearLayout)view.findViewById(R.id.ll_load);
        ll_refresh = (LinearLayout)view.findViewById(R.id.ll_refresh);
        ll_information = (LinearLayout)view.findViewById(R.id.ll_information);
        showmeidialog();
        pictureSetting();
        ChoiceInformation();
    }
    @Override
    protected void showByLoginState() {
        if (mLoginState){

        }else {

        }
    }

    @Override
    protected void setListener() {
        sv_view.setOnTouchListener(this);
    }
    @Override
    public void onClick(View view) {}
    private void showmeidialog(){
        if (mView==null){
            mView = new CatLoadingView();
            mView.show(getFragmentManager(), "");
            viewHandler.sendEmptyMessageDelayed(4,7000);
        }
    }

    private void pictureSetting() {
        mQueue = Volley.newRequestQueue(getActivity());
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

    private void ChoiceInformation() {
        try {
            if(FancySelectVariable.osFancySelected.contains("可再生能源")){
                mStrUrl = "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm;jsessionid=C6E1686738E860D7CF9A735C35FEB6CA?parentPageId=1505810791964&directionId=4&controlType=openhome";
                getDataMessage("可再生能源", mStrUrl);
            }
            if (FancySelectVariable.osFancySelected.contains("纳米科技")){
                mStrUrl = "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505811585475&directionId=1&controlType=openhome";
                getDataMessage("纳米科技", mStrUrl);
            }
            if (FancySelectVariable.osFancySelected.contains("食物营养")){
                mStrUrl = "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505811793424&directionId=5&controlType=openhome";
                getDataMessage("食物营养", mStrUrl);

            }
            if (FancySelectVariable.osFancySelected.contains("油气类")){
                mStrUrl = "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813148957&directionId=19&controlType=openhome";
                getDataMessage("油气类", mStrUrl);
            }
            if (FancySelectVariable.osFancySelected.contains("水体污染治理")){
                mStrUrl = "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813167069&directionId=8&controlType=openhome";
                getDataMessage("水体污染治理", mStrUrl);
            }
            if (FancySelectVariable.osFancySelected.contains("大气污染防治")){
                mStrUrl = "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813183979&directionId=20&controlType=openhome";
                getDataMessage("大气污染防治", mStrUrl);
            }
            if (FancySelectVariable.osFancySelected.contains("集成电路装备")){
                mStrUrl = "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813203183&directionId=7&controlType=openhome";
                getDataMessage("集成电路装备", mStrUrl);

            }
            if (FancySelectVariable.osFancySelected.contains("数控机床")){
                mStrUrl = "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813218549&directionId=9&controlType=openhome";
                getDataMessage("数控机床", mStrUrl);
            }
            if (FancySelectVariable.osFancySelected.contains("转基因生物")){
                mStrUrl = "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813284131&directionId=12&controlType=openhome";
                getDataMessage("转基因生物", mStrUrl);

            }
            if (FancySelectVariable.osFancySelected.contains("农业污染防治")){
                mStrUrl = "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813301166&directionId=6&controlType=openhome";
                getDataMessage("农业污染防治", mStrUrl);

            }
            if (FancySelectVariable.osFancySelected.contains("宽带移动通信")){
                mStrUrl = "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813316922&directionId=2&controlType=openhome";
                getDataMessage("宽带移动通信", mStrUrl);

            }
            if (FancySelectVariable.osFancySelected.contains("新药创制")){
                mStrUrl = "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813335018&directionId=10&controlType=openhome";
                getDataMessage("新药创制", mStrUrl);
            }
            if (FancySelectVariable.osFancySelected.contains("重大传染防治")){
                mStrUrl = "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813348341&directionId=3&controlType=openhome";
                getDataMessage("重大传染防治", mStrUrl);
            }
            if (FancySelectVariable.osFancySelected.contains("重要报告")){
                mStrUrl = "http://portal.nstl.gov.cn/STMonitor/home/bg.htm?serverId=82";
                getDataMessage("重要报告", mStrUrl);
            }
            if (FancySelectVariable.osFancySelected.contains("编译报道")){
                mStrUrl = "http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendspecial.htm?parentPageId=1505653299236&serverId=82";
                getDataMessage("编译报道", mStrUrl);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getDataMessage(String category, String mUrl) {
        if (HandleWebDataMessage.webDataMessageExist(category)){
            mWebDataMessage = HandleWebDataMessage.readWebDataMessageMessage(category);
            lines = mWebDataMessage.oArticleCount;
            time = mWebDataMessage.oTime;
            clickNumber = mWebDataMessage.oLookCount;
            title = mWebDataMessage.oTitleName;
            content = mWebDataMessage.oContent;
            url = mWebDataMessage.oURL;
            from = mWebDataMessage.oStrFrom;
            pictureUrl = mWebDataMessage.oPictureURL;
            AddView(lines);
            for (int i = 0; i < lines; i++){
                mListWebDataMessage.add(mWebDataMessage);
            }
        }else {
            showmeidialog();
            CreateThread(category, mUrl);
        }
    }


    Handler viewHandler = new Handler() {
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
                case 4:
                    mView.dismiss();
                    break;
            }
        }
    };


    private void AddView(int line) {
        for (int i = lineMax; i < line+lineMax; i++) {
            CreatView(Integer.valueOf(i+"1"),Integer.valueOf(i+"2"),Integer.valueOf(i+"3"),Integer.valueOf(i+"4"),Integer.valueOf(i+"5"),Integer.valueOf(i+"6"),Integer.valueOf(i+"6"),i, i-lineMax);
            AddDate(title,content,time,from,clickNumber,i-lineMax);
        }
        lineMax=lineMax+line;
         if (mView.isVisible()){
             mView.dismiss();
         }
    }

    private void AddDate(String[] title,String[] content,String[] time,String[] from,String[] clickumber,int i) {
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
        netpicture.setImageUrl(pictureUrl[i], imageLoader);
    }

    private void CreatView(int relativeLayoutId, int netpictureId, int titleId, int contentId, int timeId, int fromId, int clickNumberId, final int i, final int catogoryPosition) {

        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 20);
        layoutParams.setMargins(10,20,10,0);
        space = new RelativeLayout(getActivity());
        space.setLayoutParams(layoutParams);

        ll_information.addView(space);


        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,20,10,0);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        relativeLayout = new RelativeLayout(getActivity());
        relativeLayout.setLayoutParams(layoutParams);
        relativeLayout.setId(relativeLayoutId);
        relativeLayout.setBackgroundResource(R.drawable.information_background);
        relativeLayout.setElevation(10.0f);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Jump(i,catogoryPosition);
            }
        });
        ll_information.addView(relativeLayout);

        layoutParams=new RelativeLayout.LayoutParams(200,250);
        layoutParams.setMargins(10,10,10,10);
        netpicture=new NetworkImageView(getActivity());
        netpicture.setLayoutParams(layoutParams);
        netpicture.setId(netpictureId);

        relativeLayout.addView(netpicture);


        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,0,10,0);
        layoutParams.addRule(RelativeLayout.RIGHT_OF,netpicture.getId());
        tv_title=new TextView(getActivity());
        tv_title.setLayoutParams(layoutParams);
        tv_title.setId(titleId);
        tv_title.setTextColor(Color.parseColor("#FF000000"));
        tv_title.setTextSize(20.0f);
        tv_title.setLines(2);
        tv_title.setEllipsize(TextUtils.TruncateAt.END);

        relativeLayout.addView(tv_title);

        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,0,10,0);
        layoutParams.addRule(RelativeLayout.BELOW,tv_title.getId());
        layoutParams.addRule(RelativeLayout.RIGHT_OF,netpicture.getId());
        tv_content=new TextView(getActivity());
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
        tv_time=new TextView(getActivity());
        tv_time.setLayoutParams(layoutParams);
        tv_time.setId(timeId);

        relativeLayout.addView(tv_time);

        layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,0,0,10);
        layoutParams.addRule(RelativeLayout.BELOW,tv_content.getId());
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tv_from=new TextView(getActivity());
        tv_from.setLayoutParams(layoutParams);
        tv_from.setId(fromId);

        relativeLayout.addView(tv_from);

        layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,0,20,10);
        layoutParams.addRule(RelativeLayout.BELOW,tv_content.getId());
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        tv_clicknumber=new TextView(getActivity());
        tv_clicknumber.setLayoutParams(layoutParams);
        tv_clicknumber.setId(clickNumberId);
        relativeLayout.addView(tv_clicknumber);

    }

    private void Jump(int i,int pointint) {
        Intent intent=new Intent(getContext(),DetailMessageActivity.class);
        intent.putExtra("position", pointint);
        Bundle bundle = new Bundle();
        bundle.putSerializable("webDataMessage", mListWebDataMessage.get(i));
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }


    private void CreateThread(final String paramCategory, final String paramUrl) {
        synchronized (mLoadThreadLock){
            Thread thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    String strCategory = paramCategory;
                    String strUrl = paramUrl;
                    try {
                        Document doc = Jsoup.connect(strUrl).timeout(15000).get();
                        Document doc2;
                        lines=doc.body().select("a.font13.aral.blue1").size();
                        title=new String[lines];
                        content=new String[lines];
                        time=new String[lines];
                        from=new String[lines];
                        clickNumber=new String[lines];
                        url = new String[lines];
                        pictureUrl = new String[lines];
                        for (int i = 0;i < lines;i++){
                            title[i]=doc.body().select("a.font13.aral.blue1").get(i).text();
                            content[i]=doc.body().select("div.zxx_text_overflow_5").get(i).text();
                            time[i]=doc.body().select("span.date").get(i).text();
                            from[i]=doc.body().select("span.fl").select("a").get(i).text();
                            Log.e("超链接",doc.body().select("a.font13.aral.blue1").get(i).attr("href"));
                            url[i] = "http://portal.nstl.gov.cn"+doc.body().select("a.font13.aral.blue1").get(i).attr("href");
                            doc2 = Jsoup.connect("http://portal.nstl.gov.cn"+doc.body().select("a.font13.aral.blue1").get(i).attr("href")).timeout(5000).get();
                            if (doc2.select("div.zoom.mt-15").select("img").attr("src").length()<30){
                                pictureUrl[i] = "http://bpic.588ku.com/element_origin_min_pic/17/04/14/61a6df84d26a81f05c9e22dbbebe4ef1.jpg";
                            }else {
                                pictureUrl[i] = "http://portal.nstl.gov.cn"+doc2.select("div.zoom.mt-15").select("img").attr("src").substring(0,doc2.select("div.zoom.mt-15").select("img").attr("src").indexOf(".jpg")+4);
                                Log.e("添加的图片的网址","http://portal.nstl.gov.cn"+doc2.select("div.zoom.mt-15").select("img").attr("src").substring(0,doc2.select("div.zoom.mt-15").select("img").attr("src").indexOf(".jpg")+4));
                            }

                        }
                        for (int j=4;j<doc.body().select("span.fl").size();j++){
                            clickNumber[j-4]=doc.body().select("span.fl").get(j).text().substring( doc.body().select("span.fl").get(j).text().indexOf("点击量：")+4);
                        }

                        Message message=new Message();
                        message.what=3;
                        message.obj=lines;
                        viewHandler.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                      /*保存数据到本地*/
                    WebDataMessage webDataMessage = new WebDataMessage(strCategory, url, title, content, time, clickNumber,pictureUrl, from, new String[lines] ,lines);
                    HandleWebDataMessage.saveWebDataMessageData(webDataMessage);
                    for (int i = 0; i < lines; i++){
                        mListWebDataMessage.add(webDataMessage);
                    }
                }
            };
            thread.start();
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
