package cn.sensorsdata.demo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.igexin.sdk.PushManager;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.exceptions.InvalidDataException;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 代码埋点Activity
 */

public class CodeActivity extends Activity implements View.OnClickListener {

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
        setActionBar();
        initView();
        //由于此页面演示代码埋点，所以忽略此页面的全埋点采集
        SensorsDataAPI.sharedInstance(this).filterAutoTrackActivity(this);

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
                break;
            case R.id.button_code_profile:
                profile();//设置用户属性profile
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
        } catch (InvalidDataException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tv_txt.setGravity(Gravity.LEFT);
        //把事件显示到TextView上
        if (SensorsDataAPI.sharedInstance(this).getLoginId() == null) {
            tv_txt.setText("track事件\n" +
                    "事件名称：ViewProduct\n" +
                    "事件属性：{\n" +
                    "  ProductID：123456\n" +
                    "  ProductCatalog：Laptop Computer\n" +
                    "  IsAddedToFav：false\n" +
                    "  }\n" +
                    "distinct_id：" + SensorsDataAPI.sharedInstance(this).getAnonymousId() + "\n" +
                    "loginId："
            );
        } else {
            tv_txt.setText("track事件\n" +
                    "事件名称：ViewProduct\n" +
                    "事件属性：{\n" +
                    "  ProductID：123456\n" +
                    "  ProductCatalog：Laptop Computer\n" +
                    "  IsAddedToFav：false\n" +
                    "  }\n" +
                    "distinct_id：" + SensorsDataAPI.sharedInstance(this).getAnonymousId() + "\n" +
                    "loginId：" + SensorsDataAPI.sharedInstance(this).getLoginId()
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

            //由于切换用户，所以每次登录都要发 jgAndroidId 到profile 表
            if(!TextUtils.isEmpty(JPushInterface.getRegistrationID(this))&&!TextUtils.isEmpty(MiPushClient.getRegId(getApplicationContext()))){
                JSONObject properties = new JSONObject();
                // 将用户 Profile "jgAndroidId" 设为 registrationId
                properties.put("jgAndroidId", JPushInterface.getRegistrationID(this)+"")
                          .put("xmAndroidId",MiPushClient.getRegId(this)+"")
                          .put("gtAndroidId", PushManager.getInstance().getClientid(this));
                // 设置用户 Profile
                SensorsDataAPI.sharedInstance(this).profileSet(properties);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_txt.setGravity(Gravity.LEFT);
        //把事件显示到TextView上
        tv_txt.setText("login事件\n" +
                "distinct_id：" + SensorsDataAPI.sharedInstance(this).getAnonymousId() + "\n" +
                "loginId：" + SensorsDataAPI.sharedInstance(this).getLoginId()
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
        } catch (InvalidDataException e) {
            e.printStackTrace();
        } catch (JSONException e) {
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
                    "distinct_id：" + SensorsDataAPI.sharedInstance(this).getAnonymousId() + "\n" +
                    "loginId："
            );
        } else {
            tv_txt.setText("profile事件\n" +
                    "事件名称：ViewProduct\n" +
                    "事件属性：{\n" +
                    "  Sex：男\n" +
                    "  Age：26\n" +
                    "  }\n" +
                    "distinct_id：" + SensorsDataAPI.sharedInstance(this).getAnonymousId() + "\n" +
                    "loginId：" + SensorsDataAPI.sharedInstance(this).getLoginId()
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
            SensorsDataAPI.sharedInstance(this).trackTimer("RecordTime");
        } catch (InvalidDataException e) {
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
            SensorsDataAPI.sharedInstance(this).track("RecordTime");
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置ActionBar
     */
    private void setActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("代码埋点");
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
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
