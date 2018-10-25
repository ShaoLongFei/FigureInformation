package util;

import android.content.Context;

/**
 * 杨铭 Created by kys_35 on 2017/9/24.
 * <p>Email:771365380@qq.com</p>
 * <p>Mobile phone:15133350726</p>
 */

public class DataToolsUtil
{
    /**
     * dipתΪ px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     *  px תΪ dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
