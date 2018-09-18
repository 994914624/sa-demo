package cn.sensorsdata.demo.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sensorsdata.analytics.android.sdk.ScreenAutoTracker;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataIgnoreTrackAppViewScreen;
import com.sensorsdata.analytics.android.sdk.SensorsDataTrackFragmentAppViewScreen;
import com.sensorsdata.analytics.android.sdk.SensorsDataTrackFragmentAppViewScreen;

import org.json.JSONException;
import org.json.JSONObject;

import cn.sensorsdata.demo.R;
import cn.sensorsdata.demo.test.SensorsData;

/**
 * A simple {@link Fragment} subclass.
 *
 */
@SensorsDataTrackFragmentAppViewScreen
public  abstract class BaseFragment extends Fragment implements ScreenAutoTracker {

    private static final String TAG="SA.Sensors.BaseFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view=inflater.inflate(R.layout.fragment_base, container, false);
        //SensorsDataAPI.sharedInstance().traverseView(this,view);
        return view;
    }

//    @Override
//    public void onResume() {
//        Log.e(TAG,"onResume");
//        super.onResume();
//        //SensorsDataAPI.sharedInstance().onFragmentResume(this);
//
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        Log.e(TAG,"setUserVisibleHint");
//        super.setUserVisibleHint(isVisibleToUser);
//        //SensorsDataAPI.sharedInstance().setFragmentUserVisibleHint(this,isVisibleToUser);
//
//    }
//
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        Log.e(TAG,"onHiddenChanged");
//        super.onHiddenChanged(hidden);
//        //SensorsDataAPI.sharedInstance().onFragmentHiddenChanged(this,hidden);
//    }


    public BaseFragment() {
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
     * show()或hide() Fragment不重叠（源码support -v4 24.0.0及以上不用考虑）
     *
     * Fragment重叠的根本原因在于FragmentState没有保存Fragment的显示状态，
     * 即mHidden，那我们就自己手动在Fragment中维护一个mSupportHidden，
     * 在页面重启后，我们自己来决定Fragment是否显示
     *
     */
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
        }


    /**
     * 如果你需要在Fragment中用到宿主Activity对象，
     * 建议在你的基类Fragment定义一个Activity的全局变量，在onAttach中初始化，
     * 这不是最好的解决办法，但这可以有效避免一些意外Crash。
     *
     */
    protected Activity mActivity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    /**
     * Set a hint for whether this fragment's menu should be visible
     */
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(getView()!=null){
            getView().setVisibility(menuVisible?View.VISIBLE:View.GONE);
        }
    }
}
