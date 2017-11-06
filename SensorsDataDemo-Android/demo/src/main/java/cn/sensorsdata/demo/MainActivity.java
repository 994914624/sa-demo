package cn.sensorsdata.demo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.exceptions.InvalidDataException;
import com.umeng.analytics.MobclickAgent;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.sensorsdata.demo.fragmentation.FragmentationActivity;


/**
 * 主页面 MainActivity。
 */
@Route(path = "/main/activity")
public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //requestPermission();
        permission();
        if (Build.VERSION.SDK_INT > 11) setActionBar();
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
        TextView demo = (TextView) findViewById(R.id.textView_yang);
        code.setOnClickListener(this);
        vt.setOnClickListener(this);
        autotrack.setOnClickListener(this);
        jPush.setOnClickListener(this);
        xmPush.setOnClickListener(this);
        gtPush.setOnClickListener(this);

        //demo 测试模块
        if (true) {
            demo.setOnClickListener(this);
            //上下文菜单
            registerForContextMenu(demo);
        }
    }

    /**
     * 设置ActionBar
     */
    @TargetApi(11)
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
                ARouter.getInstance().build("/code/activity").navigation();
//                intent.setClass(this, CodeActivity.class);
//                startActivity(intent);
                break;
            case R.id.textView_vt:
                ARouter.getInstance().build("/vt/activity").navigation();
//                intent.setClass(this, VTActivity.class);
//                startActivity(intent);
                break;
            case R.id.textView_autotrack:
                ARouter.getInstance().build("/autotrack/activity").navigation();
//                intent.setClass(this, AutoTrackActivity.class);
//                startActivity(intent);
                break;
            case R.id.textView_jg:
                ARouter.getInstance().build("/jiguangpush/activity").navigation();
//                intent.setClass(this, JiguangPushActivity.class);
//                startActivity(intent);
                break;
            case R.id.textView_xm:
                ARouter.getInstance().build("/xiaomipush/activity").navigation();
//                intent.setClass(this, XiaomiPushActivity.class);
//                startActivity(intent);
                break;
            case R.id.textView_gt:
                ARouter.getInstance().build("/getui/activity").navigation();
//                intent.setClass(this, GeTuiActivity.class);
//                startActivity(intent);
                break;
            case R.id.textView_yang:


                intent.setClass(this, FragmentationActivity.class);


                startActivity(intent);
                break;

            default:
                break;
        }


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


    /*
     *context menu----------------------
     *
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("选择Activity页面");
        // add context menu item
        menu.add(0, 1, Menu.NONE, "DemoActivity");
        menu.add(0, 2, Menu.NONE, "YangActivity");
        menu.add(0, 3, Menu.NONE, "YangappActivity");
        menu.add(0, 4, Menu.NONE, "YangTabActivity");
        menu.add(0, 5, Menu.NONE, "YangTab2Activity");
        menu.add(0, 6, Menu.NONE, "YangfragActivity");
        menu.add(0, 7, Menu.NONE, "YangRecycleViewActivity");


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                ARouter.getInstance().build("/demo/activity").navigation();
                break;
            case 2:
                ARouter.getInstance().build("/yang/activity").navigation();
                break;
            case 3:
                ARouter.getInstance().build("/yangapp/activity").navigation();
                break;
            case 4:
                ARouter.getInstance().build("/yangtab/activity").navigation();
                break;
            case 5:
                ARouter.getInstance().build("/yangtab2/activity").navigation();
                break;
            case 6:
                ARouter.getInstance().build("/yangfrag/activity").navigation();
                break;
            case 7:
                ARouter.getInstance().build("/yangrecycleview/activity").navigation();
                break;


        }
        return super.onContextItemSelected(item);
    }

    private void requestPermission() {
        AndPermission.with(this)
                .requestCode(100)
                .permission(Permission.PHONE)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int i, @NonNull List<String> list) {
                        Log.e(TAG, "permission-onSucceed");
                    }

                    @Override
                    public void onFailed(int i, @NonNull List<String> list) {
                        Log.e(TAG, "permission-onFailed");
                    }
                })
                .start();
    }

    private void permission() {
        Log.e(TAG,"123");

        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
//        if(ActivityCompat.checkSelfPermission(this,"android.permission.READ_SMS")!=PackageManager.PERMISSION_GRANTED) {
//            Log.e(TAG,"permission1");
//            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_PHONE_STATE", "android.permission.READ_SMS"}, 111);
//
//        }
        if (ActivityCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "permission2");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_PHONE_STATE"}, 112);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e(TAG, requestCode + "#" + permissions[0] + "#" + grantResults[0]);
        if (requestCode == 112) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG,"success");
                try {
                    //初始化我们SDK后 调用这段代码，用于记录安装事件、渠道追踪。
                    JSONObject properties = new JSONObject();
                    properties.put("DownloadChannel", "应用宝");//这里的 DownloadChannel 负责记录下载商店的渠道。
                    //这里安装事件取名为 AppInstall。
                    //注意 由于要追踪不同渠道链接中投放的渠道，所以 Manifest 中不能按照神策的方式定制渠道，代码中也不能传入 $utm_ 开头的渠道字段！！！
                    SensorsDataAPI.sharedInstance(this).trackInstallation("AppInstall", properties);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
