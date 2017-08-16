package cn.sensorsdata.demo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sensorsdata.analytics.android.sdk.ScreenAutoTracker;
import com.sensorsdata.analytics.android.sdk.SensorsDataIgnoreTrackAppViewScreen;

import org.json.JSONException;
import org.json.JSONObject;

import cn.sensorsdata.demo.R;


/**
 * A simple {@link Fragment} subclass.
 */
//@SensorsDataIgnoreTrackAppViewScreen
public class MineFragment extends HomeFragment implements View.OnClickListener, AdapterView.OnItemClickListener,ScreenAutoTracker {


    private View view = null;
    private ListView listView = null;


    private String[] arrStr = new String[]{
            "我的上传", "我的直播", "播放记录",
            "我的收藏", "关注的IDOL",
            "购物车", "我的订单",
            "会员中心", "我的钱包", "客服中心"
    };


    public MineFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mine, container, false);
        view = inflater.inflate(R.layout.fragment_mine, container, false);

        initView();
        initHeader();
        initAdapter();

        return view;
    }

    private void initAdapter()
    {
    }

    /**
     * @yang 头布局, 尾部
     */
    RelativeLayout wanshangerenziliao = null;
    RelativeLayout fensi = null;
    RelativeLayout guanzhu = null;
    RelativeLayout fabu = null;
    ImageView touxian = null;
    TextView nicheng = null;

    private void initHeader()
    {

    }

    ImageView map = null;

    private void initView()
    {
        listView = (ListView) view.findViewById(R.id.listView_mine);

        map = (ImageView) view.findViewById(R.id.map);
        map.setOnClickListener(this);
        view.findViewById(R.id.test_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"bb", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view)
    {


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Toast.makeText(getActivity(), "位置" + i, Toast.LENGTH_SHORT).show();



    }

    /**
     *
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public String getScreenUrl() {
        return "frg/mine";
    }

    @Override
    public JSONObject getTrackProperties() throws JSONException {
        return new JSONObject().put("fragP","12345677890");
    }
}