package cn.sensorsdata.demo.util;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.PowerManager;
import android.view.Display;

import java.util.List;

/**
 * Created by yzk on 2018/8/31
 */

public class MyUtils {


    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean isScreenOn(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= 20) {
                for (Display state : ((DisplayManager) context.getSystemService("display")).getDisplays()) {
                    if (state.getState() != 1) {
                        return true;
                    }
                }
                return false;
            } else if (((PowerManager) context.getSystemService("power")).isScreenOn()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return true;
        }
    }
}
