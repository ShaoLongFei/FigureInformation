package util;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.kys_31.figureinformation.R;
import com.iflytek.cloud.Setting;

/**
 * Created by 张同心 on 2017/9/26.
 * @function 视图工具类
 */

public class ViewUtil {

    /**
     * 改变Dialog大小
     * @param dialog
     * @param context
     * @param width
     * @param height
     */
    public static void setDialogWindowAttr(Dialog dialog, int width, int height){
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.ActionSheetDialogAnimation);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = width;
        lp.height = height;
        dialog.getWindow().setAttributes(lp);
    }

    /**
     * 设置屏幕亮度
     * @param activity 环境
     * @param value 亮度值
     */
    public static void setScreenBrightness(Activity activity, int value){
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.screenBrightness = value / 255f;
        activity.getWindow().setAttributes(params);
    }

    /**
     * 获取屏幕亮度
     * @param activity
     * @return
     */
  public static int getScreenBrightness(Activity activity){
      int value = 0;
      ContentResolver cr = activity.getContentResolver();
      try {
          value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
      }catch (Exception e){
          e.printStackTrace();
      }
      return value;
  }

}
