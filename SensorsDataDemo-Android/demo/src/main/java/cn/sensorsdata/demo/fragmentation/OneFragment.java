package cn.sensorsdata.demo.fragmentation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sensorsdata.analytics.android.sdk.SensorsDataTrackFragmentAppViewScreen;

import java.util.ArrayList;
import java.util.List;

import cn.sensorsdata.demo.R;
import cn.sensorsdata.demo.fragment.HomeFragment;
import cn.sensorsdata.demo.fragment.MineFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 *
 */

@SensorsDataTrackFragmentAppViewScreen
public class OneFragment extends SupportFragment {


    public OneFragment() {
        // Required empty public constructor
    }


    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_one, container, false);
        initViewPager();
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

    private FragmentManager fragmentManager=null;



    private List<Fragment> listPagerViews = null;
    private PagerAdapter pagerAdapter=null;
    private void initViewPager() {
        listPagerViews=new ArrayList<>();

        listPagerViews.add(new cn.sensorsdata.demo.fragment.MyFragment());
        listPagerViews.add(new MineFragment());
        listPagerViews.add(new HomeFragment());
        ViewPager viewPager= (ViewPager) v.findViewById(R.id.one_viewPager);
        pagerAdapter=new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return listPagerViews.get(position);
            }

            @Override
            public int getCount() {
                return listPagerViews.size();
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);
            }

        };

        viewPager.setAdapter(pagerAdapter);

    }
}
