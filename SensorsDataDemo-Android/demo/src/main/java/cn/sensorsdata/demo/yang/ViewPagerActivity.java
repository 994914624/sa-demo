package cn.sensorsdata.demo.yang;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.sensorsdata.demo.R;
import cn.sensorsdata.demo.fragment.HomeFragment;
import cn.sensorsdata.demo.fragment.MineFragment;
import cn.sensorsdata.demo.fragment.MyFragment;

public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        initViewPager();
    }



    private List<Fragment> listPagerViews = null;
    private PagerAdapter pagerAdapter=null;
    private void initViewPager() {
        listPagerViews=new ArrayList<>();

        listPagerViews.add(new MyFragment());
        listPagerViews.add(new HomeFragment());
        listPagerViews.add(new MineFragment());
        ViewPager viewPager= (ViewPager) findViewById(R.id.view_pager_activity_vp);
        pagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
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
