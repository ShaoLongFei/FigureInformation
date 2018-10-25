package com.example.kys_31.figureinformation;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javabean.BookBean;

/**
 * Created by 幽蓝丶流月 on 2017/9/20.
 */

public class SearchResultActivity extends AppCompatActivity {

    private TextView tv_name;
    private TextView tv_subtitle;
    private TextView tv_author;
    private TextView tv_summary;
    private TextView tv_publisher;
    private TextView tv_pubplace;
    private TextView tv_pubdate;
    private TextView tv_page;
    private TextView tv_price;
    private TextView tv_binding;
    private TextView tv_isbn;
    private TextView tv_isbn10;
    private TextView tv_edition;
    private TextView tv_impression;
    private TextView tv_language;
    private NetworkImageView netpicture;
    private String pictureUrl;
    private String ISBN;
    private String name;
    private String subtitle;
    private String author;
    private String summary;
    private String publisher;
    private String pubplace;
    private String pubdate;
    private String page;
    private String price;
    private String binding;
    private String isbn10;
    private String edition;
    private String impression;
    private String language;
    private String API_URL="http://api.jisuapi.com/isbn/query";
    private String API_KEY="88353a70e36f503a";
    public static Handler handler;
    private LinearLayout ll_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar=getSupportActionBar();
        bar.hide();
        ISBN=getIntent().getStringExtra("ISBN");
        setContentView(R.layout.search_reault);
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#EA403C"));
        initView();
        initHandler();
        RequestDate();

    }

    private void initHandler() {
        handler=new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                showPicture();
                showInfomation();
            }
        };
    }

    private void RequestDate() {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendMsg(ISBN);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void initView() {
        tv_name=(TextView)findViewById(R.id.tv_name);
        tv_subtitle=(TextView)findViewById(R.id.tv_subtitle);
        tv_author=(TextView)findViewById(R.id.tv_author);
        tv_summary=(TextView)findViewById(R.id.tv_summary);
        tv_publisher=(TextView)findViewById(R.id.tv_publisher);
        tv_pubplace=(TextView)findViewById(R.id.tv_pubplace);
        tv_pubdate=(TextView)findViewById(R.id.tv_pubdate);
        tv_page=(TextView)findViewById(R.id.tv_page);
        tv_binding=(TextView)findViewById(R.id.tv_binding);
        tv_price=(TextView)findViewById(R.id.tv_price);
        tv_isbn=(TextView)findViewById(R.id.tv_isbn);
        tv_isbn10=(TextView)findViewById(R.id.tv_isbn10);
        tv_edition=(TextView)findViewById(R.id.tv_edition);
        tv_impression=(TextView)findViewById(R.id.tv_impression);
        tv_language=(TextView)findViewById(R.id.tv_language);
        ll_back=(LinearLayout)findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Log.e("点击了退出","");
            }
        });
    }


    private void showPicture(){
        RequestQueue mQueue = Volley.newRequestQueue(SearchResultActivity.this);
        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        netpicture=(NetworkImageView) findViewById(R.id.netpicture);
        netpicture.setDefaultImageResId(R.drawable.picture_load);
        netpicture.setErrorImageResId(R.drawable.picture_error);
        Log.e("图片网址",pictureUrl);
        netpicture.setImageUrl(pictureUrl, imageLoader);
    }



    private void sendMsg(String msg) throws IOException, JSONException {
        String url = setParams(msg);
        Log.e("网址",url);
        String res = doGet(url);

        BookBean bookBean=new Gson().fromJson(res,BookBean.class);
        BookBean.ResultBean resultBean=bookBean.getResult();
        name=resultBean.getTitle();
        subtitle=resultBean.getSubtitle();
        author=resultBean.getAuthor();
        summary=resultBean.getSummary();
        publisher=resultBean.getPublisher();
        pubplace=resultBean.getPubplace();
        pubdate=resultBean.getPubdate();
        page=resultBean.getPage();
        binding=resultBean.getBinding();
        price=resultBean.getPrice();
        isbn10=resultBean.getIsbn10();
        edition=resultBean.getEdition();
        impression=resultBean.getImpression();
        language=resultBean.getLanguage();
        pictureUrl=resultBean.getPic();

       handler.sendEmptyMessage(1);

    }

    private void showInfomation() {
        initView();
        tv_name.setText(name);
        tv_subtitle.setText(subtitle);
        tv_author.setText(author);
        tv_summary.setText(summary);
        tv_publisher.setText(publisher);
        tv_pubplace.setText(pubplace);
        tv_pubdate.setText(pubdate);
        tv_page.setText(page);
        tv_binding.setText(binding);
        tv_price.setText(price);
        tv_isbn.setText(ISBN);
        tv_isbn10.setText(isbn10);
        tv_edition.setText(edition);
        tv_impression.setText(impression);
        tv_language.setText(language);
    }


    /**
     * 拼接Url
     *
     * @param msg
     * @return
     */
    private  String setParams(String msg) {
        try {
            msg = URLEncoder.encode(msg, "UTF-8");//特殊字符转换成转义字符
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return API_URL+ "?appkey=" + API_KEY + "&isbn=" + msg;
    }

    /**
     * Get请求，获得返回数据
     *
     * @param urlStr
     * @return
     */
    private String doGet(String urlStr) throws IOException {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;

        url = new URL(urlStr);
        conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5 * 1000);
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        String jsonString;
        Log.e("看看结果码",conn.getResponseCode()+"");
        if (conn.getResponseCode() == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            final StringBuffer responseResult = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                responseResult.append("\n").append(line);
            }
            jsonString = responseResult.toString();
        } else {
            jsonString = "null";
        }
        return jsonString;
    }

}
