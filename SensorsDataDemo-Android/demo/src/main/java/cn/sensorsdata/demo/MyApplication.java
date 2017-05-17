package cn.sensorsdata.demo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.igexin.sdk.PushManager;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.exceptions.InvalidDataException;
import com.umeng.analytics.MobclickAgent;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.json.JSONObject;

import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.fabric.sdk.android.Fabric;

/**
 * Created by yang on 2016/12/28.
 */

public class MyApplication extends Application {

    /**
     * Sensors Analytics 采集数据的地址
     */
    //private final static String SA_SERVER_URL = "http://vtrack.cloud.sensorsdata.cn:8006/sa?project=default&token=448973e7b3aa67a9";
    private final static String SA_SERVER_URL = "http://test-zouyuhan.cloud.sensorsdata.cn:8006/sa?project=yangzhankun&token=db52d13749514676";

    /**
     * Sensors Analytics 配置分发的地址
     */
    //private final static String SA_CONFIGURE_URL = "http://vtrack.cloud.sensorsdata.cn:8006/config/?project=default";
    private final static String SA_CONFIGURE_URL = "http://test-zouyuhan.cloud.sensorsdata.cn:8006/config/?project=yangzhankun";
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
        if(shouldInit()){

            initCrashlytics();
            initSensorsDataAPI();
            initJGPush();
            initXMPush();
            //initUMeng();
            initGTPush();
            if(SensorsDataAPI.sharedInstance(this)!=null){
                Log.i("SA.","SensorsDataAPI.sharedInstance(this) 不为空");
            }
        }



    }




    /**
     * 初始化 Sensors Analytics SDK
     */
    private void initSensorsDataAPI() {

        SensorsDataAPI.sharedInstance(
                this,                               // 传入 Context
                SA_SERVER_URL,                      // 数据接收的 URL
                SA_CONFIGURE_URL,                   // 配置分发的 URL
                SA_DEBUG_MODE);                     // Debug 模式选项


        SensorsDataAPI.sharedInstance(this).enableAutoTrack(); //打开自动采集
        Log.i("SA.","初始化 Sensors Analytics SDK");
        try {
            //渠道追踪，这里取名为 APPInstall （AndroidManifest 文件中已配置了utm 信息）
            SensorsDataAPI.sharedInstance(this).trackInstallation("AppInstall",new JSONObject());
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }


    }

    /**
     * 极光推送 SDK 初始化
     */
    private  void  initJGPush(){

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

    public XiaomiPushActivity.DemoHandler handler=null;

    /**
     * 小米推送初始化
     */
    private void initXMPush(){
            MiPushClient.registerPush(this, APP_ID, APP_KEY);//xiaomi初始化 APP_ID、APP_KEY
            xiaomiLog();//xiaomiLog日志
            //handler用于转发接受到的消息
            handler=new XiaomiPushActivity.DemoHandler(this);
            XiaomiPushBroadcastReceiver.setHandle(handler);

    }

    /**
     * 小米 log
     */
    private void xiaomiLog(){

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
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType. E_UM_NORMAL);
        MobclickAgent.setDebugMode( true );

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
                Log.i("*************",info.processName);
                return true;
            }

        }
        return false;
    }

    /**
     * 初始化 个推
     */
    private void initGTPush(){
        //GeTuiService 为第三方自定义推送服务
        PushManager.getInstance().initialize(this, GeTuiService.class);
        //用于接收CID、透传消息以及其他推送服务事件。
        PushManager.getInstance().registerPushIntentService(this, GeTuiIntentService.class);

    }

}

//

