package cn.sensorsdata.demo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.igexin.sdk.PushManager;
import com.qihoo360.replugin.RePlugin;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackAppViewScreenUrl;
import com.sensorsdata.analytics.android.sdk.exceptions.InvalidDataException;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import cn.sensorsdata.demo.util.FingerprintHelper;

/**
 * 代码埋点Activity
 */
@Route(path = "/code/activity")
@SensorsDataAutoTrackAppViewScreenUrl(url="xxx.aaa页面")
public class CodeActivity extends BaseActivity implements View.OnClickListener {

    TextView tv_txt = null;
    private TextView textView_code_bg = null;
    private ImageView imageView_code_bg = null;
    private LinearLayout linearLayout_code_bg = null;
    private boolean isVisible = false;
    //记录事件时长
    private long begin = 0;
    private long end = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        if(Build.VERSION.SDK_INT>11)setActionBar();
        initView();
        //由于此页面演示代码埋点，所以忽略此页面的全埋点采集
        SensorsDataAPI.sharedInstance(this).ignoreAutoTrackActivity(CodeActivity.class);

        cryptInit(this);
        String str=null;
       // Log.i("CodeActivity",str.toString());

//        try {
//            // 注册成功/登录成功 后 保存 jgId 到用户表
//            String registrationId=JPushInterface.getRegistrationID(this);
//            if(!TextUtils.isEmpty(registrationId)){
//                JSONObject properties = new JSONObject();
//                properties.put("jgId", registrationId);
//                SensorsDataAPI.sharedInstance().profileSet(properties);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        //Runtime.getRuntime().exec()


        //打开内部插件apk
        //RePlugin.startActivity(this, RePlugin.createIntent("com.yang.testapp2", "com.yang.testapp2.MainActivity"));


    }


    /**
     * 加密init
     */
    private FingerprintHelper helper;
    private void cryptInit(final Context context) {
        helper = new FingerprintHelper(context);
        helper.generateKey();
        helper.setCallback(new FingerprintHelper.SimpleAuthenticationCallback(){

            @Override
            public void onAuthenticationSucceeded(String value) {

                Toast.makeText(context,""+value,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFail() {
                Toast.makeText(context,"Fail",Toast.LENGTH_SHORT).show();
            }
        });
        //检查是否存在硬件
        helper.checkFingerprintAvailable(context);
    }

    /**
     * 解密
     */
    private void decrypt() {
        helper.setPurpose(KeyProperties.PURPOSE_DECRYPT);
        helper.authenticate();

    }

    /**
     * 加密
     */
    private void encrypt() {
        helper.setPurpose(KeyProperties.PURPOSE_ENCRYPT);
        helper.authenticate();
    }


    /**
     * 初始化View
     */
    private void initView() {
        tv_txt = (TextView) findViewById(R.id.textView_code_txt);
        Button bt_login = (Button) findViewById(R.id.button_code_login);
        Button bt_track = (Button) findViewById(R.id.button_code_track);
        Button bt_profile = (Button) findViewById(R.id.button_code_profile);
        linearLayout_code_bg = (LinearLayout) findViewById(R.id.linearLayout_code_bg);
        imageView_code_bg = (ImageView) findViewById(R.id.imageView_code_bg);
        textView_code_bg = (TextView) findViewById(R.id.textView_code_bg);
        Button button_trackTime_begin = (Button) findViewById(R.id.button_codeActivity_trackTime_begin);
        button_trackTime_begin.setText("计时开始\n(trackTimer)");
        Button button_trackTime_end = (Button) findViewById(R.id.button_codeActivity_trackTime_end);
        button_trackTime_begin.setOnClickListener(this);
        button_trackTime_end.setOnClickListener(this);
        textView_code_bg.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        bt_track.setOnClickListener(this);
        bt_profile.setOnClickListener(this);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_code_login:
                login();//关联用户login
                break;
            case R.id.button_code_track:
                track();//事件埋点track

                //打开外部插件apk
               // RePlugin.startActivity(this, RePlugin.createIntent("com.kz.test", "com.kz.test.MainActivity"));

                encrypt();//加密
                break;
            case R.id.button_code_profile:
                profile();//设置用户属性profile
                decrypt();//解密
                break;
            case R.id.textView_code_bg:
                if (!isVisible) {
                    linearLayout_code_bg.setVisibility(View.GONE);
                    imageView_code_bg.setVisibility(View.VISIBLE);
                    textView_code_bg.setText("关闭示例");
                    isVisible = true;
                } else {
                    linearLayout_code_bg.setVisibility(View.VISIBLE);
                    imageView_code_bg.setVisibility(View.GONE);
                    textView_code_bg.setText("点击可查看代码埋点事件属性，以ViewProduct事件示例");
                    isVisible = false;

                }
                break;
            case R.id.button_codeActivity_trackTime_begin:
                trackTimer_begin();//计时开始
                break;
            case R.id.button_codeActivity_trackTime_end:
                trackTiemer_end();//计时结束
                break;
            default:
                break;
        }

    }


    /**
     * 事件埋点track
     */
    private void track() {
        try {
            JSONObject properties = new JSONObject();
            properties.put("ProductID", 123456);                    // 设置商品ID
            properties.put("ProductCatalog", "Laptop Computer");    // 设置商品类别
            properties.put("IsAddedToFav", false);                  // 是否被添加到收藏夹
            SensorsDataAPI.sharedInstance(this).track("ViewProduct", properties);
            SensorsDataAPI.sharedInstance().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_txt.setGravity(Gravity.LEFT);
        //把事件显示到TextView上
        if (SensorsDataAPI.sharedInstance(this).getLoginId() == null) {
            tv_txt.setText(String.format("track事件\n事件名称：ViewProduct\n事件属性：{\n  ProductID：123456\n  ProductCatalog：Laptop Computer\n  IsAddedToFav：false\n  }\ndistinct_id：%s\nloginId：", SensorsDataAPI.sharedInstance().getAnonymousId())
            );
        } else {
            tv_txt.setText(String.format("track事件\n事件名称：ViewProduct\n事件属性：{\n  ProductID：123456\n  ProductCatalog：Laptop Computer\n  IsAddedToFav：false\n  }\ndistinct_id：%s\nloginId：%s", SensorsDataAPI.sharedInstance().getAnonymousId(), SensorsDataAPI.sharedInstance().getLoginId())
            );
        }
    }

    /**
     * 关联用户login
     */
    private void login() {
        String loginId = (int) (Math.random() * (100000 - 11111) + 11111) + "";//这里随机生成11111~99999作为登录Id
        try {
            SensorsDataAPI.sharedInstance(this).login(loginId);
            //SensorsDataAPI.sharedInstance(this).trackSignUp(loginId);

            // 注册成功 / 登录成功 后 保存 jgId 到用户表
            JSONObject properties = new JSONObject();
            if(!TextUtils.isEmpty(JPushInterface.getRegistrationID(this))){
                // 将用户 Profile "jgAndroidId" 设为 registrationId
                properties.put("jgAndroidId", JPushInterface.getRegistrationID(this)+"");
            }
            if(!TextUtils.isEmpty(MiPushClient.getRegId(getApplicationContext()))){
                properties.put("xmAndroidId",MiPushClient.getRegId(this)+"");
            }
            if(!TextUtils.isEmpty(PushManager.getInstance().getClientid(this))){
                properties.put("gtAndroidId", PushManager.getInstance().getClientid(this));
            }

            // 设置用户 Profile
            SensorsDataAPI.sharedInstance().profileSet(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {//初始化我们SDK后 直接调用这段代码
//            JSONObject properties=new JSONObject();
//            properties.put("source","应用宝");//这里的source负责记录不同的渠道包的渠道
//            //渠道追踪，这里取名为 APPInstall
//            SensorsDataAPI.sharedInstance(this).trackInstallation("AppInstall",properties);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        tv_txt.setGravity(Gravity.LEFT);
        //把事件显示到TextView上
        tv_txt.setText("login事件\n" +
                "distinct_id：" + SensorsDataAPI.sharedInstance().getAnonymousId() + "\n" +
                "loginId：" + SensorsDataAPI.sharedInstance().getLoginId()
        );
    }

    /**
     * 设置用户属性profile
     */
    private void profile() {
        try {
            JSONObject properties = new JSONObject();
            // 设定用户性别属性 "Sex" 为 "Male"
            properties.put("Sex", "男");
            // 设定用户年龄属性 "Age" 为 18
            properties.put("Age", 26);
            // 设定用户属性
            SensorsDataAPI.sharedInstance(this).profileSet(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_txt.setGravity(Gravity.LEFT);
        //把事件显示到TextView上
        if (SensorsDataAPI.sharedInstance(this).getLoginId() == null) {
            tv_txt.setText("profile事件\n" +
                    "事件名称：ViewProduct\n" +
                    "事件属性：{\n" +
                    "  Sex：男\n" +
                    "  Age：26\n" +
                    "  }\n" +
                    "distinct_id：" + SensorsDataAPI.sharedInstance().getAnonymousId() + "\n" +
                    "loginId："
            );
        } else {
            tv_txt.setText("profile事件\n" +
                    "事件名称：ViewProduct\n" +
                    "事件属性：{\n" +
                    "  Sex：男\n" +
                    "  Age：26\n" +
                    "  }\n" +
                    "distinct_id：" + SensorsDataAPI.sharedInstance().getAnonymousId() + "\n" +
                    "loginId：" + SensorsDataAPI.sharedInstance().getLoginId()
            );
        }
    }

    /**
     * 计时开始（用于追踪事件的时长）
     */
    private void trackTimer_begin() {
        tv_txt.setText("计时开始\n" +
                "事件名称：RecordTime");
        begin = System.currentTimeMillis();
        Toast.makeText(this, "  计时开始  " +
                        "事件名为：RecordTime "
                , Toast.LENGTH_LONG).show();
        try {
            SensorsDataAPI.sharedInstance().trackTimer("RecordTime");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计时结束（用于追踪事件的时长）
     */
    private void trackTiemer_end() {
        end = System.currentTimeMillis();
        if (begin == 0) {
            Toast.makeText(this, "请先点击计时开始按钮", Toast.LENGTH_SHORT).show();
            return;
        }
        tv_txt.setText("计时结束\n" +
                "事件名称：RecordTime\n" +
                "事件属性：{ event_duration : " + (end - begin) + " }");
        Toast.makeText(this, "计时结束，已尝试向Sensors Analytics发送track事件  " +
                        "事件名为：RecordTime \n" +
                        "事件属性：{ event_duration : " + (end - begin) +
                        " }"
                , Toast.LENGTH_LONG).show();
        try {
            SensorsDataAPI.sharedInstance().track("RecordTime");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置ActionBar
     */
    @TargetApi(18)
    private void setActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("代码埋点");
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.left_back);
        int titleId = Resources.getSystem().getIdentifier("action_bar_title",
                "id", "android");
        TextView tvTitle = (TextView) findViewById(titleId);
        int width = getResources().getDisplayMetrics().widthPixels;
        tvTitle.setWidth(width);
        tvTitle.setGravity(Gravity.CENTER);
    }

    /**
     * back键
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
