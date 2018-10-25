package application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mob.MobSDK;
import com.mob.tools.proguard.ProtectedMemberKeeper;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import database.SQLHelper;
import util.TimeUtil;
import variable.FancySelectVariable;
import variable.PersionCenterVariable;
import variable.SystemSetVariable;

/**
 * Created by 张同心 on 2017/9/13.
 * @function 启动调用
 */

public class MyApplication extends Application implements ProtectedMemberKeeper {

    private static MyApplication mMyApplication;
    private SQLHelper sqlHelper;

    @Override
    public void onCreate(){
        super.onCreate();
        MobSDK.init(this, this.getAppkey(), this.getAppSecret());
        initVariable();
        initData();
        initImageLoader(getApplicationContext());
        mMyApplication = this;
        ActivityManager activityManager =(ActivityManager)getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getLargeMemoryClass();

    }
    protected String getAppkey() {
        return null;
    }

    protected String getAppSecret() {
        return null;
    }

    /**
     * 初始化工程量
     */
    private void initVariable() {
        /*关于夜间模式*/
        SharedPreferences nightModelSp = getSharedPreferences("nightModel", 0);
        SystemSetVariable.osNightModel = nightModelSp.getBoolean("nightModel", false);

        SharedPreferences lookCountSp = getSharedPreferences("lookCount", 0);
        PersionCenterVariable.osLookCount = lookCountSp.getInt("lookCount", 0) + 1;
        SharedPreferences.Editor lookCountEditor = lookCountSp.edit();
        lookCountEditor.putInt("lookCount", PersionCenterVariable.osLookCount);
        lookCountEditor.commit();

        SharedPreferences upLookTimeSp = getSharedPreferences("upLookTime", 0);
        PersionCenterVariable.osUpLookTime = upLookTimeSp.getString("upLookTime", "首次使用");
        SharedPreferences.Editor upLookTimeEditor = upLookTimeSp.edit();
        upLookTimeEditor.putString("upLookTime", TimeUtil.getSystemTime());
        upLookTimeEditor.commit();

        FancySelectVariable.osFancySelected = getSharedPreferences("like",Context.MODE_APPEND).getString("like", "重要报告");//选择兴趣

    }

    /**
     * 初始化数据
     */
    private void initData() {

    }

    /** 获取Application */
    public static MyApplication getApp() {
        return mMyApplication;
    }

    /** 获取数据库Helper */
    public SQLHelper getSQLHelper() {
        if (sqlHelper == null)
            sqlHelper = new SQLHelper(mMyApplication);
        return sqlHelper;
    }
    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        if (sqlHelper != null)
            sqlHelper.close();
        super.onTerminate();
        //整体摧毁的时候调用这个方法
    }
    /** 初始化ImageLoader */
    public static void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "topnews/Cache");//获取到缓存的目录地址
        Log.d("cacheDir", cacheDir.getPath());
        //创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                //.memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                //.discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null) // Can slow ImageLoader, use it carefully (Better don't use it)设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                //.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation你可以通过自己的内存缓存实现
                //.memoryCacheSize(2 * 1024 * 1024)  
                ///.discCacheSize(50 * 1024 * 1024)  
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                //.discCacheFileNameGenerator(new HashCodeFileNameGenerator())//将保存的时候的URI名称用HASHCODE加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                //.discCacheFileCount(100) //缓存的File数量
                .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                //.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                //.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);//全局初始化此配置
    }

}
