package cn.sensorsdata.demo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.igexin.sdk.PushManager;
import com.xiaomi.mipush.sdk.MiPushClient;

/**
 * 个推 Activity
 */
@Route(path = "/getui/activity")
public class GeTuiActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ge_tui);
        if(Build.VERSION.SDK_INT>11)setActionBar();
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        TextView textView = (TextView) findViewById(R.id.textView_GeTuiActivity);
        //个推送的RegistrationID
        if(TextUtils.isEmpty(PushManager.getInstance().getClientid(this))){
            textView.setText("个推送初始化失败");

        }else {
            textView.setText(PushManager.getInstance().getClientid(this) + "");


        }
    }

    /**
     * 设置ActionBar
     */
    @TargetApi(11)
    private void setActionBar(){
        ActionBar actionBar=getActionBar();
        actionBar.setTitle("个推");
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
