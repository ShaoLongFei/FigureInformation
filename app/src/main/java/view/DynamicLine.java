package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * 杨铭 Created by kys_35 on 2017/9/13.
 * <p>Email:771365380@qq.com</p>
 * <p>Mobile phone:15133350726</p>
 */
public class DynamicLine extends View
{

    private int lineHeight;
    private int shaderColorEnd;
    private int shaderColorStart;
    private float startX;
    private float stopX;
    private Paint paint;
    private RectF rectf = new RectF(startX,0,startX,0);


    public DynamicLine(Context context)
    {
        super(context);
    }

    public DynamicLine(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public DynamicLine(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public DynamicLine(Context context, int shaderColorStart, int shaderColorEnd, int lineHeight)
    {
        this(context,null);

        this.shaderColorStart = shaderColorStart;
        this.shaderColorEnd = shaderColorEnd;
        this.lineHeight = lineHeight;
        init();
    }

    private void init()
    {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        paint.setShader(new LinearGradient(0,100,Tool.getScreenWith(getContext()),100,shaderColorStart,shaderColorEnd, Shader.TileMode.MIRROR));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(lineHeight, MeasureSpec.getMode(heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        rectf.set(startX,0,stopX,10);
        canvas.drawRoundRect(rectf,5,5,paint);
    }

    public void updateView(float startX, float stopX){

        this.startX = startX;
        this.stopX = stopX;
        invalidate();
    }
}
