package cn.sensorsdata.demo.yang;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import cn.sensorsdata.demo.util.AppInfoUtil;

/**
 * Created by yang on 2018/3/1
 */

public class MyIntentService extends IntentService {

private static String TAG="MyIntentService";

    public MyIntentService() {
        super("MyIntentService");

    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        for (int i = 0; i < AppInfoUtil.getInstance(this).getInstalledApps(2).size(); i++) {

            Log.i(TAG, AppInfoUtil.getInstance(this).getInstalledApps(2).get(i).getAppName() + "");
        }

    }
}