package cn.sensorsdata.demo.fragmentation;


import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.sensorsdata.demo.R;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class FragmentationActivity extends SupportActivity {

    private SupportFragment[] mFragments = new SupportFragment[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmentation);

        //使用Fragmentation

        SupportFragment firstFragment = findFragment(OneFragment.class);
        if (firstFragment == null) {
            mFragments[0] = new OneFragment();
            mFragments[1] = new TwoFragment();

            loadMultipleRootFragment(R.id.fl_container, 0,
                    mFragments[0],
                    mFragments[1]
                   );
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.findFragmentByTag()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[0] = firstFragment;
            mFragments[1] = findFragment(TwoFragment.class);

        }



    }

    public void onOne(View view){
        Toast.makeText(this,"one",Toast.LENGTH_SHORT).show();
        showHideFragment(mFragments[0],mFragments[1]);
    }
    public void onTwo(View view){
        Toast.makeText(this,"two",Toast.LENGTH_SHORT).show();
        showHideFragment(mFragments[1],mFragments[0]);
        //start(mFragments[1]);
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }
}
