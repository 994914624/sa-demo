package cn.sensorsdata.demo.bean;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yang on 2017/6/16.
 */

public class TestToJS extends Object {
    Context context;
    public TestToJS(Context context) {
        this.context=context;
    }

    @JavascriptInterface
    public void javaMethod(String str){
        Toast.makeText(context,"js调用"+str,Toast.LENGTH_SHORT).show();
    }
    @JavascriptInterface
    public String getAppDistinctID(){
        Toast.makeText(context,"getAppDistinctID",Toast.LENGTH_SHORT).show();
        try {
            JSONObject properties = new JSONObject();
            //这里传平台类型字段给js,是为了区分Android或iOS中的H5
            properties.put("Platform", "Android-H5");
            String loginId = SensorsDataAPI.sharedInstance().getLoginId();
            //这里是将用户标识ID 传给js,如果是登录的用户，优先传递登录ID给js,否则传递匿名ID给js
            if (!TextUtils.isEmpty(loginId)) {
                properties.put("distinct_id", loginId);
            } else {
                properties.put("distinct_id", SensorsDataAPI.sharedInstance().getAnonymousId());
            }
            return properties.toString();
        } catch (JSONException e) {
           e.printStackTrace();
        }
        return null;
    }
}
