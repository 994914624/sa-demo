package cn.sensorsdata.demo.yang;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.alibaba.android.arouter.facade.annotation.Route;

import cn.sensorsdata.demo.R;
import cn.sensorsdata.demo.fragment.Myapp1Fragment;
import cn.sensorsdata.demo.fragment.Myapp2Fragment;

/**
 * android.app.Fragment
 */
@Route(path = "/yangappfrag/activity")
public class YangappfragActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yangappfrag);
        initFragment();
    }

    FragmentManager fragmentManager=null;
    Myapp1Fragment myapp1Fragment=null;
    Myapp2Fragment myapp2Fragment=null;
    @TargetApi(11)
    private void initFragment() {
        Button bt_appfrag1= (Button) findViewById(R.id.bt_appfrag_1);
        Button bt_appfrag2= (Button) findViewById(R.id.bt_appfrag_2);
        bt_appfrag1.setOnClickListener(this);
        bt_appfrag2.setOnClickListener(this);
        LinearLayout ll= (LinearLayout) findViewById(R.id.ll_appfrag);
        fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        myapp1Fragment=new Myapp1Fragment();
        myapp2Fragment=new Myapp2Fragment();
        fragmentTransaction.add(R.id.ll_appfrag,myapp1Fragment);
        fragmentTransaction.add(R.id.ll_appfrag,myapp2Fragment);
        fragmentTransaction.hide(myapp2Fragment);
        fragmentTransaction.commit();


    }

    @Override
    @TargetApi(11)
    public void onClick(View v) {

        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Toast.makeText(this,"click",Toast.LENGTH_SHORT).show();
        switch (v.getId()){
            case R.id.bt_appfrag_1 :
                fragmentTransaction.hide(myapp1Fragment);
                fragmentTransaction.hide(myapp2Fragment);
                fragmentTransaction.show(myapp1Fragment);
                break;
            case R.id.bt_appfrag_2 :
                fragmentTransaction.hide(myapp1Fragment);
                fragmentTransaction.hide(myapp2Fragment);
                fragmentTransaction.show(myapp2Fragment);
                break;

        }
        fragmentTransaction.commit();
    }
}
