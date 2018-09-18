package cn.sensorsdata.demo.fragmentation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sensorsdata.analytics.android.sdk.SensorsDataTrackFragmentAppViewScreen;

import cn.sensorsdata.demo.R;
import cn.sensorsdata.demo.XiaomiPushActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
@SensorsDataTrackFragmentAppViewScreen
public class TwoFragment extends SupportFragment {


    public TwoFragment() {
        // Required empty public constructor
    }


    View  v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       v=inflater.inflate(R.layout.fragment_two, container, false);
        v.findViewById(R.id.two_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), XiaomiPushActivity.class));
            }
        });
        return v;
    }

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
}
