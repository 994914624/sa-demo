package cn.sensorsdata.demo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.exceptions.InvalidDataException;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 个推 IntentService 用于接收消息
 */

public  class GeTuiIntentService extends GTIntentService {


    private static final String TAG = "__ Ge Tui __";
    private static final String TXT = "----------------------- 个推 ---------------------------\n";

    @Override
    public void onReceiveServicePid(Context context, int i) {
        Log.i(TAG,"onReceiveServicePid:" + TXT + i);
    }

    /**
     * 接收 cid
     */
    @Override
    public void onReceiveClientId(Context context, String clientid) {
        try {
            Log.i(TAG,"onReceiveClientId:" + TXT );
            JSONObject properties = new JSONObject();
            properties.put("gtId", clientid);
            // 将 clientid 保存到用户表 gtId 中
            SensorsDataAPI.sharedInstance().profileSet(properties);
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
        try {
            Log.i(TAG,"onReceiveMessageData:" + TXT );
            JSONObject properties = new JSONObject();
            // 获取消息 ID，并保存在事件属性 msg_id 中
            properties.put("msg_id", gtTransmitMessage.getMessageId() );
            // 追踪 "点击打开消息" 事件
            SensorsDataAPI.sharedInstance(getApplicationContext()).track("OpenNotification", properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * cid 离线上线通知
     */
    @Override
    public void onReceiveOnlineState(Context context, boolean b) {
        Log.i(TAG,"onReceiveOnlineState:" + TXT );
    }

    /**
     * 各种事件处理回执
     */
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {
        Log.i(TAG, "onReceiveCommandResult:" + TXT);

    }

    //onNotificationMessageClicked


    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {
        Log.i(TAG, "onNotificationMessageClicked:" + TXT);
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {
        Log.i(TAG, "onNotificationMessageArrived:" + TXT);
        Toast.makeText(context," 个推 消息到达",Toast.LENGTH_SHORT).show();
    }
}
