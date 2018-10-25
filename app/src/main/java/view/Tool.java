package view;

import android.content.Context;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 杨铭 Created by kys_35 on 2017/9/16.
 * <p>Email:771365380@qq.com</p>
 * <p>Mobile phone:15133350726</p>
 */

public class Tool
{
    public static float getTextViewLength(TextView textView)
    {
        TextPaint paint = textView.getPaint();
        return  paint.measureText(textView.getText().toString());
    }

    public static float getTextViewLenth(TextView textView, float textSize)
    {
        TextPaint paint = textView.getPaint();
        paint.setTextSize(textSize);
        return paint.measureText(textView.getText().toString());
    }

    public static int getScreenWith(Context context)
    {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
