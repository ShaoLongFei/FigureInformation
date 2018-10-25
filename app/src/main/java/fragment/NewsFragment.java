package fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

import data.HandleWebDataMessage;
import data.WebDataMessage;
import view.CatLoadingView;
import view.MyScrollView;


public class NewsFragment extends Fragment implements View.OnTouchListener{
	String text;
	int id;
	View view;

	private volatile MyScrollView sv_view;
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
	private int lines;
	private RelativeLayout.LayoutParams layoutParams;
	private RelativeLayout relativeLayout;
	private TextView tv_title;
	private TextView tv_content;
	private TextView tv_time;
	private TextView tv_from;
	private TextView tv_clicknumber;
	private RelativeLayout space;
	private String URL;
	private ImageLoader imageLoader;
	private RequestQueue mQueue;
	private NetworkImageView netpicture;
	private int layout =0;

	private CatLoadingView mView;
	private boolean canRefresh=true;
	private String mStrLoadThreadLock = "loadThreadLock";

	private WebDataMessage mWebDataMessage;

	private Handler viewHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 1:
					ll_load.setVisibility(View.GONE);
					break;
				case 3:
					AddView(Integer.valueOf(msg.obj.toString()));
					break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle args = getArguments();
		text = args != null ? args.getString("text") : "";
		id = args != null ?  args.getInt("id",0) :0;
		Log.e("TAG", "onCreate:"+text);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.main_layout, null);
		Log.e("TAG", "onCreateView:"+text);
		loadView();
		return view;
	}

	private void loadView(){
		if (HandleWebDataMessage.webDataMessageExist(text)){
			initControl();
			addListener();
			pictureSetting();
			showmeidialog();
			mWebDataMessage = HandleWebDataMessage.readWebDataMessageMessage(text);
			lines = mWebDataMessage.oArticleCount;
			time = mWebDataMessage.oTime;
			clickNumber = mWebDataMessage.oLookCount;
			title = mWebDataMessage.oTitleName;
			content = mWebDataMessage.oContent;
			url = mWebDataMessage.oURL;
			from = mWebDataMessage.oStrFrom;
			pictureUrl = mWebDataMessage.oPictureURL;
			AddView(lines);
		}else {
			loadDataMessage(text);
		}
	}

	private void loadDataMessage(String category){
		switch (category){
			case "可再生能源":
				layout= R.layout.main_layout;
				URL="http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm;jsessionid=C6E1686738E860D7CF9A735C35FEB6CA?parentPageId=1505810791964&directionId=4&controlType=openhome";
				GeneralMethod();
				break;
			case "纳米科技" :
				layout = R.layout.main_layout;
				URL="http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505811585475&directionId=1&controlType=openhome";
				GeneralMethod();
				break;
			case "食物营养" :
				layout = R.layout.main_layout;
				URL="http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505811793424&directionId=5&controlType=openhome";
				GeneralMethod();
				break;
			case "油气类" :
				layout = R.layout.main_layout;
				URL="http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813148957&directionId=19&controlType=openhome";
				GeneralMethod();
				break;
			case "水体污染治理" :
				layout = R.layout.main_layout;
				URL="http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813167069&directionId=8&controlType=openhome";
				GeneralMethod();
				break;
			case "大气污染防治" :
				layout = R.layout.main_layout;
				URL="http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813183979&directionId=20&controlType=openhome";
				GeneralMethod();
				break;
			case "集成电路装备" :
				layout = R.layout.main_layout;
				URL="http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813203183&directionId=7&controlType=openhome";
				GeneralMethod();
				break;
			case "数控机床":
				layout = R.layout.main_layout;
				URL="http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813218549&directionId=9&controlType=openhome";
				GeneralMethod();
				break;
			case "转基因生物":
				layout = R.layout.main_layout;
				URL="http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813284131&directionId=12&controlType=openhome";
				GeneralMethod();
				break;
			case "农业污染防治" :
				layout = R.layout.main_layout;
				URL="http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813301166&directionId=6&controlType=openhome";
				GeneralMethod();
				break;
			case "宽带移动通信" :
				layout = R.layout.main_layout;
				URL="http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813316922&directionId=2&controlType=openhome";
				GeneralMethod();
				break;
			case "新药创制" :
				layout = R.layout.main_layout;
				URL="http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813335018&directionId=10&controlType=openhome";
				GeneralMethod();
				break;
			case "重大传染防治":
				layout = R.layout.main_layout;
				URL="http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm?parentPageId=1505813348341&directionId=3&controlType=openhome";
				GeneralMethod();
				break;
			case "重要报告" :
				layout = R.layout.main_layout;
				URL="http://portal.nstl.gov.cn/STMonitor/home/bg.htm?serverId=82";
				GeneralMethod();
				break;
			case "编译报道" :
				layout = R.layout.main_layout;
				URL="http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendspecial.htm?parentPageId=1505653299236&serverId=82";
				GeneralMethod();
				break;
			default:
				layout= R.layout.main_layout;
				URL="http://portal.nstl.gov.cn/STMonitor/home/resource_more_recommendadd.htm;jsessionid=C6E1686738E860D7CF9A735C35FEB6CA?parentPageId=1505810791964&directionId=4&controlType=openhome";
				GeneralMethod();
				break;
		}
	}

	private void GeneralMethod() {
		initControl();
		addListener();
		pictureSetting();
		showmeidialog();
		CreateThread();
	}

	private void showmeidialog(){
		mView = new CatLoadingView();
		mView.show(getChildFragmentManager(),"");
	}

	private void addListener() {
		sv_view.setOnTouchListener(this);
	}


	private void initControl() {
		sv_view = (MyScrollView)view.findViewById(R.id.sv_view);
		ll_load = (LinearLayout)view.findViewById(R.id.ll_load);
		ll_refresh = (LinearLayout)view.findViewById(R.id.ll_refresh);
		ll_information = (LinearLayout)view.findViewById(R.id.ll_information);
	}

	private void pictureSetting() {
		mQueue = Volley.newRequestQueue(getContext());
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



	private void AddView(int line) {
		for (int i = 0; i < line; i++) {
			CreatView(Integer.valueOf(i+"1"), Integer.valueOf(i+"2"), Integer.valueOf(i+"3"), Integer.valueOf(i+"4"), Integer.valueOf(i+"5"), Integer.valueOf(i+"6"), Integer.valueOf(i+"7"),i);
			AddDate(title,content,time,from,clickNumber,i);
		}
		mView.dismiss();
		canRefresh=true;
	}

	private void AddDate(String[] title, String[] content, String[] time, String[] from, String[] clickNumber, int i) {
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

	private void CreatView(int relativeLayoutId,int netpictureId,int titleId,int contentId,int timeId,int fromId,int clickNumberId,int i) {

		layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 20);
		layoutParams.setMargins(10,20,10,0);
		space = new RelativeLayout(getContext());
		space.setLayoutParams(layoutParams);

		ll_information.addView(space);

		layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(10,20,10,0);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		relativeLayout = new RelativeLayout(getContext());
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
		netpicture=new NetworkImageView(getContext());
		netpicture.setLayoutParams(layoutParams);
		netpicture.setId(netpictureId);

		relativeLayout.addView(netpicture);

		layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(10,0,10,0);
		layoutParams.addRule(RelativeLayout.RIGHT_OF,netpicture.getId());
		tv_title=new TextView(getContext());
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
		tv_content=new TextView(getContext());
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
		tv_time=new TextView(getContext());
		tv_time.setLayoutParams(layoutParams);
		tv_time.setId(timeId);

		relativeLayout.addView(tv_time);

		layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(10,0,0,10);
		layoutParams.addRule(RelativeLayout.BELOW,tv_content.getId());
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		tv_from=new TextView(getContext());
		tv_from.setLayoutParams(layoutParams);
		tv_from.setId(fromId);

		relativeLayout.addView(tv_from);

		layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(10,0,20,10);
		layoutParams.addRule(RelativeLayout.BELOW,tv_content.getId());
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		tv_clicknumber=new TextView(getContext());
		tv_clicknumber.setLayoutParams(layoutParams);
		tv_clicknumber.setId(clickNumberId);

		relativeLayout.addView(tv_clicknumber);
	}

	private void Jump(int i) {
		Intent intent=new Intent(getContext(),DetailMessageActivity.class);
		intent.putExtra("position", i);
		Bundle bundle = new Bundle();
		bundle.putSerializable("webDataMessage", mWebDataMessage);
		intent.putExtra("bundle", bundle);
		startActivity(intent);
	}

	private void CreateThread() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				super.run();
				synchronized (mStrLoadThreadLock){
					try {
						Log.e("主页开始加载",text);
						Document doc = Jsoup.connect(URL).timeout(10000).get();
						Document doc2;
						Log.e("网页总标题", doc.title());
						Log.e("数据行数", doc.body().select("p").size() + "");
						lines=doc.body().select("a.font13.aral.blue1").size();
						title=new String[lines];
						content=new String[lines];
						time=new String[lines];
						from=new String[lines];
						clickNumber=new String[lines];
						url=new String[lines];
						pictureUrl = new String[lines];
						for (int i=0;i<lines;i++){
							title[i]=doc.body().select("a.font13.aral.blue1").get(i).text();
							content[i]=doc.body().select("div.zxx_text_overflow_5").get(i).text();
							time[i]=doc.body().select("span.date").get(i).text();
							from[i]=doc.body().select("span.fl").select("a").get(i).text();
							Log.e("超链接",doc.body().select("a.font13.aral.blue1").get(i).attr("href"));
							url[i]="http://portal.nstl.gov.cn"+doc.body().select("a.font13.aral.blue1").get(i).attr("href");
							doc2 = Jsoup.connect("http://portal.nstl.gov.cn"+doc.body().select("a.font13.aral.blue1").get(i).attr("href")).timeout(5000).get();
							if (doc2.select("div.zoom.mt-15").select("img").attr("src").length()<30){
								pictureUrl[i] = "http://bpic.588ku.com/element_origin_min_pic/17/04/14/61a6df84d26a81f05c9e22dbbebe4ef1.jpg";
							}else {
								pictureUrl[i] = "http://portal.nstl.gov.cn"+doc2.select("div.zoom.mt-15").select("img").attr("src").substring(0,doc2.select("div.zoom.mt-15").select("img").attr("src").indexOf(".jpg")+4);
								Log.e("添加的图片的网址","http://portal.nstl.gov.cn"+doc2.select("div.zoom.mt-15").select("img").attr("src").substring(0,doc2.select("div.zoom.mt-15").select("img").attr("src").indexOf(".jpg")+4));
							}
						}
						Log.e("点击量数据行数",doc.body().select("span.fl").size()+"");
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
				}
				/*保存数据到本地*/
				mWebDataMessage = new WebDataMessage(text, url, title, content, time, clickNumber,pictureUrl, from,new String[lines] ,lines);
				HandleWebDataMessage.saveWebDataMessageData(mWebDataMessage);
			}


		};
		thread.start();
	}


	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {

		switch (motionEvent.getAction()) {
			case MotionEvent.ACTION_MOVE: {
				Log.e("TAG", " "+sv_view.getDistanceY());
				if (sv_view.getDistanceY() > 50  && sv_view.getDistanceY() < 70) {
					if (canRefresh){
						canRefresh=false;
						loadDataMessage(mWebDataMessage.oStrCategory);
					}

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



}
