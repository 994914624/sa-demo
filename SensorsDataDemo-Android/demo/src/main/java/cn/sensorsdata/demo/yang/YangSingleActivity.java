package cn.sensorsdata.demo.yang;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.sensorsdata.analytics.android.sdk.SensorsDataIgnoreTrackAppViewScreen;

import cn.sensorsdata.demo.R;
import cn.sensorsdata.demo.fragment.HomeFragment;
import cn.sensorsdata.demo.fragment.MyFragment;

@SensorsDataIgnoreTrackAppViewScreen
public class YangSingleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yang_single);

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.ll_single,new HomeFragment());
        fragmentTransaction.commit();
    }
}
