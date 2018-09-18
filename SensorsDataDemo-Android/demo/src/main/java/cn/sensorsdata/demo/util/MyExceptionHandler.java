package cn.sensorsdata.demo.util;

import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

import org.json.JSONException;
import org.json.JSONObject;

import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * Created by yang on 2017/9/15
 */

public class MyExceptionHandler  implements Thread.UncaughtExceptionHandler {

    private static final int SLEEP_TIMEOUT_MS = 400;

    private static MyExceptionHandler sInstance;
    private final Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    public MyExceptionHandler() {
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static void init() {
        if (sInstance == null) {
            synchronized (ExceptionHandler.class) {
                if (sInstance == null) {
                    sInstance = new MyExceptionHandler();
                }
            }
        }
    }

    @Override
    public void uncaughtException(final Thread t, final Throwable e) {

        // Only one worker thread - giving priority to storing the event first and then flush
//        SensorsDataAPI.allInstances(new SensorsDataAPI.InstanceProcessor() {
//            @Override
//            public void process(SensorsDataAPI sensorsData) {
//                try {
//                    final JSONObject messageProp = new JSONObject();
//
//                    StringBuffer stackTrace = new StringBuffer();
//                    StackTraceElement[] elements = e.getStackTrace();
//                    if (elements != null) {
//                        for (int i = 0; i < elements.length; i++) {
//                            stackTrace.append(elements[i].toString() + "\n");
//                        }
//
//                        messageProp.put("app_crashed_reason", stackTrace.toString());
//                    }
//                    sensorsData.track("AppCrashed", messageProp);
//                } catch (JSONException e) {}
//            }
//        });
//
//        SensorsDataAPI.allInstances(new SensorsDataAPI.InstanceProcessor() {
//            @Override
//            public void process(SensorsDataAPI sensorsData) {
//                sensorsData.flush();
//            }
//        });

        if (mDefaultExceptionHandler != null) {
            mDefaultExceptionHandler.uncaughtException(t, e);
        } else {
            killProcessAndExit();
        }
    }

    private void killProcessAndExit() {
        try {
            Thread.sleep(SLEEP_TIMEOUT_MS);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }
}
