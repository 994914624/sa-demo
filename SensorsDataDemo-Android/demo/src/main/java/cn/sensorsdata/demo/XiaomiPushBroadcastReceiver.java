package cn.sensorsdata.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import org.json.JSONObject;

/**
 * Created by yang on 2017/1/19.
 * 用于接收小米推送的 BroadcastReceiver
 */

public class XiaomiPushBroadcastReceiver extends PushMessageReceiver {


    private static XiaomiPushActivity.DemoHandler handler = null;

    public static void setHandle(XiaomiPushActivity.DemoHandler handle) {
        handler = handle;
    }

   private static final String TAG = "__xiao mi__";
    private static final String TXT = "----------------------- 小米推送 ---------------------------\n";
    /**
     * 用来接收服务器发来的通知栏消息
     * （消息到达客户端时触发，并且可以接收应用在前台时不弹出通知的通知消息）
     */
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {

        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putString("msg_title_xm", message.getTitle());
        bundle.putString("msg_id_xm", message.getMessageId());
        msg.obj = bundle;
        msg.what = 1;//用 1 标识用户收到消息
        handler.sendMessage(msg);

        Log.i(TAG,"onNotificationMessageArrived:"+TXT);
        try {
            Toast.makeText(context," 小米 消息到达",Toast.LENGTH_SHORT).show();
            JSONObject properties = new JSONObject();
            // 获取消息标题，并保存在事件属性 msg_title 中
            properties.put("msg_title_xm", message.getTitle());
            // 获取消息 ID，并保存在事件属性 msg_id 中
            properties.put("msg_id_xm", message.getMessageId());
            // 追踪 "App 消息推送成功" 事件
            SensorsDataAPI.sharedInstance(context).track("AppReceivedNotification_xm", properties);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用来接收服务器发来的通知栏消息（用户点击通知栏时触发）
     */
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {

        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putString("msg_title_xm", message.getTitle());
        bundle.putString("msg_id_xm", message.getMessageId());
        msg.obj = bundle;
        msg.what = 2;//用 2 标识用户打开消息
        handler.sendMessage(msg);
        Log.i(TAG,"onNotificationMessageClicked:"+TXT);
        try {

            JSONObject properties = new JSONObject();
            // 获取消息标题，并保存在事件属性 msg_title 中
            properties.put("msg_title_xm", message.getTitle());
            // 获取消息 ID，并保存在事件属性 msg_id 中
            properties.put("msg_id_xm", message.getMessageId());
            // 追踪 "App 推送消息打开" 事件
            SensorsDataAPI.sharedInstance(context).track("AppOpenNotification_xm", properties);

        } catch (Exception e) {
            e.printStackTrace();
        }
//
//        Intent intent=new Intent(context,XiaomiPushActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);

    }

    /**
     * 用来接收服务器发送的透传消息
     */
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {

        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putString("msg_content_xm", message.getContent());
        msg.obj = bundle;
        msg.what = 3;//用 3 标识用户收到自定义(透传)消息
        handler.sendMessage(msg);
        Log.i(TAG,"onReceivePassThroughMessage:"+TXT);
        try {
            // 追踪 "App 收到自定义(透传)消息" 事件
            SensorsDataAPI.sharedInstance(context).track("AppReceivedMessage_xm");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 用来接受客户端向服务器发送注册命令消息后返回的响应。
     */
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        Log.i(TAG,"onReceiveRegisterResult:"+TXT);
        String command = message.getCommand();
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {


            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            msg.obj = bundle;
            msg.what = 4;//用 4 标识注册成功
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                //小米推送初注册成功
                try {
                    JSONObject properties = new JSONObject();
                    // 将用户 Profile "xmAndroidId" 设为 registrationId
                    properties.put("xmAndroidId", MiPushClient.getRegId(context)+"");
                    // 设置用户 Profile
                    SensorsDataAPI.sharedInstance(context).profileSet(properties);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            handler.sendMessage(msg);
        }
    }


    /**
     * 用来接收客户端向服务器发送命令消息后返回的响应
     */
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        Log.i(TAG,"onCommandResult:"+TXT);
    }


}
