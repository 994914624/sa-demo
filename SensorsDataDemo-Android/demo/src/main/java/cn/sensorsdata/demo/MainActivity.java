package cn.sensorsdata.demo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

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

import org.json.JSONException;
import org.json.JSONObject;

import cn.sensorsdata.demo.fragmentation.FragmentationActivity;


/**
 * 主页面 MainActivity。
 */
@Route(path = "/main/activity")
public class MainActivity extends Activity implements View.OnClickListener {

    private int num=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(Build.VERSION.SDK_INT>11)setActionBar();
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
        demo.setOnClickListener(this);
//        demo.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                startActivity(new Intent().setClass(MainActivity.this, YangTab2Activity.class));
//                //startActivity(new Intent().setClass(MainActivity.this, YangappfragActivity.class));
//                return false;
//            }
//        });
        //上下文菜单
        registerForContextMenu(demo);
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


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
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


        }
        return super.onContextItemSelected(item);
    }


}
