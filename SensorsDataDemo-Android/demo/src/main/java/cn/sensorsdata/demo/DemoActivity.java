package cn.sensorsdata.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sensorsdata.analytics.android.sdk.SensorsDataTrackViewOnClick;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DemoActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);
    }

    /**
     * 方案①：在点击事件上加我们的注解：@SensorsDataTrackViewOnClick
     */
    @SensorsDataTrackViewOnClick
    @OnClick(R.id.butterknife01)
    public void myClick(View view) {
        // TODO
        Toast.makeText(this,"demo",Toast.LENGTH_SHORT).show();
    }

    /**
     * 方案②：写成标准的点击事件名 onClick 并且 implements View.OnClickListener
     */
    @OnClick(R.id.butterknife02)
    @Override
    public void onClick(View view) {
        // TODO
    }

    /**
     * 火猫退出，进程并没有真正的被杀死。当有当前包下有多进程的时候，这种方式并不能退出。
     * 导致后台一直，有当前的进程，从而有Application 的实例，导致不走onCreate方法。
     */
    private void exit(){
        this.finish();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }








}
