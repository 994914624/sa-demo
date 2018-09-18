package cn.sensorsdata.demo.test;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sensorsdata.analytics.android.sdk.ScreenAutoTracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.sensorsdata.demo.R;
import cn.sensorsdata.demo.fragment.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 *
 */

public class RecycleFragment extends BaseFragment implements ScreenAutoTracker {

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;
    private  View view=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view=inflater.inflate(R.layout.fragment_recycle, container, false);


        initData();
        initView();
        return view;
    }


    private void initData() {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new MyAdapter(getData());
    }

    private void initView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.test_recycleView);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        //分割线
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
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



    public RecycleFragment() {
        // Required empty public constructor
    }

    @Override
    public String getScreenUrl() {
        return this.getClass().getCanonicalName();
    }

    @Override
    public JSONObject getTrackProperties() throws JSONException {
        return null;
    }

    /**
     * 
     */

}
