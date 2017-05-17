package cn.sensorsdata.demo;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.exceptions.InvalidDataException;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 主页面 MainActivity
 */

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBar();
        initView();
//        try {
//            JSONObject properties = new JSONObject();
//            properties.put("$screen_name", "cn.sensorsdata.demo.HomeFragment")
//                      .put("$title", "首页");
//            SensorsDataAPI.sharedInstance(this).trackViewScreen(null, properties);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        //initSensorsDataAPI();










//        MobclickAgent.onEvent(this, "AppStart");
//        MobclickAgent.onProfileSignIn("123456");

    }

    /**
     * 初始化view
     */
    private void initView() {
        TextView code = (TextView) findViewById(R.id.textView_code);
        TextView vt = (TextView) findViewById(R.id.textView_vt);
        TextView autotrack = (TextView) findViewById(R.id.textView_autotrack);
        TextView jPush = (TextView) findViewById(R.id.textView_jg);
        TextView xmPush = (TextView) findViewById(R.id.textView_xm);
        TextView gtPush = (TextView) findViewById(R.id.textView_gt);
        code.setOnClickListener(this);
        vt.setOnClickListener(this);
        autotrack.setOnClickListener(this);
        jPush.setOnClickListener(this);
        xmPush.setOnClickListener(this);
        gtPush.setOnClickListener(this);
    }

    /**
     * 设置ActionBar
     */
    private void setActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("神策Demo");
        actionBar.setDisplayShowHomeEnabled(false);
        int titleId = Resources.getSystem().getIdentifier("action_bar_title",
                "id", "android");
        TextView tvTitle = (TextView) findViewById(titleId);
        int width = getResources().getDisplayMetrics().widthPixels;
        tvTitle.setWidth(width);
        tvTitle.setGravity(Gravity.CENTER);
    }

    /**
     * 点击跳转到指定的 Activity
     */
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.textView_code:
                intent.setClass(this, CodeActivity.class);
                break;
            case R.id.textView_vt:
                intent.setClass(this, VTActivity.class);
                break;
            case R.id.textView_autotrack:
                intent.setClass(this, AutoTrackActivity.class);
                break;
            case R.id.textView_jg:
                intent.setClass(this, JiguangPushActivity.class);
                break;
            case R.id.textView_xm:
                intent.setClass(this, XiaomiPushActivity.class);

                break;
            case R.id.textView_gt:
                intent.setClass(this, GeTuiActivity.class);

                break;

            default:
                break;
        }
        startActivity(intent);
    }

    /**
     * 友盟 页面时长
     */
//    public void onResume() {
//        super.onResume();
//        MobclickAgent.onResume(this);
//    }
//    public void onPause() {
//        super.onPause();
//        MobclickAgent.onPause(this);
//    }


//    /**
//     * Sensors Analytics 采集数据的地址
//     */
//    //private final static String SA_SERVER_URL = "http://vtrack.cloud.sensorsdata.cn:8006/sa?project=default&token=448973e7b3aa67a9";
//    private final static String SA_SERVER_URL = "http://test-zouyuhan.cloud.sensorsdata.cn:8006/sa?project=yangzhankun&token=db52d13749514676";
//
//    /**
//     * Sensors Analytics 配置分发的地址
//     */
//    //private final static String SA_CONFIGURE_URL = "http://vtrack.cloud.sensorsdata.cn:8006/config/?project=default";
//    private final static String SA_CONFIGURE_URL = "http://test-zouyuhan.cloud.sensorsdata.cn:8006/config/?project=yangzhankun";
//    //private final static String SA_VT_URL = "ws://test-zouyuhan.cloud.sensorsdata.cn/api/ws?project=yangzhankun";
//
//    /**
//     * Sensors Analytics DEBUG 模式
//     * SensorsDataAPI.DebugMode.DEBUG_OFF - 关闭 Debug 模式
//     * SensorsDataAPI.DebugMode.DEBUG_ONLY - 打开 Debug 模式，校验数据，但不进行数据导入
//     * SensorsDataAPI.DebugMode.DEBUG_AND_TRACK - 打开 Debug 模式，校验数据，并将数据导入到 Sensors Analytics 中
//     * 注意！请不要在正式发布的 App 中使用 Debug 模式！
//     */
//    private final SensorsDataAPI.DebugMode SA_DEBUG_MODE = SensorsDataAPI.DebugMode.DEBUG_AND_TRACK;
//
//    /**
//     * 初始化 Sensors Analytics SDK
//     */
//    private void initSensorsDataAPI() {
//        SensorsDataAPI.sharedInstance(
//                this,                               // 传入 Context
//                SA_SERVER_URL,                      // 数据接收的 URL
//                SA_CONFIGURE_URL,                   // 配置分发的 URL
//                SA_DEBUG_MODE);                     // Debug 模式选项
//        SensorsDataAPI.sharedInstance(this).enableAutoTrack(); //打开自动采集
//        Log.i("SA.2","初始化 Sensors Analytics SDK");
//        try {
//            //渠道追踪，这里取名为 APPInstall （AndroidManifest 文件中已配置了utm 信息）
//            SensorsDataAPI.sharedInstance(this).trackInstallation("AppInstall",new JSONObject());
//        } catch (InvalidDataException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//

}
