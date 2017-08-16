package cn.sensorsdata.demo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.crashlytics.android.Crashlytics;
import com.growingio.android.sdk.collection.Configuration;
import com.growingio.android.sdk.collection.GrowingIO;
import com.igexin.sdk.PushManager;
import com.sensorsdata.analytics.android.sdk.BuildConfig;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.exceptions.InvalidDataException;
import com.squareup.leakcanary.LeakCanary;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import cn.sensorsdata.demo.bean.WeChatMessage;
import io.fabric.sdk.android.Fabric;

import com.sensorsdata.analytics.android.sdk.*;

/**
 * Created by yang on 2016/12/28.
 * MultiDexApplication
 */

public class MyApplication extends Application {


    /**
     * Sensors Analytics 采集数据的地址
     */
    //private final static String SA_SERVER_URL = "http://vtrack.cloud.sensorsdata.cn:8006/sa?project=default&token=448973e7b3aa67a9";
    private final static String SA_SERVER_URL = "http://test-zouyuhan.cloud.sensorsdata.cn:8006/sa?project=yangzhankun&token=db52d13749514676";
    //private final static String SA_SERVER_URL = "https://sa.carapp.gtmc.com.cn:1443/sa?project=default";

    /**
     * Sensors Analytics 配置分发的地址
     */
    //private final static String SA_CONFIGURE_URL = "http://vtrack.cloud.sensorsdata.cn:8006/config/?project=default";
    private final static String SA_CONFIGURE_URL = "http://test-zouyuhan.cloud.sensorsdata.cn:8006/config/?project=yangzhankun";
    //private final static String SA_CONFIGURE_URL = "";
    //private final static String SA_VT_URL = "ws://test-zouyuhan.cloud.sensorsdata.cn/api/ws?project=yangzhankun";

    /**
     * Sensors Analytics DEBUG 模式
     * SensorsDataAPI.DebugMode.DEBUG_OFF - 关闭 Debug 模式
     * SensorsDataAPI.DebugMode.DEBUG_ONLY - 打开 Debug 模式，校验数据，但不进行数据导入
     * SensorsDataAPI.DebugMode.DEBUG_AND_TRACK - 打开 Debug 模式，校验数据，并将数据导入到 Sensors Analytics 中
     * 注意！请不要在正式发布的 App 中使用 Debug 模式！
     */
    private final SensorsDataAPI.DebugMode SA_DEBUG_MODE = SensorsDataAPI.DebugMode.DEBUG_AND_TRACK;

    @Override
    public void onCreate() {
        super.onCreate();
        if (shouldInit()) {


            intARouter();
            initCrashlytics();
            initSensorsDataAPI();
            initJGPush();
            initXMPush();
            //initUMeng();
            initGTPush();
            initLitepal(this);
            initLeakCanary();
            //initGio();
            initTalkingData();
        }


    }

    private void initTalkingData() {
        TCAgent.setReportUncaughtExceptions(false);
        TCAgent.LOG_ON=true;
        // App ID: 在TalkingData创建应用后，进入数据报表页中，在“系统设置”-“编辑应用”页面里查看App ID。
        // 渠道 ID: 是渠道标识符，可通过不同渠道单独追踪数据。
        TCAgent.init(this, "A1093941946740739F0EDEDA6A1B3E44", "XX渠道");
        //设置反作弊
        TCAgent.setAntiCheatingEnabled(this,true);
        TCAgent.setGlobalKV("platform", "Android");
        TCAgent.onEvent(this,"yang","app_start");
        Map kv = new HashMap();
        kv.put("商品类型", "休闲食品");
        kv.put("价格","5～10元" );
        TCAgent.onEvent(this, "点击首页推荐位", "第3推广位", kv);

    }

    private void initGio() {
        GrowingIO.startWithConfiguration(this, new Configuration()
                .useID()
                .trackAllFragments()
                .setChannel("XXX应用商店"));
    }

    private void initLeakCanary() {
        LeakCanary.install(this);
    }


    private void intARouter() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    private void initLitepal(Context context) {
        LitePal.initialize(context);
        //new WeChatMessage("李白","今天是端午节").save();
    }


    /**
     * 初始化 Sensors Analytics SDK
     */
    private void initSensorsDataAPI() {

        SensorsDataAPI.sharedInstance(
                this,                               // 传入 Context
                SA_SERVER_URL,                      // 数据接收的 URL
                "",                                 // 配置分发的 URL
                SA_DEBUG_MODE);                     // Debug 模式选项

        //初始化SDK后，如果能拿到用户ID，立即调用 login 传入用户ID

        //初始化SDK后，将应用名称设置为公共属性
        try {
            JSONObject properties=new JSONObject();
            properties.put("app_name",getAppName(this));
            SensorsDataAPI.sharedInstance().registerSuperProperties(properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        List<SensorsDataAPI.AutoTrackEventType> eventTypeList = new ArrayList<>();
        eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_START);
        eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_END);
        eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_CLICK);
        eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_VIEW_SCREEN);
        SensorsDataAPI.sharedInstance(this).enableAutoTrack(eventTypeList);
        //初始化 SDK 之后，开启自动采集 Fragment 页面浏览事件
        SensorsDataAPI.sharedInstance().trackFragmentAppViewScreen();
        SensorsDataAPI.sharedInstance().enableReactNativeAutoTrack();
        SensorsDataAPI.sharedInstance().enableLog(true);

        //替换匿名ID
        //SensorsDataAPI.sharedInstance().identify("d:"+SensorsDataAPI.sharedInstance().getAnonymousId());
        //导入神策 Android 模块

        /**
         * 对于 Android 的推广渠道 会出现在同一个渠道上, 跟踪不同投放方案的效果的情况,
         * 比如微博, 可能会同时做 A/B 两个投放方案进行测试, 看不同方案转化效果。
         *
         * 针对此情况，我们可以单独新增一个字段(比如：DownloadChannel)来记录下载商店的渠道。
         * 用神策渠道链接 来记录不同推广方案( A/B )的渠道。
         */
        try {
            //初始化我们SDK后 调用这段代码，用于记录安装事件、渠道追踪。
            JSONObject properties = new JSONObject();
            properties.put("DownloadChannel", "微博");//这里的 DownloadChannel 负责记录下载商店的渠道。
            //这里安装事件取名为 AppInstall。
            //注意 由于要追踪不同渠道链接中投放的渠道，所以 Manifest 中不能按照神策的方式定制渠道，代码中也不能传入 $utm_ 开头的渠道字段！！！
            SensorsDataAPI.sharedInstance(this).trackInstallation("AppInstall", properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            JSONArray jsonArray=new JSONArray();
//            jsonArray.put(11).put(12);
//            JSONObject properties=new JSONObject();
//            properties.put("XY",jsonArray);
//            Log.e("MyApplication",properties.toString()+"");
//            SensorsDataAPI.sharedInstance().track("test1",properties);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        try {
            JSONArray jsonArray=new JSONArray();
            jsonArray.put("8").put("9");
            JSONObject properties=new JSONObject();
            properties.put("XY",jsonArray);
            Log.e("MyApplication",properties.toString()+"");
            SensorsDataAPI.sharedInstance().track("test2",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context){
        try {
            PackageManager packageManager=context.getPackageManager();
            PackageInfo packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }




    /**
     * 极光推送 SDK 初始化
     */
    private void initJGPush() {

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }


    /**
     * 小米推送 required
     */
    // user your appid the key.
    private static final String APP_ID = "2882303761517543539";
    // user your appid the key.
    private static final String APP_KEY = "5741754322539";
    // 此TAG在adb logcat中检索自己所需要的信息， 只需在命令行终端输入 adb logcat | grep cn.sensorsdata.demo
    public static final String TAG = "cn.sensorsdata.demo";

    public XiaomiPushActivity.DemoHandler handler = null;

    /**
     * 小米推送初始化
     */
    private void initXMPush() {
        MiPushClient.registerPush(this, APP_ID, APP_KEY);//xiaomi初始化 APP_ID、APP_KEY
        xiaomiLog();//xiaomiLog日志
        //handler用于转发接受到的消息
        handler = new XiaomiPushActivity.DemoHandler(this);
        XiaomiPushBroadcastReceiver.setHandle(handler);

    }

    /**
     * 小米 log
     */
    private void xiaomiLog() {

        LoggerInterface newLogger = new LoggerInterface() {
            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);
    }

    /**
     * 友盟初始化
     */
    private void initUMeng() {
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.setDebugMode(true);

    }

    /**
     * crashlytics 初始化，用于crash收集
     */
    private void initCrashlytics() {
        Fabric.with(this, new Crashlytics());
    }

    /**
     * 判断是否为当前包的主进程（保证只在 cn.sensorsdata.demo 进程中初始化）
     */
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                Log.i("*************", info.processName);
                return true;
            }

        }
        return false;
    }

    /**
     * 初始化 个推
     */
    private void initGTPush() {
        //GeTuiService 为第三方自定义推送服务
        PushManager.getInstance().initialize(this, GeTuiService.class);
        //用于接收CID、透传消息以及其他推送服务事件。
        PushManager.getInstance().registerPushIntentService(this, GeTuiIntentService.class);

    }

}

//

