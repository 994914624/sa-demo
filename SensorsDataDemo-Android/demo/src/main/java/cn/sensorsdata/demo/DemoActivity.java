package cn.sensorsdata.demo;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.DefaultLayoutHelper;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.FloatLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.sensorsdata.analytics.android.sdk.ScreenAutoTracker;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackAppViewScreenUrl;
import com.sensorsdata.analytics.android.sdk.SensorsDataIgnoreTrackAppViewScreen;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
//import com.sensorsdata.analytics.android.sdk.SensorsDataTrackViewOnClick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;

import butterknife.OnClick;
import cn.sensorsdata.demo.bean.WeChatMessage;
import cn.sensorsdata.demo.constant.SeverURLConfig;
import cn.sensorsdata.demo.fragment.HomeFragment;
import cn.sensorsdata.demo.fragment.MineFragment;
import cn.sensorsdata.demo.fragment.MyFragment;
import cn.sensorsdata.demo.test.SensorsData;


@Route(path = "/demo/activity")






@SensorsDataAutoTrackAppViewScreenUrl(url="xxx.demo页面")
public class DemoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        ButterKnife.bind(this);

        Log.e("/demo/activity",SensorsDataUtils.getUserAgent(this)+"");

        this.setTitle("商品详情");


        initToolbar();
        initFragment();
        //initViewPager();
        //intRecylerView();
    }


    private boolean isSupportJellyBean;
    private FragmentManager fragmentManager=null;



    private List<android.support.v4.app.Fragment> listPagerViews = null;
    private PagerAdapter pagerAdapter=null;
    private void initViewPager() {
        listPagerViews=new ArrayList<>();

        listPagerViews.add(new cn.sensorsdata.demo.fragment.MyFragment());
        listPagerViews.add(new MineFragment());
        listPagerViews.add(new HomeFragment());
        ViewPager viewPager= (ViewPager) findViewById(R.id.demo_viewPager);
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

    private MyFragment myFragment=null;
    private HomeFragment homeFragment=null;
    private MineFragment mineFragment=null;
    private LinearLayout frag_lineLayout=null;
    private void initFragment() {
        frag_lineLayout= (LinearLayout) findViewById(R.id.frag_LinearLayout);
        fragmentManager=this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
         myFragment=new MyFragment();
         homeFragment=new HomeFragment();
         mineFragment=new MineFragment();

        fragmentTransaction.add(R.id.frag_LinearLayout,myFragment);
        fragmentTransaction.hide(myFragment);
        fragmentTransaction.add(R.id.frag_LinearLayout,homeFragment);
        fragmentTransaction.hide(homeFragment);
        fragmentTransaction.add(R.id.frag_LinearLayout,mineFragment);
        fragmentTransaction.hide(mineFragment);
        fragmentTransaction.show(mineFragment);
        fragmentTransaction.commit();
        //
        //mineFragment.setUserVisibleHint(true);
        LinearLayout ll= (LinearLayout) findViewById(R.id.frag_LinearLayout);

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.demo_TooBar);
//        WeChatMessage one=DataSupport.find(WeChatMessage.class,1);
//        Log.i("###",DataSupport.findAll(WeChatMessage.class,false).toString()+"");
//        DataSupport.findAll(WeChatMessage.class,true);
        toolbar.setTitle("测试");
        toolbar.setSubtitle("123");
        setSupportActionBar(toolbar);
    }

    private void intRecylerView() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recyclerView);

        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        //layoutManager.setReverseLayout(true);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(10, 10, 10, 10);
            }
        });

        final List<LayoutHelper> helpers = new LinkedList<>();

        final GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setItemCount(25);


        final ScrollFixLayoutHelper scrollFixLayoutHelper = new ScrollFixLayoutHelper(FixLayoutHelper.TOP_RIGHT, 100, 100);
        StickyLayoutHelper slh=new StickyLayoutHelper();


        helpers.add(DefaultLayoutHelper.newHelper(7));
        helpers.add(slh);
        helpers.add(scrollFixLayoutHelper);
        helpers.add(DefaultLayoutHelper.newHelper(2));
        helpers.add(gridLayoutHelper);


        layoutManager.setLayoutHelpers(helpers);

        recyclerView.setAdapter(
                new VirtualLayoutAdapter(layoutManager) {
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        return new MainViewHolder(new TextView(DemoActivity.this));
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                        VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, 300);
                        holder.itemView.setLayoutParams(layoutParams);

                        ((TextView) holder.itemView).setText(Integer.toString(position));

                        ((TextView) holder.itemView).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(DemoActivity.this,""+position,Toast.LENGTH_SHORT).show();
                            }
                        });

                        if (position == 7) {
                            layoutParams.height = 200;
                            layoutParams.width = 200;
                        }
                        if (position == 8) {
                            layoutParams.height = 100;
                            layoutParams.width = 100;
                        } else if (position > 35) {
                            layoutParams.height = 200 + (position - 30) * 100;
                        }

                        if (position > 35) {
                            holder.itemView.setBackgroundColor(0x66cc0000 + (position - 30) * 128);
                        } else if (position % 2 == 0) {
                            holder.itemView.setBackgroundColor(0xaa00ff00);
                        } else {
                            holder.itemView.setBackgroundColor(0xccff00ff);
                        }


                    }

                    @Override
                    public int getItemCount() {
                        List<LayoutHelper> helpers = getLayoutHelpers();
                        if (helpers == null) {
                            return 0;
                        }
                        int count = 0;
                        for (int i = 0, size = helpers.size(); i < size; i++) {
                            count += helpers.get(i).getItemCount();
                        }
                        return count;
                    }
                });

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(7);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }, 6000);
    }



    static class MainViewHolder extends RecyclerView.ViewHolder {

        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }




    /**
     * 方案①：在点击事件上加我们的注解：@SensorsDataTrackViewOnClick
     */
    //@SensorsDataTrackViewOnClick

    @OnClick(R.id.你好)
    public void myClick(View view) {
        // TODO
        Log.i("###","trackTimerBegin");

        Toast.makeText(this,"demo",Toast.LENGTH_SHORT).show();
        ARouter.getInstance().build("/xiaomipush/activity").navigation();

    }

    /**
     * 方案②：写成标准的点击事件名 onClick 并且 implements View.OnClickListener
     */
    @OnClick(R.id.butterknife02)
    @Override
    public void onClick(View view) {
        // TODO
        Log.i("###","trackTimerEnd");
        //SensorsDataAPI.sharedInstance(this).trackTimerEnd("testTime");
        Toast.makeText(this,"demo2",Toast.LENGTH_SHORT).show();
    }

    /**
     * 火猫退出，进程并没有真正的被杀死。当有当前包下有多进程的时候，这种方式并不能退出。
     * 导致后台一直，有当前的进程，从而有Application 的实例，导致不走onCreate方法。
     */
    private void exit(){
        this.finish();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }





    /**
     * BUTTON1 和 BUTTON2 切换 Fragment
     */
    public void btOnClick(View view) {
        this.setTitle("title111");
        // TODO
        //SensorsDataAPI.sharedInstance(this).trackTimerBegin("testTime", TimeUnit.MINUTES);
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.hide(myFragment);
        fragmentTransaction.hide(mineFragment);
        fragmentTransaction.hide(homeFragment);
        fragmentTransaction.show(myFragment);
        fragmentTransaction.commit();
        //myFragment.setUserVisibleHint(false);

        Toast.makeText(this,"11",Toast.LENGTH_SHORT).show();
    }
    public void btOnClick2(View view) {
        this.setTitle("title222");
        // TODO
        //SensorsDataAPI.sharedInstance(this).trackTimerBegin("testTime", TimeUnit.MINUTES);
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.hide(myFragment);
        fragmentTransaction.hide(mineFragment);
        fragmentTransaction.hide(homeFragment);
        fragmentTransaction.show(mineFragment);
        fragmentTransaction.commit();
        //mineFragment.setUserVisibleHint(true);

        Toast.makeText(this,"22",Toast.LENGTH_SHORT).show();
    }



}
