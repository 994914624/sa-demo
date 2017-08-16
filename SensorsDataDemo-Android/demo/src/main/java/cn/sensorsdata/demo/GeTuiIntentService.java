package cn.sensorsdata.demo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.exceptions.InvalidDataException;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 个推 IntentService 用于接收消息
 */

public class GeTuiIntentService extends GTIntentService {


    @Override
    public void onReceiveServicePid(Context context, int i) {
        Log.i("onReceiveServicePid",""+i);
    }

    /**
     * 接收 cid
     */
    @Override
    public void onReceiveClientId(Context context, String s) {
        Log.i("onReceiveClientId",s);
        JSONObject properties2 = new JSONObject();
        // 将用户 Profile "gtAndroidId" 设为 ClientId
        try {
            properties2.put("gtAndroidId", ""+PushManager.getInstance().getClientid(getApplicationContext()));
            // 设置用户 Profile
            SensorsDataAPI.sharedInstance(getApplicationContext()).profileSet(properties2);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    /**
     * 处理透传消息。
     * 注意：个推 的消息 点击之后，会默认重新打开App
     */
    @Override
    public void onReceiveMessageData(Context context, final GTTransmitMessage gtTransmitMessage) {
        Log.i("onReceiveMessageData",""+gtTransmitMessage.toString());

        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject properties = new JSONObject();
                    // 获取消息标题，并保存在事件属性 msg_title 中
                    //properties.put("msg_title_gt", gtTransmitMessage.);
                    // 获取消息 ID，并保存在事件属性 msg_id 中
                    properties.put("msg_id_gt", gtTransmitMessage.getMessageId()+"");
                    // 追踪 "App 消息推送成功" 事件
                    SensorsDataAPI.sharedInstance(getApplicationContext()).track("AppReceivedNotification_gt", properties);
                    Toast.makeText(getApplicationContext(), "用户点击打开了消息/或者收到了透传消息\n" +
                            "已尝试向Sensors Analytics发送track事件\n" +
                            "事件名为:AppReceivedNotification_gt" +
                            "事件属性:{ "+
                            ",msg_id_xm : " + gtTransmitMessage.getMessageId() +
                            " }", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * cid 离线上线通知
     */
    @Override
    public void onReceiveOnlineState(Context context, boolean b) {
        Log.i("onReceiveOnlineState",""+b);
    }

    /**
     * 各种事件处理回执
     */
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {
        Log.i("onReceiveCommandResult",""+gtCmdMessage.toString());
    }
}
