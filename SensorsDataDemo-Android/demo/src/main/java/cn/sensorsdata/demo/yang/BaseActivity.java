package cn.sensorsdata.demo.yang;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sensorsdata.analytics.android.sdk.ScreenAutoTracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.sensorsdata.demo.R;
import cn.sensorsdata.demo.fragment.MyFragment;

@Route(path = "/base/activity")
public class BaseActivity extends AppCompatActivity implements ScreenAutoTracker {


    @Override
    public String getScreenUrl() {
        return null;
    }

    @Override
    public JSONObject getTrackProperties() throws JSONException {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);



    }


    /**
     * View 被 remove 时，包含此 View 的 Activity 退出时，会回调此方法
     */
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * 此时已完成 View 的 measure
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }



    /**
     * Called when activity resume is complete (after {@link #onResume} has
     * been called). Applications will generally not implement this method;
     * it is intended for system classes to do final setup after application
     * resume code has run.
     *
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onResume
     */
    @Override
    protected void onPostResume() {
        super.onPostResume();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}









