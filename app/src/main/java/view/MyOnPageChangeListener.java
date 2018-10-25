package view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v4.view.ViewPager.SCROLL_STATE_SETTLING;

/**
 * 杨铭 Created by kys_35 on 2017/9/13.
 * <p>Email:771365380@qq.com</p>
 * <p>Mobile phone:15133350726</p>
 */
public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener
{
    private static final String TAG = "text_tag";
    private final Context context ;
    private ViewPager pager;
    private DynamicLine dynamicLine;
    private ViewPagerTitle viewPagerTitle;
    private int allLength;
    private int margin;
    private int fixLeftDis;

    private ArrayList<TextView> textViews;
    private int pageCount;
    private int screenWidth;
    private int lineWidth;
    private int everyLenth;
    private int lastPosition;
    private int dis;
    private int [] location = new int[2];
    private float starX;
    private float stopX;

    public MyOnPageChangeListener(Context context, ViewPager viewPager, DynamicLine dynamicLine, ViewPagerTitle viewPagerTitle, int allLength, int margin, int fixLeftDis)
    {
        this.context = context;
        this.pager = viewPager;
        this.dynamicLine = dynamicLine;
        this.viewPagerTitle = viewPagerTitle;
        this.fixLeftDis = fixLeftDis;

        textViews = viewPagerTitle.getTextViews();
        pageCount = textViews.size();
        screenWidth = Tool.getScreenWith(context);
        lineWidth = (int) Tool.getTextViewLength(textViews.get(0));
        everyLenth = allLength/pageCount;
        dis = margin;

    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
        if (lastPosition > position){
            dynamicLine.updateView((position + positionOffset) * everyLenth + dis + fixLeftDis,(lastPosition + 1) * everyLenth - dis);
        }else {
            if (positionOffset> 0.5f){
                positionOffset = 0.5f;
            }
            dynamicLine.updateView(lastPosition * everyLenth + fixLeftDis,(position + positionOffset * 2 ) * everyLenth +dis + lineWidth);

        }
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG,lastPosition + "[[[[");
        viewPagerTitle.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {
        boolean scrollRight;// 页面向右
        if (state == SCROLL_STATE_SETTLING){
            scrollRight = lastPosition < pager.getCurrentItem();
            lastPosition = pager.getCurrentItem();
            if (lastPosition + 1 <textViews.size() && lastPosition - 1 >= 0){
                textViews.get(scrollRight ? lastPosition + 1 : lastPosition - 1).getLocationOnScreen(location);
                if (location[0] > screenWidth){
                    viewPagerTitle.smoothScrollBy(screenWidth / 2,0);
                }else if (location[0] < 0){
                    viewPagerTitle.smoothScrollBy(-screenWidth / 2,0);
                }
            }
        }

    }


}
