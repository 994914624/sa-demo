package cn.sensorsdata.demo.yang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

import cn.sensorsdata.demo.R;


@Route(path = "/yangfrag/activity")
public class YangfragActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yangfrag);
        initView();


    }

    LinearLayout ll_frag1=null;
    LinearLayout ll_frag2=null;
    LinearLayout ll_frag3=null;
    private void initView() {
        Button bt_frag1= (Button) findViewById(R.id.bt_frag_1);
        Button bt_frag2= (Button) findViewById(R.id.bt_frag_2);
        Button bt_frag3= (Button) findViewById(R.id.bt_frag_3);
        ll_frag1= (LinearLayout) findViewById(R.id.ll_frag_1);
        ll_frag2= (LinearLayout) findViewById(R.id.ll_frag_2);
        ll_frag3= (LinearLayout) findViewById(R.id.ll_frag_3);
        bt_frag1.setOnClickListener(this);
        bt_frag2.setOnClickListener(this);
        bt_frag3.setOnClickListener(this);
        ll_frag1.setVisibility(View.VISIBLE);
        ll_frag2.setVisibility(View.INVISIBLE);
        ll_frag3.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this,"click",Toast.LENGTH_SHORT).show();
        switch (v.getId()){
            case R.id.bt_frag_1 :
                ll_frag1.setVisibility(View.VISIBLE);
                ll_frag2.setVisibility(View.INVISIBLE);
                ll_frag3.setVisibility(View.INVISIBLE);
                    break;
            case R.id.bt_frag_2 :
                ll_frag1.setVisibility(View.INVISIBLE);
                ll_frag2.setVisibility(View.VISIBLE);
                ll_frag3.setVisibility(View.INVISIBLE);
                    break;
            case R.id.bt_frag_3 :
                ll_frag1.setVisibility(View.INVISIBLE);
                ll_frag2.setVisibility(View.INVISIBLE);
                ll_frag3.setVisibility(View.VISIBLE);
                    break;
        }
    }
}
