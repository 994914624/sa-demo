package cn.sensorsdata.demo.yang;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sensorsdata.analytics.android.sdk.SensorsDataIgnoreTrackAppViewScreen;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sensorsdata.demo.R;
import cn.sensorsdata.demo.bean.UserData;
//import cn.sensorsdata.demo.databinding.ActivityYangSingleBinding;
import cn.sensorsdata.demo.fragment.HomeFragment;
import cn.sensorsdata.demo.fragment.MyFragment;

@Route(path = "/yangsingle/activity")
public class YangSingleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //databinding
//        ActivityYangSingleBinding binding=DataBindingUtil.setContentView(this, R.layout.activity_yang_single);
//        UserData userdata = new UserData("databinding", "yang");
//        binding.setUserData(userdata);
//        binding.setClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(YangSingleActivity.this,"databinding",Toast.LENGTH_SHORT).show();
//            }
//        });

        setContentView(R.layout.activity_yang_single);
        //lambda
        //findViewById(R.id.bt_lambda).setOnClickListener(view -> Toast.makeText(this,"lambda",Toast.LENGTH_SHORT).show());

        ButterKnife.bind(this);

    }


    /**
     * ButterKnife 方式
     */
    @OnClick(R.id.bt_bn)
    void click(View view){

        Toast.makeText(this,"bt_bn",Toast.LENGTH_SHORT).show();

        //SensorsDataAPI.sharedInstance().setServerUrl("http://test2-zouyuhan.cloud.sensorsdata.cn:8006/sa?project=yangzhankun&token=386e4bed00b5701e");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
