package util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kys_31.figureinformation.LoginActivity;
import com.example.kys_31.figureinformation.R;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import data.HandleUserMessage;
import data.UserMessage;
import observer.LoginStateObservable;
import variable.UserMessageVariable;

/**
 * Created by 张同心 on 2017/9/21.
 * @function 第三方登录
 */

public class ThirdPartyLoginUtil {

    /**
     * 新浪授权
     */
    public static void authPlatform_XinLang(final Activity activity) {
        //获取具体的平台手动授权
        final Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        //设置回调监听
        weibo.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, final HashMap<String, Object> hashMap) {
                userLogin(platform, activity);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                activity.finish();
                throwable.printStackTrace();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                weibo.removeAccount(true);
            }
        });
        //authorize与showUser单独调用一个即可，
        //单独授权,进入输入用户名和密码界面
        /*weibo.authorize();*/
        //授权并获取用户信息
        weibo.showUser(null);
    }

    /**
     * QQ 授权
     */
    public static void authPlatform_QQ(final Activity activity) {
        //获取QQ平台手动授权
        final Platform qq = ShareSDK.getPlatform(QQ.NAME);
        //设置回调监听
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, final HashMap<String, Object> hashMap) {
                userLogin(platform, activity);

            }
            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                activity.finish();
                throwable.printStackTrace();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                qq.removeAccount(true);
            }
        });
        qq.showUser(null);
    }
    /**
     * 微信授权
     */
    public static void authPlatform_Weixin(final Activity activity) {
        //设置平台手动授权
        final Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
        weixin.removeAccount(true);
        //设置监听回调
        weixin.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, final HashMap<String, Object> hashMap) {
                userLogin(platform, activity);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                activity.finish();
                throwable.printStackTrace();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                weixin.removeAccount(true);
            }
        });
        //单独授权,进入输入用户名和密码界面
        /*weixin.authorize();*/
        //授权并获取用户信息
        weixin.showUser(null);

    }

    /**
     * 用户登录
     * @param platform
     */
    private static void userLogin(Platform platform, Activity activity){
        UserMessageVariable.osUserMessage = new UserMessage(platform.getDb().getUserId(), platform.getDb().getUserId(),
               BitmapUtil.bitmapToString(BitmapUtil.URLToBitmap(platform.getDb().getUserIcon())), platform.getDb().getUserName(), "0", platform.getDb().getUserId(),
                platform.getDb().getUserGender().equals("m")?0:1, 0, "首次使用");
        HandleUserMessage.saveData( UserMessageVariable.osUserMessage);
        LoginStateObservable.getInstatnce().notificationOberver(true);
        AutoLoginUtil.autoLogin(activity);
        activity.finish();
    }
}
