package cn.sensorsdata.demo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

/**
 * 可视化埋点 Activity
 */
@Route(path = "/vt/activity")
public class VTActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private  TextView textView_vt_bg =null;
    private ImageView imageView_vt_bg=null;
    private LinearLayout linearLayout_vt_bg=null;
    private boolean isVisible=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vt);
        if(Build.VERSION.SDK_INT>11)setActionBar();
        initView();
        openVTtack();
        //由于此页面演示可视化埋点，所以忽略此页面的全埋点采集
       SensorsDataAPI.sharedInstance(this).ignoreAutoTrackActivity(VTActivity.class);
    }

    /**
     * 初始化View
     */
    private void initView() {

        ImageView imageView_vt= (ImageView) findViewById(R.id.imageView_vt);
        TextView textView_vt= (TextView) findViewById(R.id.textView_vt);
        Button button_vt= (Button) findViewById(R.id.button_vt);
        CheckBox checkBox_vt= (CheckBox) findViewById(R.id.checkBox_vt);
        RadioButton radioButton_vt= (RadioButton) findViewById(R.id.radioButton_vt);
        ToggleButton toggleButton_vt= (ToggleButton) findViewById(R.id.toggleButton_vt);
        EditText editText_vt= (EditText) findViewById(R.id.editText_vt);
        imageView_vt.setOnClickListener(this);
        textView_vt.setOnClickListener(this);
        button_vt.setOnClickListener(this);
        checkBox_vt.setOnCheckedChangeListener(this);
        radioButton_vt.setOnCheckedChangeListener(this);
        toggleButton_vt.setOnCheckedChangeListener(this);
        editText_vt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Toast.makeText(VTActivity.this, "" +
                        "如果你已在网页上埋点并部署成功" +
                        "点击后，会尝试向Sensors Analytics发送track事件\n" , Toast.LENGTH_LONG).show();
            }
        });

        //展示属性图片所需
        textView_vt_bg= (TextView) findViewById(R.id.textView_vt_bg);
        imageView_vt_bg= (ImageView) findViewById(R.id.imageView_vt_bg);
        linearLayout_vt_bg= (LinearLayout) findViewById(R.id.linearLayout_vt_bg);
        textView_vt_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isVisible){
                    linearLayout_vt_bg.setVisibility(View.GONE);
                    imageView_vt_bg.setVisibility(View.VISIBLE);
                    textView_vt_bg.setText("关闭示例");
                    isVisible=true;
                }else {
                    imageView_vt_bg.setVisibility(View.GONE);
                    linearLayout_vt_bg.setVisibility(View.VISIBLE);
                    textView_vt_bg.setText("点击可查看可视化全埋点事件属性，以TextView示例");
                    isVisible=false;
                }
            }
        });
    }

    /**
     * 打开可视化
     */
    private void openVTtack() {
       // SensorsDataAPI.sharedInstance(this).enableEditingVTrack();
    }

    /**
     * onClick
     */
    @Override
    public void onClick(View v) {
        Toast.makeText(VTActivity.this, "" +
                "如果你已在网页上埋点并部署成功" +
                "点击后，会尝试向Sensors Analytics发送track事件\n" , Toast.LENGTH_LONG).show();
    }

    /**
     * onCheckedChanged
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(VTActivity.this, "" +
                "如果你已在网页上埋点并部署成功" +
                "点击后，会尝试向Sensors Analytics发送track事件\n" , Toast.LENGTH_LONG).show();
    }

    /**
     * 设置ActionBar
     */
    @TargetApi(11)
    private void setActionBar(){
        ActionBar actionBar=getActionBar();
        actionBar.setTitle("可视化埋点");
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
