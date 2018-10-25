package util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.view.Display;
import android.view.WindowManager;

import org.jsoup.Connection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 张同心 on 2017/9/25.
 * @function 图片工具类
 */

public class BitmapUtil {

    /**
     * 根据屏幕大小自动适应图片
     * @param context
     * @return 适应后的图片
     */
    public static Bitmap FitTheScreenSizeImage(Context context, int drawableID) {
        Display displayWindow = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int screenWidth = displayWindow.getWidth();
        int screenHeight = displayWindow.getHeight();
        Bitmap bitmapDoing = BitmapFactory.decodeResource(context.getResources(), drawableID);
        int bitmapDoingWidth = bitmapDoing.getWidth();
        int bitmapDoingHeight = bitmapDoing.getHeight();
        float width = (float)screenWidth/bitmapDoing.getWidth();
        float height = (float)screenHeight/bitmapDoing.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(width, height);
        return Bitmap.createBitmap(bitmapDoing, 0, 0, bitmapDoingWidth, bitmapDoingHeight,matrix,true);
    }

    /**
     * 图片的URL转Bitmap
     */

    public static Bitmap URLToBitmap(String url){
        URL fileUrl = null;
        Bitmap bitmap = null;
        try {
            fileUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) fileUrl.openConnection();
            connection.setDoInput(true);
            connection.connect();
           bitmap =  BitmapFactory.decodeStream(connection.getInputStream());
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * Bitmap转字符串
     * @param bitmap
     * @return 字符串
     */
    public static String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, arrayOutputStream);
        byte[] picture = arrayOutputStream.toByteArray();
        return Base64.encodeToString(picture, Base64.DEFAULT);
    }

    /**
     * 字符串转Bitmap
     * @param strBitmap
     * @return Bitmap
     */
    public static Bitmap stringToBitmap(String strBitmap){
        Bitmap bitmap = null;
        byte[] bitmapArray;
        bitmapArray = Base64.decode(strBitmap, Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        return bitmap;
    }
}
