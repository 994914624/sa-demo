package cn.sensorsdata.demo.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;

import cn.sensorsdata.demo.R;

@Route(path = "/yangrecycleview/activity")
public class YangRecycleViewActivity extends Activity {

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yang_recycle_view);

        initData();
        initView();
    }

    private void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new MyAdapter(getData());
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.test_recycleView);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        //分割线
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String temp = " item";
        for(int i = 0; i < 20; i++) {
            data.add(i + temp);
        }

        return data;
    }
}



