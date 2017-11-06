package cn.sensorsdata.demo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sensorsdata.analytics.android.sdk.ScreenAutoTracker;
import com.sensorsdata.analytics.android.sdk.SensorsDataIgnoreTrackAppViewScreen;

import org.json.JSONException;
import org.json.JSONObject;

import cn.sensorsdata.demo.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */

public class BaseFragment extends Fragment implements ScreenAutoTracker {


    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public String getScreenUrl() {
        return this.getClass().getCanonicalName();
    }

    @Override
    public JSONObject getTrackProperties() throws JSONException {
        return null;
    }
}
