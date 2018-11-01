package cn.sensorsdata.demo.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


import com.sensorsdata.analytics.android.sdk.Pathfinder;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.util.AopUtil;

/**
 * Created by yzk on 2018/9/7
 */

public class SensorsDataUtil {

    private static String CLASS_NAME = "className";

    /**
     * 调用track接口，追踪一个带有属性的事件
     *
     * @param eventName  事件的名称
     * @param properties 事件的属性
     * @param activity   事件发生时所在的 Activity，用于获取类名
     */
    public static void track(final String eventName, JSONObject properties, final Activity activity) {
        try {
            if (activity != null) {
                String className = activity.getClass().getCanonicalName();
                if (properties == null) {
                    properties = new JSONObject();
                }
                properties.put(CLASS_NAME, className);
            }
            SensorsDataAPI.sharedInstance().track(eventName, properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用track接口，追踪一个带有属性的事件
     *
     * @param eventName  事件的名称
     * @param properties 事件的属性
     * @param fragment   事件发生时所在的 Fragment，用于获取类名
     */
    public static void track(final String eventName, JSONObject properties, final android.app.Fragment fragment) {
        try {
            if (fragment != null) {
                String className = fragment.getClass().getCanonicalName();
                if (properties == null) {
                    properties = new JSONObject();
                }
                properties.put(CLASS_NAME, className);
            }
            SensorsDataAPI.sharedInstance().track(eventName, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用track接口，追踪一个带有属性的事件
     *
     * @param eventName  事件的名称
     * @param properties 事件的属性
     * @param fragment   事件发生时所在的 Fragment，用于获取类名
     */
    public static void track(final String eventName, JSONObject properties, final android.support.v4.app.Fragment fragment) {
        try {
            if (fragment != null) {
                String className = fragment.getClass().getCanonicalName();
                if (properties == null) {
                    properties = new JSONObject();
                }
                properties.put(CLASS_NAME, className);
            }
            SensorsDataAPI.sharedInstance().track(eventName, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用track接口，追踪一个带有属性的事件
     *
     * @param eventName  事件的名称
     * @param properties 事件的属性
     * @param view       事件发生时所在的 View ，用于获取类名、ViewPath
     */
    public static void track(final String eventName, JSONObject properties, final View view) {
        try {
            if (view != null) {
                if (properties == null) {
                    properties = new JSONObject();
                }

                //获取所在的 Context
                Context context = view.getContext();
                //将 Context 转成 Activity
                Activity activity = AopUtil.getActivityFromContext(context, view);
                if (activity != null) {
                    //获取类名
                    String className = activity.getClass().getCanonicalName();
                    properties.put(CLASS_NAME, className);
                    getFragmentNameFromView(view, properties);
                    // viewPath
                    addViewPathProperties(activity, view, properties);
                }
            }
            SensorsDataAPI.sharedInstance().track(eventName, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存用户 推送 ID 到用户表
     *
     * @param property 属性名称（例如 jgId ）
     * @param pushId   推送 ID
     * @param context  context
     *                 使用：profilePushId("jgId",JPushInterface.getRegistrationID(this),this);
     */
    public  void profilePushId(String property, String pushId, Context context) {
        try {
            if (TextUtils.isEmpty(property)||TextUtils.isEmpty(pushId) || context == null) {
                return;
            }
            //当前的 distinctId + jgId
            String distinctId = SensorsDataAPI.sharedInstance().getLoginId();
            if (TextUtils.isEmpty(distinctId)) {
                distinctId = SensorsDataAPI.sharedInstance().getAnonymousId();
            }
            String distinctId_jgId = distinctId + pushId;

            //获取存储的 sp_distinctId_jgId
            SharedPreferences sp = context.getSharedPreferences(context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
            String sp_distinctId_jgId = sp.getString("distinctId" + property, "");

            // 当前的 和 存储的 sp_distinctId_jgId 不相等时，触发 profileSet 设置 jgId
            if (!distinctId_jgId.equals(sp_distinctId_jgId)) {
                SensorsDataAPI.sharedInstance().profileSet(property, pushId);
                sp.edit().putString("distinctId" + property, distinctId_jgId).apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //----------------------------------------------------------------------

    private static void getFragmentNameFromView(View view, JSONObject properties) {
        try {
            String fragmentName = (String) view.getTag(com.sensorsdata.analytics.android.sdk.R.id.sensors_analytics_tag_view_fragment_name);
            String fragmentName2 = (String) view.getTag(com.sensorsdata.analytics.android.sdk.R.id.sensors_analytics_tag_view_fragment_name2);
            if (!TextUtils.isEmpty(fragmentName2)) {
                fragmentName = fragmentName2;
            }
            if (!TextUtils.isEmpty(fragmentName)) {
                String screenName = properties.optString(CLASS_NAME);
//                    if (!TextUtils.isEmpty(fragmentName)) {
                properties.put(CLASS_NAME, String.format(Locale.CHINA, "%s|%s", screenName, fragmentName));
//                    } else {
//                        properties.put(CLASS_NAME, fragmentName);
//                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addViewPathProperties(Activity activity, View view, JSONObject properties) {
        try {
            ViewParent viewParent;
            List<String> viewPath = new ArrayList<>();
            do {
                viewParent = view.getParent();
                int index = getChildIndex(viewParent, view);
                viewPath.add(view.getClass().getCanonicalName() + "[" + index + "]");
                if (viewParent instanceof ViewGroup) {
                    view = (ViewGroup) viewParent;
                }

            } while (viewParent instanceof ViewGroup);

            Collections.reverse(viewPath);
            StringBuilder stringBuffer = new StringBuilder();
            for (int i = 1; i < viewPath.size(); i++) {
                stringBuffer.append(viewPath.get(i));
                if (i != (viewPath.size() - 1)) {
                    stringBuffer.append("/");
                }
            }
            properties.put("$element_selector", stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static int getChildIndex(ViewParent parent, View child) {
        try {
            if (parent == null || !(parent instanceof ViewGroup)) {
                return -1;
            }

            ViewGroup _parent = (ViewGroup) parent;
            final String childIdName = AopUtil.getViewId(child);

            String childClassName = child.getClass().getCanonicalName();
            int index = 0;
            for (int i = 0; i < _parent.getChildCount(); i++) {
                View brother = _parent.getChildAt(i);

                if (!Pathfinder.hasClassName(brother, childClassName)) {
                    continue;
                }

                String brotherIdName = AopUtil.getViewId(brother);

                if (null != childIdName && !childIdName.equals(brotherIdName)) {
                    index++;
                    continue;
                }

                if (brother == child) {
                    return index;
                }

                index++;
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
