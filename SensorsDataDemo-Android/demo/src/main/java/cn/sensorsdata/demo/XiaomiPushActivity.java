package cn.sensorsdata.demo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sensorsdata.analytics.android.sdk.ScreenAutoTracker;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;


/**
 * 小米推送 Activity
 */
@Route(path = "/xiaomipush/activity")
public class XiaomiPushActivity extends BaseActivity implements ScreenAutoTracker{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaomi);
        if(Build.VERSION.SDK_INT>11)setActionBar();
        initView();

        try {
            JSONObject properties = new JSONObject();
            properties.put("xmAndroidId", MiPushClient.getRegId(this)+"");
            // 设置用户 Profile
            SensorsDataAPI.sharedInstance().profileSet(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化View
     */
    private void initView() {
        TextView textView = (TextView) findViewById(R.id.textView_XiaomiPushActivity);
        //小米推送的RegistrationID
        if(TextUtils.isEmpty(MiPushClient.getRegId(getApplicationContext()))){
            textView.setText("小米推送初始化失败");

        }else {
            textView.setText(MiPushClient.getRegId(getApplicationContext()) + "");
            Log.i("getRegId_xiaomi:",MiPushClient.getRegId(getApplicationContext()) + "");


        }
    }

    /**
     * 设置ActionBar
     */
    @TargetApi(18)
    private void setActionBar(){
        ActionBar actionBar=getActionBar();
        actionBar.setTitle("小米推送");
        // 设置不显示左侧图标
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.left_back);
        int titleId = Resources.getSystem().getIdentifier("action_bar_title",
                "id", "android");
        TextView tvTitle = (TextView) findViewById(titleId);
        int width=getResources().getDisplayMetrics().widthPixels;
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

    @Override
    public String getScreenUrl() {
        return "xiaomi/activity";
    }

    @Override
    public JSONObject getTrackProperties() throws JSONException {
        return null;
    }


    /**
     * 根据 XiaomiPushBroadcastReceiver 广播的接收值，弹出不同的提示
     */
    public static class DemoHandler extends Handler {

        private Context context;

        public DemoHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = (Bundle) msg.obj;
            String msg_title_xm = bundle.getString("msg_title_xm");
            String msg_id_xm = bundle.getString("msg_id_xm");

            switch (msg.what) {
                case 1:

                    Toast.makeText(context, "用户收到了推送消息\n" +
                            "已尝试向Sensors Analytics发送track事件\n" +
                            "事件名为:AppReceivedNotification_xm" +
                            "事件属性:{ msg_title_xm : " + msg_title_xm +
                            ",msg_id_xm : " + msg_id_xm +
                            " }", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(context, "用户点击打开了消息\n" +
                            "已尝试向Sensors Analytics发送track事件\n" +
                            "事件名为:AppOpenNotification_xm" +
                            "事件属性:{ msg_title_xm : " + msg_title_xm +
                            ",msg_id_xm : " + msg_id_xm +
                            " }", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(context, "用户收到了自定义消息\n" +
                            "已尝试向Sensors Analytics发送track事件\n" +
                            "事件名为: AppReceivedMessage_xm"
                            , Toast.LENGTH_LONG).show();
                    break;
                case 4:
//
//                    Toast.makeText(context, "小米推送注册成功\n" +
//                            "已尝试向Sensors Analytics发送profile事件\n" +
//                            "事件属性:{ xmAndroidId : " + MiPushClient.getRegId(context) +
//                            " }", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;


            }

        }
    }
}
