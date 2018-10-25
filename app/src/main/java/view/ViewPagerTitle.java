package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kys_31.figureinformation.R;

import java.util.ArrayList;

import adapter.MyPagerAdapter;

import static view.Tool.getScreenWith;
import static view.Tool.getTextViewLength;


/**
 * 杨铭 Created by kys_35 on 2017/9/13.
 * <p>Email:771365380@qq.com</p>
 * <p>Mobile phone:15133350726</p>
 */

public class ViewPagerTitle extends HorizontalScrollView
{


    private String[] titles;
    private ArrayList<TextView> textViews = new ArrayList<>();
    private OnTextViewClick onTextViewClick;
    private DynamicLine dynamicLine;
    private ViewPager viewPager;
    private MyOnPageChangeListener myOnPageChangeListener;
    private int margin;
    private LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private float defaultTextSize;
    private float selectedTextSize;
    private int defaultTextColor;
    private int lineHeight;
    private int shaderColorEnd;
    private int shaderColorStart;

    private int slectedTextColor;
    private int allTextViewLength;
    private int backgroundColor;
    private float itemMargins;
    private int backgroundContentColor;



    private void initAttributeSet(Context context, AttributeSet attrs)
    {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerTitle);
        defaultTextColor = array.getColor(R.styleable.ViewPagerTitle_defaultTextViewColor, Color.GRAY);
        slectedTextColor = array.getColor(R.styleable.ViewPagerTitle_selectedTextViewColor, Color.BLACK);
        defaultTextSize = array.getDimension(R.styleable.ViewPagerTitle_defaultTextViewSize,18);
        selectedTextSize = array.getDimension(R.styleable.ViewPagerTitle_defaultTextViewSize,22);
        backgroundColor = array.getColor(R.styleable.ViewPagerTitle_background_content_color, Color.WHITE);
        itemMargins = array.getDimension(R.styleable.ViewPagerTitle_item_margins,30);

        shaderColorStart = array.getColor(R.styleable.ViewPagerTitle_line_start_color, Color.parseColor("#EA403C"));
        shaderColorEnd = array.getColor(R.styleable.ViewPagerTitle_line_end_color, Color.parseColor("#ff4500"));
        lineHeight = array.getInteger(R.styleable.ViewPagerTitle_line_height,20);

        array.recycle();
    }

    /**
     * 初始化时，调用这个方法。ViewPager需要设置Adapter，且titles的数据长度需要与Adapter中的数据长度一置。
     * @param titles 标题1、标题2 etc
     * @param viewPager
     * @param defaultIndex 默认选择的第几个页面
     */
    public void initData(String[] titles, ViewPager viewPager, int defaultIndex){
        this.titles = titles;
        this.viewPager = viewPager;
        createDynamicLine();
        createTextViews(titles);

        int fixLeftDis = getFixLeftDis();
        myOnPageChangeListener = new MyOnPageChangeListener(getContext(),viewPager,dynamicLine,this,allTextViewLength,margin,fixLeftDis);
        setDefaultIndex(defaultIndex);
        viewPager.addOnPageChangeListener(myOnPageChangeListener);
    }

    /**
     * 该方法修正TextView左右边距的
     * 每个TextView而言LeftMargins+ TextViewLength+
     * @return
     */
    private int getFixLeftDis() {
        TextView textView = new TextView(getContext());
        textView.setTextSize(defaultTextSize);
        textView.setText(titles[0]);
        float defaultTextSize = getTextViewLength(textView);
        textView.setTextSize(selectedTextSize);
        float selectedTextSize = getTextViewLength(textView);
        return (int) ((selectedTextSize - defaultTextSize)/2);
    }

    public ArrayList<TextView> getTextViews(){
        return textViews;
    }


    private void createDynamicLine()
    {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dynamicLine = new DynamicLine(getContext(),shaderColorStart,shaderColorEnd,lineHeight);
        dynamicLine.setLayoutParams(params);
    }

    private void createTextViews(String[] titles){

        LinearLayout contentL1 = new LinearLayout(getContext());
        contentL1.setBackgroundColor(backgroundColor);
        contentL1.setLayoutParams(contentParams);
        contentL1.setOrientation(LinearLayout.VERTICAL);
        addView(contentL1);

        LinearLayout textViewl1 = new LinearLayout(getContext());
        textViewl1.setBackgroundColor(backgroundColor);
        textViewl1.setOrientation(LinearLayout.HORIZONTAL);

        margin = getTextViewMargins(titles);
        textViewParams.setMargins(margin,0,margin,0);

        for (int i = 0;i<titles.length;i++){
            TextView textView = new TextView(getContext());
            textView.setText(titles[i]);
            textView.setTextColor(Color.GRAY);
            textView.setTextSize(defaultTextSize);
            textView.setLayoutParams(textViewParams);
            textView.setOnClickListener(onClickListener);
            textView.setTag(i);
            textViews.add(textView);
            textViewl1.addView(textView);
        }
        contentL1.addView(textViewl1);
        contentL1.addView(dynamicLine);

    }

    private int getTextViewMargins(String[] titles)
    {
        float countLength = 0;
        TextView textView = new TextView(getContext());
        textView.setTextSize(defaultTextSize);
        TextPaint paint = textView.getPaint();

        for (int i = 0;i<titles.length;i++){
            countLength = countLength + itemMargins + paint.measureText(titles[i]) + itemMargins;
        }
        int screenWith = getScreenWith(getContext());

        if (countLength<=screenWith){
            allTextViewLength = screenWith;
            return (screenWith/titles.length - (int) paint.measureText(titles[0]))/2;
        }else{
            allTextViewLength = (int) countLength;
            return (int) itemMargins;
        }

    }

    private OnClickListener onClickListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            setCurrentItem((int) v.getTag());
            viewPager.setCurrentItem((int) v.getTag());
            if (onTextViewClick != null){
                onTextViewClick.textViewClick((TextView)v, (Integer) v.getTag());
            }
        }
    };

    private void setDefaultIndex(int index)
    {
        setCurrentItem(index);
    }

   public void setCurrentItem(int index)
    {
        for (int i = 0;i < textViews.size();i++){
            if (i == index){
                textViews.get(i).setTextColor(slectedTextColor);
                textViews.get(i).setTextSize(selectedTextSize);
            }else {
                textViews.get(i).setTextColor(defaultTextColor);
                textViews.get(i).setTextSize(defaultTextSize);
            }
        }

    }

    private interface OnTextViewClick{
        void textViewClick(TextView textView, int index);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        viewPager.removeOnPageChangeListener(myOnPageChangeListener);
    }

    public ViewPagerTitle(Context context)
    {
        this(context,null);
    }

    public ViewPagerTitle(Context context, AttributeSet attrs)
    {
        this(context, attrs,0);
    }

    public ViewPagerTitle(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initAttributeSet(context,attrs);
    }

}
