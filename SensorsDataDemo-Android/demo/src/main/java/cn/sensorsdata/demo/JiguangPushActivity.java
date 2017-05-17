package cn.sensorsdata.demo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sensorsdata.analytics.android.sdk.SensorsDataTrackViewOnClick;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;


/**
 * 极光推送 Activity
 */

public class JiguangPushActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpush);
        setActionBar();
        initView();

    }




    /**
     * 初始化View
     */
    private void initView() {
        TextView textView= (TextView) findViewById(R.id.textView_JiguangPushActivity);
        //极光推送的RegistrationID
        if(TextUtils.isEmpty(JPushInterface.getRegistrationID(this))){
            textView.setText("极光推送初始化失败");

        }else {
            textView.setText(JPushInterface.getRegistrationID(this)+"");
            Log.i("getRegId_jg:",JPushInterface.getRegistrationID(this)+"");

        }
    }

    /**
     * 设置ActionBar
     */
    private void setActionBar(){
        ActionBar actionBar=getActionBar();
        actionBar.setTitle("极光推送");
        // 设置不显示左侧图标
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        int titleId = Resources.getSystem().getIdentifier("action_bar_title",
                "id", "android");
        TextView tvTitle = (TextView) findViewById(titleId);
        int width=getResources().getDisplayMetrics().widthPixels;
        tvTitle.setWidth(width);
        tvTitle.setGravity(Gravity.CENTER);
    }

    /**
     * back键
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
