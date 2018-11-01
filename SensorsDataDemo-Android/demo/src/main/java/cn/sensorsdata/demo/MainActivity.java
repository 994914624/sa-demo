package cn.sensorsdata.demo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.qihoo360.replugin.RePlugin;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.exceptions.InvalidDataException;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import com.umeng.analytics.MobclickAgent;

import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import cn.sensorsdata.demo.constant.SeverURLConfig;
import cn.sensorsdata.demo.fragmentation.FragmentationActivity;
import cn.sensorsdata.demo.test.YangRecycleViewActivity;
import cn.sensorsdata.demo.util.SensorsDataUtil;
import cn.sensorsdata.demo.yang.BaseViewActivity;
import cn.sensorsdata.demo.yang.KotlinActivity;
import cn.sensorsdata.demo.yang.MyDialogFragment;
import cn.sensorsdata.demo.yang.ServerManager;
import cn.sensorsdata.demo.yang.ViewPagerActivity;
import cn.sensorsdata.demo.yang.YangSingleActivity;
import cn.sensorsdata.demo.yang.YangTab2Activity;
import cn.sensorsdata.demo.yang.YangTabActivity;
import cn.sensorsdata.demo.yang.YangWebViewActivity;
import cn.sensorsdata.demo.yang.YangappfragActivity;
import cn.sensorsdata.demo.yang.YangfragActivity;
import es.dmoral.toasty.Toasty;


/**
 * 主页面 MainActivity。
 */
@Route(path = "/main/activity")
public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private int num = 0;
    private LinearLayout linearBack = null;

    private ServerManager mServerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("首页");

        Log.d("SA.Sensors","MainActivity  onCreate");

 //Toasty.success(this, "welcome").show();

        //友盟
        //startActivity(new Intent(this, KotlinActivity.class));

        //requestPermission();
        permission();
        if (Build.VERSION.SDK_INT > 11) setActionBar();
        initView();

        //RePlugin External apk
        //RePlugin.install("/sdcard/testExternalRePlugin.apk");

        //dialog1();
//        MyDialogFragment fragment= new MyDialogFragment ();
//
//        fragment.show(getFragmentManager(), "MyDialogment");



        // AndServer run in the service.
        mServerManager = new ServerManager(this);
        mServerManager.register();

        //申请 READ_PHONE_STATE 权限
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") != PackageManager.PERMISSION_GRANTED) {
                //SensorsDataAPI.sharedInstance().ignoreAutoTrackActivity(MainActivity.class);
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_PHONE_STATE"}, (int) (Math.random()*1000)+10);
            }
        } else {
            // 6.0 以下 直接调用 trackInstallation 触发激活事件,做渠道追踪。
            trackInstallation();
        }

    }

    /**
     * 初始化view
     */
    private void initView() {
        linearBack = (LinearLayout) findViewById(R.id.activity_main);
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

    TextView title_project = null;

    /**
     * 设置ActionBar
     */
    @TargetApi(11)
    private void setActionBar() {
//        ActionBar actionBar = getActionBar();
//        actionBar.setTitle("神策Demo");
//        actionBar.setDisplayShowHomeEnabled(false);
//        int titleId = Resources.getSystem().getIdentifier("action_bar_title",
//                "id", "android");
//        TextView tvTitle = (TextView) findViewById(titleId);
//        int width = getResources().getDisplayMetrics().widthPixels;
//        tvTitle.setWidth(width);
//        tvTitle.setGravity(Gravity.CENTER);

        //使用自定义ActionBar
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar_layout, null);
        ActionBar actionBar = getActionBar();
        if(actionBar!=null){
            actionBar.setCustomView(mActionBarView, lp);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        //设置选中的项目
        title_project = (TextView) mActionBarView.findViewById(R.id.title_project);
        title_project.setOnClickListener(this);


    }

    /**
     * 点击跳转到指定的 Activity
     */
    @Override
    @TargetApi(11)
    public void onClick(View view) {
        Log.e(TAG, view.getX() + "," + view.getY());
        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.textView_code:
                //ARouter.getInstance().build("/code/activity").navigation();
                intent.setClass(this, CodeActivity.class);
                break;
            case R.id.textView_vt:
                //ARouter.getInstance().build("/vt/activity").navigation();
                intent.setClass(this, VTActivity.class);
                break;
            case R.id.textView_autotrack:
               // ARouter.getInstance().build("/autotrack/activity").navigation();
                intent.setClass(this, AutoTrackActivity.class);
                break;
            case R.id.textView_jg:
                //ARouter.getInstance().build("/jiguangpush/activity").navigation();
                intent.setClass(this, JiguangPushActivity.class);
                break;
            case R.id.textView_xm:
                //ARouter.getInstance().build("/xiaomipush/activity").navigation();
                intent.setClass(this, XiaomiPushActivity.class);
                break;
            case R.id.textView_gt:
                //ARouter.getInstance().build("/getui/activity").navigation();
                intent.setClass(this, GeTuiActivity.class);
                break;
//            case R.id.textView_yang:
//
//
//               // intent.setClass(this, FragmentationActivity.class);
//                //intent.setClass(this, DemoActivity.class);
//                //intent.setClass(this,BaseViewActivity.class);
//
//
//                break;
            case R.id.title_project:

                //打开popupwindow
                //popupwindow(view);
                break;

            default:
                break;
        }
        try {
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    /**
     *
     */
    private void popupwindow(View view) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentview = inflater.inflate(R.layout.popup_window, null);
        TextView project1 = (TextView) contentview.findViewById(R.id.popup_project_1);
        PopupWindow popupWindow = new PopupWindow(contentview, getResources().getDisplayMetrics().widthPixels * 4 / 5,
                getResources().getDisplayMetrics().heightPixels * 4 / 5);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        //linearBack.setBackgroundColor(Color.GRAY);
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
        menu.add(0, 2, Menu.NONE, "BaseViewActivity");
        menu.add(0, 3, Menu.NONE, "YangappActivity");
        menu.add(0, 4, Menu.NONE, "YangTabActivity");
        menu.add(0, 5, Menu.NONE, "YangTab2Activity");
        menu.add(0, 6, Menu.NONE, "YangfragActivity");
        menu.add(0, 7, Menu.NONE, "YangRecycleViewActivity");
        menu.add(0, 8, Menu.NONE, "YangWebViewActivity");
        menu.add(0, 9, Menu.NONE, "YangSingleActivity");
        menu.add(0, 10, Menu.NONE, "KotlinActivity");
        menu.add(0, 11, Menu.NONE, "ViewPagerActivity");

    }

    /**
     * @param menu
     * @return 选项菜单(用于切换不同的项目数据接收地址)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * @param item
     * @return 选项菜单item的点击事件(用于切换不同的项目数据接收地址)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String debug_model = "";
        int debug_ID = 0;
        switch (item.getItemId()) {
            // action
            case R.id.action_1:
                debug_model = "DEBUG_ONLY";
                debug_ID = 1;
                break;
            case R.id.action_2:
                debug_model = "DEBUG_AND_TRACK";
                debug_ID = 2;
                break;
            case R.id.action_3:
                debug_model = "DEBUG_OFF";
                debug_ID = 3;
                break;


            default:
                break;

        }

        Toast.makeText(this, "已切到" + debug_model + "请杀掉App重新打开", Toast.LENGTH_SHORT).show();
        //保存项目ID设置到本地
        saveTimeToSharedPreference(debug_ID);
        return super.onOptionsItemSelected(item);
    }

    /**
     * 保存项目设置到本地
     */
    private void saveTimeToSharedPreference(int project) {
        SharedPreferences sharedPreferences = getSharedPreferences("cn.sensorsdata.demo.project",
                Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("project", project);
        editor.commit();
    }

    /**
     * 从本地读取设置项目ID的值
     */
    private int getProjectIDFromSharedPreference() {

        SharedPreferences sharedPreferences = getSharedPreferences("cn.sensorsdata.demo.project",
                Activity.MODE_PRIVATE);
        int project = sharedPreferences.getInt("project", 0);
        return project;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent intent =new Intent();
        switch (item.getItemId()) {
            case 1:
               // ARouter.getInstance().build("/demo/activity").navigation();
                intent.setClass(this,DemoActivity.class);
                break;
            case 2:
                //ARouter.getInstance().build("/baseview/activity").navigation();
                intent.setClass(this,BaseViewActivity.class);
                break;
            case 3:
                //ARouter.getInstance().build("/yangapp/activity").navigation();
                intent.setClass(this,YangappfragActivity.class);
                break;
            case 4:
                //ARouter.getInstance().build("/yangtab/activity").navigation();
                intent.setClass(this,YangTabActivity.class);
                break;
            case 5:
               // ARouter.getInstance().build("/yangtab2/activity").navigation();
                intent.setClass(this,YangTab2Activity.class);
                break;
            case 6:
                //ARouter.getInstance().build("/yangfrag/activity").navigation();
                intent.setClass(this,YangfragActivity.class);
                break;
            case 7:
                //ARouter.getInstance().build("/yangrecycleview/activity").navigation();
                intent.setClass(this,YangRecycleViewActivity.class);
                break;
            case 8:
               // ARouter.getInstance().build("/yangwebview/activity").navigation();
                intent.setClass(this,YangWebViewActivity.class);
                break;
            case 9:
                //ARouter.getInstance().build("/yangsingle/activity").navigation();
                intent.setClass(this,YangSingleActivity.class);
                break;
            case 10:
                //ARouter.getInstance().build("/kotlin/activity").navigation();
                intent.setClass(this,KotlinActivity.class);
                break;
            case 11:
               // ARouter.getInstance().build("/vie/activity").navigation();
                intent.setClass(this,ViewPagerActivity.class);
                break;


        }
        startActivity(intent);
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
        Log.e(TAG, "123");

        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
//        if(ActivityCompat.checkSelfPermission(this,"android.permission.READ_SMS")!=PackageManager.PERMISSION_GRANTED) {
//            Log.e(TAG,"permission1");
//            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_PHONE_STATE", "android.permission.READ_SMS"}, 111);
//
//        }
//        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
//            if (ActivityCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_PHONE_STATE"}, 100);
//            }
//        }


        if (ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "permission3");
            //SensorsDataAPI.sharedInstance().ignoreAutoTrackActivity(MainActivity.class);
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 113);

        }

        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "permission4");
            //SensorsDataAPI.sharedInstance().ignoreAutoTrackActivity(MainActivity.class);
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION","android.permission.ACCESS_COARSE_LOCATION","android.permission.ACCESS_LOCATION_EXTRA_COMMANDS"}, 114);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // TODO
//            }

            // 申请权限结果回调时，调用 trackInstallation 触发激活事件做渠道追踪。
            trackInstallation();
        }
    }

    /**
     * 记录激活事件、并做渠道追踪。
     */
    private void trackInstallation() {
        try {
            JSONObject properties = new JSONObject();
            properties.put("DownloadChannel", "应用宝");//这里的 DownloadChannel 负责记录下载商店的渠道。这里传入具体应用商店包的标记。
            // 这里激活事件取名为 AppInstall。
            // 注意 由于要追踪不同渠道链接中投放的渠道，所以 Manifest 中不能按照神策的方式定制渠道，代码中也不能传入 $utm_ 开头的渠道字段！！！
            SensorsDataAPI.sharedInstance().trackInstallation("AppInstall", properties);
//            if(本地存一个标记) {
//                // 根据本地存储的标记，只调用一次 profileSetOnce 设置 $first_visit_time
//                SensorsDataAPI.sharedInstance().profileSetOnce(new JSONObject().put("$first_visit_time", new Date()));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SA.Sensors","MainActivity  onResume");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //SensorsDataUtil.profilePushId("jgId",JPushInterface.getRegistrationID(this),this);



      //  MobclickAgent.onResume(this);
//
//        try {
//            JSONObject properties = new JSONObject();
//            properties.put("$project", "xzw_default");
//            properties.put("$token", "defaultsuperA422046D7F2C41D5B55890713E840160");
//            SensorsDataAPI.sharedInstance().track("Y_test_1", properties);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            JSONObject properties = new JSONObject();
//            properties.put("$project", "xzw_default");
//            SensorsDataAPI.sharedInstance().track("Y_test_2", properties);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
       // SensorsDataAPI.sharedInstance().resumeAutoTrackActivity(MainActivity.class);
        Log.d("SA.Sensors","MainActivity  onPause");
       // MobclickAgent.onPause(this);

//        try {
//            JSONObject properties = new JSONObject();
//            properties.put("$project", "xzw_product");
//            properties.put("$token", "productsuperA422046D7F2C41D5B55890713E840160");
//            SensorsDataAPI.sharedInstance().track("Y_test_3", properties);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            JSONObject properties = new JSONObject();
//            properties.put("$project", "xzw_product");
//            SensorsDataAPI.sharedInstance().track("Y_test_4", properties);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /*
     *Dialog1
     */
//    private void dialog1() {
//        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);  //先得到构造器
//        //builder.create().setOwnerActivity(this);
//        builder.setTitle("提示"); //设置标题
//        builder.setMessage("是否确认退出?"); //设置内容
//        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss(); //关闭dialog
//                Toast.makeText(MainActivity.this, "确认" + which, Toast.LENGTH_SHORT).show();
//            }
//        });
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                Toast.makeText(MainActivity.this, "取消" + which, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        builder.setNeutralButton("忽略", new DialogInterface.OnClickListener() {//设置忽略按钮
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                Toast.makeText(MainActivity.this, "忽略" + which, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        Dialog dia=builder.create();
//        dia.show();
//        SensorsDataAPI.sharedInstance().trackAppDialog(MainActivity.this, dia);
//
//    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("SA.Sensors","MainActivity  onStart");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("SA.Sensors","MainActivity  onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mServerManager.unRegister();

    }
}
