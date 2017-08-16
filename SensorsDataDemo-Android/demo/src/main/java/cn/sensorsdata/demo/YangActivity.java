package cn.sensorsdata.demo;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sensorsdata.analytics.android.sdk.ScreenAutoTracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.sensorsdata.demo.fragment.MyFragment;

@Route(path = "/yang/activity")
public class YangActivity extends AppCompatActivity implements ScreenAutoTracker {


    @Override
    public String getScreenUrl() {
        return null;
    }

    @Override
    public JSONObject getTrackProperties() throws JSONException {
        return new JSONObject().put("","");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yang);

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.ll_frag_my,new MyFragment());
        fragmentTransaction.commit();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate=dateFormat.format(new Date());
    }


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}









