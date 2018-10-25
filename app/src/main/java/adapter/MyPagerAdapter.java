package adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import custom.ProgressDialogCustom;

import java.util.ArrayList;
import java.util.List;

import view.MyScrollView;

/**
 * 杨铭 Created by kys_35 on 2017/9/19.
 * <p>Email:771365380@qq.com</p>
 * <p>Mobile phone:15133350726</p>
 */

public class MyPagerAdapter extends PagerAdapter{
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
    private List<String> pictureUrl=new ArrayList<String>();
    private ProgressDialogCustom dialog;
    private final ArrayList<View> views;
    private Context context;
    private View mView;

    public MyPagerAdapter(ArrayList<View> views, Context context) {
        this.views = views;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        mView = views.get(position);
        return mView;
    }

}
