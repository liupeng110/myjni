package com.andlib.lp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.andlib.lp.util.L;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * 用于处理退出程序时可以退出所有的activity，而编写的通用类
 * 每个Activity在oncreate的时候都需调用MyApplication.getInstance().addActivity(this);
 * 以便将当前Activity加入到Activity集合中 调用MyApplication.getInstance().exit();//退出
 *
 * @author
 */
public class AndApp extends Application {

    private static List<Activity> activityList = new LinkedList<Activity>();
    public static DbManager.DaoConfig daoConfig;// xutils数据库配置
    public static DbManager db;
    public boolean isCash = true;//是否开启崩溃日志捕捉. true开启 . false关闭

    @Override
    public void onCreate() {
        super.onCreate();
        L.i("进入应用切入点....");
        x.Ext.init(this);     // 初始化xutils3
        x.Ext.setDebug(true); // 输出debug日志

        if (isCash) {// 进行捕捉崩溃日志
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(getApplicationContext());
        }
        initDatabase();// 进行配置数据库
    }

    /**
     * 添加Activity到容器中,统一退出
     */
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /***
     * 退出应用,遍历所有Activity并finish,结束自身
     */
    public static void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /****
     * 配置数据库
     */
    public void initDatabase() {
        daoConfig = new DbManager.DaoConfig()
                .setDbName("andlp.db")
                // 不设置dbDir时, 默认存储在app的私有目录.
//                 .setDbDir(new File("/sdcard"))
                // .setDbDir(newFile(Environment.getExternalStorageDirectory().getPath()))
                .setDbVersion(1)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                }).setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion,
                                          int newVersion) {
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // db.dropDb();
                    }
                });

        initImageLoader(this);

    }

    /*******
     * 初始化imageloader,
     */
    @SuppressWarnings({"deprecation", "static-access"})
    public static ImageLoader initImageLoader(Context context) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // .showImageOnLoading(R.drawable.photo3)
                // .showImageForEmptyUri(R.drawable.photo3)
                // .showImageOnFail(R.drawable.photo3).cacheInMemory()
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .displayer(new FadeInBitmapDisplayer(300)).cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        File cacheDir = StorageUtils.getOwnCacheDirectory(context,
                "andlp/Cache");// 缓存地址
        ImageLoaderConfiguration config = (new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 1)
                .threadPoolSize(4).denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(1 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .discCache(new UnlimitedDiskCache(cacheDir))
                .tasksProcessingOrder(QueueProcessingType.LIFO))
                .defaultDisplayImageOptions(options).build();
        imageLoader.getInstance().init(config);
        db = x.getDb(daoConfig);
        return imageLoader;
    }

}