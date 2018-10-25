package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kys_31.figureinformation.ChannelActivity;
import com.example.kys_31.figureinformation.R;

import java.util.ArrayList;
import java.util.Observable;

import adapter.NewsFragmentPagerAdapter;
import application.MyApplication;
import bean.ChannelItem;
import bean.ChannelManage;
import bean.NewsClassify;
import tool.BaseTools;
import view.ColumnHorizontalScrollView;

public class FirstPageFragment extends BaseFragment {

    /** 自定义HorizontalScrollView */
    private ColumnHorizontalScrollView mColumnHorizontalScrollView;
    private LinearLayout mRadioGroup_content;
    private LinearLayout ll_more_columns;
    private RelativeLayout rl_column;
    private ViewPager mViewPager;
    private ImageView button_more_columns;

    /** 新闻分类列表*/
    private ArrayList<NewsClassify> newsClassify=new ArrayList<NewsClassify>();

    /** 用户选择的新闻分类列表*/
    private ArrayList<ChannelItem> userChannelList=new ArrayList<ChannelItem>();

    /** 当前选中的栏目*/
    private int columnSelectIndex = 0;
    /** 左阴影部分*/
    public ImageView shade_left;
    /** 右阴影部分 */
    public ImageView shade_right;
    /** 屏幕宽度 */
    private int mScreenWidth = 0;
    /** Item宽度 */
    private int mItemWidth = 0;

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    /** 请求CODE */
    public final static int CHANNELREQUEST = 1;
    /** 调整返回的RESULTCODE */
    public final static int CHANNELRESULT = 10;


    @Override
    protected int getLayoutID() {
        return R.layout.firstpage_activity;
    }

    @Override
    protected void initControl() {
        mScreenWidth = BaseTools.getWindowsWidth(getActivity());
        mItemWidth = mScreenWidth / 7;// 一个Item宽度为屏幕的1/7
        mColumnHorizontalScrollView =  (ColumnHorizontalScrollView)view.findViewById(R.id.mColumnHorizontalScrollView);
        mRadioGroup_content = (LinearLayout)view.findViewById(R.id.mRadioGroup_content);
        ll_more_columns = (LinearLayout)view.findViewById(R.id.ll_more_columns);
        rl_column = (RelativeLayout)view.findViewById(R.id.rl_column);
        button_more_columns = (ImageView)view.findViewById(R.id.button_more_columns);
        mViewPager = (ViewPager)view.findViewById(R.id.mViewPager);
        shade_left = (ImageView)view.findViewById(R.id.shade_left);
        shade_right = (ImageView)view.findViewById(R.id.shade_right);
        button_more_columns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent_channel = new Intent(getActivity(), ChannelActivity.class);
                startActivityForResult(intent_channel, CHANNELREQUEST);

            }
        });
        setChangelView();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void showByLoginState() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     *  当栏目项发生变化时候调用
     * */
    private void setChangelView() {
        initColumnData();
        initTabColumn();
        initFragment();
    }

    /** 获取Column栏目 数据*/
    private void initColumnData() {
        userChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage(MyApplication.getApp().getSQLHelper()).getUserChannel());
    }

    /**
     *  初始化Column栏目项
     * */
    private void initTabColumn() {
        mRadioGroup_content.removeAllViews();
//        int count =  newsClassify.size();
        int count =  userChannelList.size();
        mColumnHorizontalScrollView.setParam(getActivity(), mScreenWidth, mRadioGroup_content, shade_left, shade_right, ll_more_columns, rl_column);
        for(int i = 0; i< count; i++){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth , ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 10;
            params.rightMargin = 10;
            //			TextView localTextView = (TextView) mInflater.inflate(R.layout.column_radio_item, null);
            TextView localTextView = new TextView(getActivity());
            localTextView.setTextAppearance(getActivity(), R.style.top_category_scroll_view_item_text);
            //			localTextView.setBackground(getResources().getDrawable(R.drawable.top_category_scroll_text_view_bg));
            localTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
            localTextView.setGravity(Gravity.CENTER);

            localTextView.setPadding(5, 0, 5, 0);

            localTextView.setId(i);
//            localTextView.setText(newsClassify.get(i).getTitle());
            localTextView.setText(userChannelList.get(i).getName());
            localTextView.setTextColor(getResources().getColorStateList(R.color.top_category_scroll_text_color_day));
            if(columnSelectIndex == i){
                localTextView.setSelected(true);
            }
            localTextView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    for(int i = 0;i < mRadioGroup_content.getChildCount();i++){
                        View localView = mRadioGroup_content.getChildAt(i);
                        if (localView != v)
                            localView.setSelected(false);
                        else{
                            localView.setSelected(true);
                            mViewPager.setCurrentItem(i);
                        }
                    }
                }
            });
            mRadioGroup_content.addView(localTextView, i ,params);
        }
    }
    /**
     *  选择的Column里面的Tab
     * */
    private void selectTab(int tab_postion) {
        columnSelectIndex = tab_postion;
        for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
            View checkView = mRadioGroup_content.getChildAt(tab_postion);
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - mScreenWidth / 2;
            // rg_nav_content.getParent()).smoothScrollTo(i2, 0);
            mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
            // mColumnHorizontalScrollView.smoothScrollTo((position - 2) *
            // mItemWidth , 0);
        }
        //判断是否选中
        for (int j = 0; j <  mRadioGroup_content.getChildCount(); j++) {
            View checkView = mRadioGroup_content.getChildAt(j);
            boolean ischeck;
            if (j == tab_postion) {
                ischeck = true;
            } else {
                ischeck = false;
            }
            checkView.setSelected(ischeck);
        }
    }

    /**
     *  初始化Fragment
     * */
    private void initFragment() {
        fragments.clear();//清空
        fragments=new ArrayList<Fragment>();
        Log.e("我看看",fragments+"");
        int count =  userChannelList.size();
        for(int i = 0; i< count;i++){
            Bundle data = new Bundle();
            data.putString("text", userChannelList.get(i).getName());
            data.putInt("id", userChannelList.get(i).getId());
            NewsFragment newfragment = new NewsFragment();
            newfragment.setArguments(data);
            fragments.add(newfragment);
            Log.e("我看看加了啥",fragments+"");
        }
        mViewPager.setAdapter(null);
        NewsFragmentPagerAdapter mAdapetr = new NewsFragmentPagerAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapetr);
        mViewPager.setOnPageChangeListener(pageListener);
    }

    /**
     *  ViewPager切换监听方法
     * */
    public ViewPager.OnPageChangeListener pageListener= new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            mViewPager.setCurrentItem(position);
            selectTab(position);
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case CHANNELREQUEST:
                if(resultCode == CHANNELRESULT){
                    setChangelView();
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onResume(){
        super.onResume();
    }
    @Override
    public void update(Observable o, Object arg) {

    }

}
