package cn.sensorsdata.demo;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.exceptions.InvalidDataException;
import org.json.JSONException;
import org.json.JSONObject;
import cn.jpush.android.api.JPushInterface;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by yang on 2016/12/28.
 * 用于接收极光推送消息的 BroadcastReceiver
 */

public class JiguangPushBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("jiguang","getAction:---"+intent.getAction());
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {










            // 极光推送注册成功，获得 RegistrationId 并保存在 Sensors Analytics用户的 Profile 中
            appRegistrationID(context, intent);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            // 收到了自定义消息，自定义消息不会展示在通知栏，只透传给 App ，使用 Sensors Analytics 追踪 "App 收到了自定义消息" 事件
            appReceivedMessage(context);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            // 收到了推送消息，使用 Sensors Analytics 追踪 "App 消息推送成功" 事件
            appReceivedNotification(context, intent);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            // 用户点击打开消息，使用 Sensors Analytics 记录 "App 打开消息" 事件
            appOpenNotification(context,intent);
        }
//        else if(JPushInterface.ACTION_STATUS){
//
//        }




    }


    /**
     * 极光推送注册成功，
     * 获得 RegistrationId，
     * 向Sensors Analytics发送profile事件
     */
    private void appRegistrationID(Context context, Intent intent) {

        Log.e("jg","appRegistrationID");
        try {
            // 获取极光推送的 RegistrationId
            final String registrationId = intent.getExtras().getString(JPushInterface.EXTRA_REGISTRATION_ID);
            // 将 Registration Id 存储在 Sensors Analytics 的用户 Profile 中
            JSONObject properties = new JSONObject();
            // 将用户 Profile "jgAndroidId" 设为 registrationId
            properties.put("jgAndroidId", registrationId);
            // 设置用户 Profile
            SensorsDataAPI.sharedInstance(context).profileSet(properties);
            Toast.makeText(context, "极光推送注册成功\n" +
                    "已尝试向Sensors Analytics发送profile事件\n" +
                    "事件属性:{ jgAndroidId : " + registrationId + " }", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户收到了推送消息，
     * 向Sensors Analytics发送track事件，
     * 事件名为：AppReceivedNotification
     */
    private void appReceivedNotification(Context context, Intent intent) {
        try {
            Bundle message = intent.getExtras();
            JSONObject properties = new JSONObject();
            // 获取消息标题，并保存在事件属性 msg_title 中
            properties.put("msg_title_jg", message.getString(JPushInterface.EXTRA_ALERT));
            // 获取消息 ID，并保存在事件属性 msg_id 中
            properties.put("msg_id_jg", message.getString(JPushInterface.EXTRA_MSG_ID));
            // 追踪 "App 消息推送成功" 事件
            SensorsDataAPI.sharedInstance(context).track("AppReceivedNotification_jg", properties);
            Toast.makeText(context, "用户收到了推送消息\n" +
                    "已尝试向Sensors Analytics发送track事件\n" +
                    "事件名为:AppReceivedNotification_jg" +
                    "事件属性:{ msg_title_jg : " + message.getString(JPushInterface.EXTRA_ALERT) +
                    ",msg_id_jg : " + message.getString(JPushInterface.EXTRA_MSG_ID) +
                    " }", Toast.LENGTH_LONG).show();

            Log.e("###",message.getString(JPushInterface.EXTRA_ALERT)+"||"+message.getString(JPushInterface.EXTRA_MSG_ID)+"");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    /**
     * 用户点击打开了消息，
     * 向Sensors Analytics发送track事件，
     * 事件名为：AppOpenNotification
     */
    private void appOpenNotification(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        try {
//            Intent intent1=new Intent(MyApplication.mcontext,JiguangPushActivity.class);
//            intent1.setFlags(FLAG_ACTIVITY_NEW_TASK );
//            MyApplication.mcontext.startActivity(intent1);

            JSONObject properties = new JSONObject();
            // 获取消息标题，并保存在事件属性 msg_title 中
            properties.put("msg_title_jg", bundle.getString(JPushInterface.EXTRA_ALERT));
            // 获取消息 ID，并保存在事件属性 msg_id 中
            properties.put("msg_id_jg", bundle.getString(JPushInterface.EXTRA_MSG_ID));
            // 追踪 "App 推送消息打开" 事件
            SensorsDataAPI.sharedInstance(context).track("AppOpenNotification_jg",properties);
            Toast.makeText(context, "用户点击打开了消息\n" +
                            "已尝试向Sensors Analytics发送track事件\n" +
                            "事件名为:AppOpenNotification_jg\n"+
                            "事件属性:{ msg_title_jg : " + bundle.getString(JPushInterface.EXTRA_ALERT) +
                            ",msg_id_jg : " + bundle.getString(JPushInterface.EXTRA_MSG_ID) +
                            " }", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Intent intent2=new Intent(context,JiguangPushActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent2);
    }

    /**
     * 用户收到了自定义消息，
     * 向Sensors Analytics发送track事件，
     * 事件名为：AppReceivedMessage
     */
    private void appReceivedMessage(Context context) {
        try {
            // 追踪 "App 收到自定义(透传)消息" 事件
            SensorsDataAPI.sharedInstance(context).track("AppReceivedMessage_jg");
            Toast.makeText(context, "用户收到了自定义消息\n" +
                            "已尝试向Sensors Analytics发送track事件\n" +
                            "事件名为: AppReceivedMessage_jg"
                    , Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



