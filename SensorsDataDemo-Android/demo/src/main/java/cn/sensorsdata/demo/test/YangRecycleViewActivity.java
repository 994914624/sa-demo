package cn.sensorsdata.demo.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;

import cn.sensorsdata.demo.R;

@Route(path = "/yangrecycleview/activity")
public class YangRecycleViewActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yang_recycle_view);
        initFragment();
    }
    private FragmentManager fragmentManager=null;

    private void initFragment() {

        fragmentManager=this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.ll_rv_activity,new RecycleFragment());
        fragmentTransaction.commit();

    }
}



