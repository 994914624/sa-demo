package cn.sensorsdata.demo;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.growingio.android.sdk.collection.Configuration;
import com.growingio.android.sdk.collection.GrowingIO;
import com.igexin.sdk.PushManager;
import com.qihoo360.replugin.RePlugin;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.exceptions.InvalidDataException;
//import com.squareup.leakcanary.LeakCanary;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;

import com.umeng.commonsdk.UMConfigure;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;


import org.apache.httpcore.HttpRequest;
import org.apache.httpcore.HttpResponse;
import org.apache.httpcore.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import cn.jpush.android.api.JPushInterface;
import cn.sensorsdata.demo.bean.WeChatMessage;
import cn.sensorsdata.demo.constant.SeverURLConfig;
import cn.sensorsdata.demo.fragment.BaseFragment;
import cn.sensorsdata.demo.test.SensorsData;
import cn.sensorsdata.demo.util.AppInfoUtil;
import cn.sensorsdata.demo.util.EmulatorUtils;
import cn.sensorsdata.demo.util.LogcatUtil;
import cn.sensorsdata.demo.util.NetUtils;
import cn.sensorsdata.demo.yang.MyIntentService;
import io.fabric.sdk.android.Fabric;

import com.sensorsdata.analytics.android.sdk.*;
import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.RequestHandler;
import com.yanzhenjie.andserver.Server;
import com.yanzhenjie.andserver.filter.HttpCacheFilter;
import com.yanzhenjie.andserver.website.AssetsWebsite;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;

/**
 * Created by yang on 2016/12/28.
 * MultiDexApplication
 */

public class App extends MultiDexApplication {



    /**
     * Sensors Analytics 采集数据的地址
     */
    //private   String SA_SERVER_URL = "http://192.168.8.252:8888/sa?";
    private   String SA_Q = "https://sensorsfocoustest.datasink.sensorsdata.cn/sa?project=default&token=6c6afc3d52a85ad3";

    private final static String SA_SERVER_URL = "http://sdk-test.cloud.sensorsdata.cn:8006/sa?project=yangzhankun&token=95c73ae661f85aa0";
    //分析师环境

    /**
     * Sensors Analytics 配置分发的地址
     */
    private final static String SA_CONFIGURE_URL = "http://sdk-test.cloud.sensorsdata.cn:8006/config/?project=default";

    /**
     * Sensors Analytics DEBUG 模式
     * SensorsDataAPI.DebugMode.DEBUG_OFF - 关闭 Debug 模式
     * SensorsDataAPI.DebugMode.DEBUG_ONLY - 打开 Debug 模式，校验数据，但不进行数据导入
     * SensorsDataAPI.DebugMode.DEBUG_AND_TRACK - 打开 Debug 模式，校验数据，并将数据导入到 Sensors Analytics 中
     * 注意！请不要在正式发布的 App 中使用 Debug 模式！
     */
    private  SensorsDataAPI.DebugMode SA_DEBUG_MODE = SensorsDataAPI.DebugMode.DEBUG_AND_TRACK;

    public static final boolean cZ = Log.isLoggable("ChineseAllReader", Log.VERBOSE);
    @Override
    public void onCreate() {
        super.onCreate();
        if (sInstance == null) {
            sInstance = this;
        }

        LogcatUtil.getInstance(this).init();

        //RePlugin
        RePlugin.App.onCreate();


        Log.e(TAG,"onCreate");
        // 初始化神策sdk
        initSensorsDataAPI();
        Log.e(TAG,"onCreate SDK OK");

        if (shouldInit()) {


            intARouter();
            initCrashlytics();

            Stetho.initializeWithDefaults(this);

            initUMeng();
            //initSensorsDataAPI();
            initJGPush();
            initXMPush();

            initGTPush();
            initLitepal(this);
            //initLeakCanary();
            initGio();
            //initTalkingData();


            //sensors();
            initAMap();

            //侧滑
            BGASwipeBackHelper.init(this,  null);
        }


    }


    /**
     * 初始化 Sensors Analytics SDK
     */
    private void initSensorsDataAPI() {

        //转换 Debug 模式
        checkDebugType();

        //初始化SDK
        SensorsDataAPI.sharedInstance(
                this,                        // 传入 Context
                SA_SERVER_URL,                      // 数据接收的 URL
                SA_DEBUG_MODE);                     // Debug 模式选项

       // SensorsDataAPI.sharedInstance().identify("RTTTTss58FFss+====");
        //设置公共属性
        try {
            SensorsDataAPI.sharedInstance().registerSuperProperties(new JSONObject().put("emulator", EmulatorUtils.isEmulator(this)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //开启自动采集
        List<SensorsDataAPI.AutoTrackEventType> eventTypeList = new ArrayList<>();
        eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_START);
        eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_END);
        eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_CLICK);
        eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_VIEW_SCREEN);
        SensorsDataAPI.sharedInstance().enableAutoTrack(eventTypeList);
        //开启调试日志
        SensorsDataAPI.sharedInstance().enableLog(true);

        // 关闭点击分析的提示框
        //SensorsDataAPI.sharedInstance().enableAppHeatMapConfirmDialog(false);
        //初始化 SDK 之后，开启自动采集 Fragment 页面浏览事件
        SensorsDataAPI.sharedInstance().trackFragmentAppViewScreen();
        //开启 React Native 页面控件的自动采集（$AppClick）
        SensorsDataAPI.sharedInstance().enableReactNativeAutoTrack();

        //收集崩溃日志
        SensorsDataAPI.sharedInstance().trackAppCrash();

        //屏幕方向
        //SensorsDataAPI.sharedInstance().enableTrackScreenOrientation(true);

        //设置 GPS
        //SensorsDataAPI.sharedInstance().setGPSLocation(1,1);
        JSONObject presetProperties=SensorsDataAPI.sharedInstance().getPresetProperties();






        String str=null;
        //Log.i(TAG,SensorsDataAPI.sharedInstance().getPresetProperties().toString());





//        try {
//            JSONObject properties =new JSONObject();
//            properties.put("word_1","qwer");
//            properties.put("word_2","qwer");
//            properties.put("word_3","qwer");
//            properties.put("word_4","qwer");
//            properties.put("word_5","qwer");
//            properties.put("word_6","qwer");
//            properties.put("word_7","qwer");
//            Log.i(TAG+"#####:",properties.toString());
//            SensorsDataAPI.sharedInstance().registerSuperProperties(properties);
//            Log.i(TAG+"#####:",SensorsDataAPI.sharedInstance().getSuperProperties().toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }



//        Intent intent=new Intent(this,MyIntentService.class);
//        startService(intent);


        //SensorsDataAPI.sharedInstance().enableEditingVTrack();
        //开启热力图
        SensorsDataAPI.sharedInstance(this).enableHeatMap();
        //SensorsDataAPI.sharedInstance(this).addHeatMapActivity(MainActivity.class);




        // 设置动态公共属性
        SensorsDataAPI.sharedInstance().registerDynamicSuperProperties(new SensorsDataDynamicSuperProperties() {
            @Override
            public JSONObject getDynamicSuperProperties() {
                try {
                    // 比如 isLogin() 是用于获取用户当前的登录状态，通过 registerDynamicSuperProperties 就可以把用户当前的登录状态，添加到触发的埋点事件中。
                    boolean bool = isLogin();
                    return new JSONObject().put("isLogin",bool);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        SensorsDataAPI.sharedInstance().deleteAll();


    }

    private boolean isLogin() {
        return false;
    }


    private static App sInstance;

    public static App get() {
        return sInstance;
    }




    /**
     * 定位
     */
    //声明AMapLocationClient类对象
    public  AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if(aMapLocation!=null){
//                aMapLocation.getLatitude();//获取纬度
//                aMapLocation.getLongitude();//获取经度
               // Log.i(TAG,"AMapLocationListener"+aMapLocation.getAddress()+":"+aMapLocation.getLocationType());
               // Log.i(TAG,"AMapLocationListener"+aMapLocation.getLatitude()+":"+aMapLocation.getLongitude());
                //SensorsDataAPI.sharedInstance().setGPSLocation(aMapLocation.getLatitude(),aMapLocation.getLongitude());


                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    String city=aMapLocation.getCity();//城市信息
                    if(!TextUtils.isEmpty(city)){
                       // Log.e(TAG,"gaode"+city);

                    }
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
//                    Log.e("AMapError","location Error, ErrCode:"
//                            + aMapLocation.getErrorCode() + ", errInfo:"
//                            + aMapLocation.getErrorInfo());
//                    Log.e(TAG,"AMapError:"+sHA1(getApplicationContext()));
                }

            }

        }
    };
    //声明AMapLocationClientOption对象
    public  AMapLocationClientOption mLocationOption = null;
    private void initAMap() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        //mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        mLocationClient.disableBackgroundLocation(true);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        RePlugin.App.attachBaseContext(this);
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

    //private void initLeakCanary() {
    //    LeakCanary.install(this);
    //}


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




    private Boolean isLogin;



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
    public static final String TAG = "yy";

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
        //MobclickAgent.setDebugMode(true);
        UMConfigure.setLogEnabled(true);

        UMConfigure.init(this,"58ad5104734be40b8d0017ab","UUUU" ,UMConfigure.DEVICE_TYPE_PHONE, "0f361187a425dcd6c167c2cb5be01321");
        //统计事件

        HashMap<String,String> map = new HashMap<String,String>();
        map.put("type","book");
        map.put("quantity","3");
        MobclickAgent.onEvent(this, "purchase", map);

        //错误收集

        MobclickAgent.setCatchUncaughtExceptions(true);
        //UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "1fe6a20054bcef865eeb0991ee84525b");
    }

    /**
     * crashlytics 初始化，用于crash收集
     */
    private void initCrashlytics() {
       // Fabric.with(this, new Crashlytics());
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
        PushManager.getInstance().initialize(this.getApplicationContext(), GeTuiService.class);
        //用于接收CID、透传消息以及其他推送服务事件。
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), GeTuiIntentService.class);

    }

    /**
     * 记录页面开始
     */
    private  void trackPageStart(){
        SensorsDataAPI.sharedInstance().trackTimerBegin("AppPage");//这里定义事件名为AppPage
    }

    /**
     * 记录页面关闭
     */
    private  void trackPageEnd(String pageName){
        try {
            JSONObject properties=new JSONObject();
            if(!TextUtils.isEmpty(pageName)){
                properties.put("pageName",pageName);
            }
            SensorsDataAPI.sharedInstance().trackTimerEnd("AppPage",properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * 从本地读取设置项目ID的值
     *
     */
    private int getProjectIDFromSharedPreference(){

        SharedPreferences sharedPreferences= getSharedPreferences("cn.sensorsdata.demo.project",
                Activity.MODE_PRIVATE);
        int project =sharedPreferences.getInt("project", 0);
        return project;
    }

    /**
     * 根据本地保存的DEBUG_ID的值，来切换DEBUG模式
     */
    private void checkDebugType() {
        int projectID=getProjectIDFromSharedPreference();
        switch (projectID){

            case 1:
                SA_DEBUG_MODE= SensorsDataAPI.DebugMode.DEBUG_ONLY;
                break;
            case 0:
            case 2:
                SA_DEBUG_MODE= SensorsDataAPI.DebugMode.DEBUG_AND_TRACK;
                break;
            case 3:
                SA_DEBUG_MODE= SensorsDataAPI.DebugMode.DEBUG_OFF;
                break;
        }

    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();

        /* Not need to be called if your application's minSdkVersion > = 14 */
        RePlugin.App.onLowMemory();

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        /* Not need to be called if your application's minSdkVersion > = 14 */
        RePlugin.App.onTrimMemory(level);

    }


    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        /* Not need to be called if your application's minSdkVersion > = 14 */
        RePlugin.App.onConfigurationChanged(newConfig);

    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


}



//

