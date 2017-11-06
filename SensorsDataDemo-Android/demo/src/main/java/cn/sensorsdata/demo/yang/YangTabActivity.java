package cn.sensorsdata.demo.yang;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.tendcloud.tenddata.TCAgent;

import cn.sensorsdata.demo.R;


@Route(path = "/yangtab/activity")
public class YangTabActivity extends TabActivity {

    private TabHost tab=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tab=this.getTabHost();
        LayoutInflater.from(this).inflate(R.layout.activity_yang_tab, tab.getTabContentView(), true);
        tab.addTab(tab.newTabSpec("选项卡一").setIndicator("选项卡一",
                getResources().getDrawable(R.mipmap.right_arrow)).setContent(R.id.tab01));
        tab.addTab(tab.newTabSpec("选项卡二").setIndicator("选项卡二",
                getResources().getDrawable(R.mipmap.ic_launcher)).setContent(R.id.tab02));
        tab.addTab(tab.newTabSpec("选项卡三").setIndicator("选项卡三",
                getResources().getDrawable(R.mipmap.left_back)).setContent(R.id.tab03));


        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                Toast.makeText(YangTabActivity.this,"tab", Toast.LENGTH_SHORT).show();
            }
        });


//        Method setOnClickListenerMethod = tab.getClass().getMethod("setOnClickListener", View.OnClickListener.class);
//
//        setOnClickListenerMethod.invoke(focusView, onClickListenr);
    }


    @Override
    protected void onResume() {
        SensorsDataAPI.sharedInstance().track("testOnResume1");
        super.onResume();
        SensorsDataAPI.sharedInstance().track("testOnResume2");

        TCAgent.onPageStart(this,"A");//talkingdata
        SensorsDataAPI.sharedInstance().trackTimerBegin("YangTabActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        TCAgent.onPageEnd(this,"A");
        SensorsDataAPI.sharedInstance().trackTimerEnd("YangTabActivity");
    }
}
