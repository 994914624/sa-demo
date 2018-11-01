package cn.sensorsdata.demo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataIgnoreTrackAppClick;
import com.sensorsdata.analytics.android.sdk.SensorsDataIgnoreTrackAppViewScreen;
import com.sensorsdata.analytics.android.sdk.SensorsDataIgnoreTrackAppViewScreenAndAppClick;
import com.sensorsdata.analytics.android.sdk.SensorsDataIgnoreTrackOnClick;

@Route(path = "/autotrack/activity")
public class AutoTrackActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private TextView textView_bg=null;
    private ImageView imageView_bg=null;
    private ScrollView scrollView_bg=null;
    private ScrollView sv_auto=null;
    private boolean isVisible=false;
    private String toggleText=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_track);
        if (Build.VERSION.SDK_INT>11)setActionBar();
        initView();
        initListView();
        //DialogFragment

    }



    /**
     * 初始化view、并设置Listener
     */
    private void initView() {

        Button button = (Button) findViewById(R.id.button_autotrack);
        TextView textView = (TextView) findViewById(R.id.textView_autotrack);
        ImageView imageView = (ImageView) findViewById(R.id.imageView_autotrack);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox_autotrack);
        RadioButton radioButton = (RadioButton) findViewById(R.id.radioButton_autotrack);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar_autotrack);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton_autotrack);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar_autotrack);
        textView_bg= (TextView) findViewById(R.id.textView_bg);
        imageView_bg= (ImageView) findViewById(R.id.imageView_bg);
        scrollView_bg= (ScrollView) findViewById(R.id.scrollView_autotrack);
        textView_bg.setOnClickListener(this);
        button.setOnClickListener(this);
        textView.setOnClickListener(this);
        imageView.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(this);
        radioButton.setOnCheckedChangeListener(this);
        toggleButton.setOnCheckedChangeListener(this);
        toggleText=toggleButton.getText()+"";
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                Toast.makeText(AutoTrackActivity.this, "" +
                        "已尝试向Sensors Analytics发送track事件\n" +
                        "事件名为:$AppClick\n" +
                        "事件属性:{ $title : 全埋点,\n" +
                        "         $screen_name : cn.sensorsdata.demo.AutoTrackActivity,\n" +
                        "         $element_type : RatingBar,\n" +
                        "         $element_id : ratingBar_autotrack\n" +
                        "         $element_content : "+ratingBar.getRating()+"\n" +
                        "         }", Toast.LENGTH_LONG).show();

            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(AutoTrackActivity.this, "" +
                        "已尝试向Sensors Analytics发送track事件\n" +
                        "事件名为:$AppClick\n" +
                        "事件属性:{ $title : 全埋点,\n" +
                        "         $screen_name : cn.sensorsdata.demo.AutoTrackActivity,\n" +
                        "         $element_type : SeekBar,\n" +
                        "         $element_id : seekBar_autotrack\n" +
                        "         $element_content : "+seekBar.getProgress()+"\n" +
                        "         }", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(AutoTrackActivity.this, "" +
                        "已尝试向Sensors Analytics发送track事件\n" +
                        "事件名为:$AppClick\n" +
                        "事件属性:{ $title : 全埋点,\n" +
                        "         $screen_name : cn.sensorsdata.demo.AutoTrackActivity,\n" +
                        "         $element_type : SeekBar,\n" +
                        "         $element_id : seekBar_autotrack\n" +
                        "         $element_content : "+seekBar.getProgress()+"\n" +
                        "         }", Toast.LENGTH_LONG).show();

            }
        });
    }


    /**
     * 点击事件
     */
    //
    // @SensorsDataIgnoreTrackOnClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_autotrack:

                Toast.makeText(this, "" +
                        "已尝试向Sensors Analytics发送track事件\n" +
                        "事件名为:$AppClick\n" +
                        "事件属性:{ $title: 全埋点,\n" +
                        "         $screen_name : cn.sensorsdata.demo.AutoTrackActivity,\n" +
                        "         $element_type : Button,\n" +
                        "         $element_id : button_autotrack\n" +
                        "         }", Toast.LENGTH_LONG).show();
                break;
            case R.id.textView_autotrack:
                Toast.makeText(this, "" +
                        "已尝试向Sensors Analytics发送track事件\n" +
                        "事件名为:$AppClick\n" +
                        "事件属性:{ $title : 全埋点," +
                        "         $screen_name : cn.sensorsdata.demo.AutoTrackActivity,\n" +
                        "         $element_type : TextView,\n" +
                        "         $element_id : textView_autotrack,\n" +
                        "         $element_content : TextView\n" +
                        "         }", Toast.LENGTH_LONG).show();
                break;
            case R.id.imageView_autotrack:
                Toast.makeText(this, "" +
                        "已尝试向Sensors Analytics发送track事件\n" +
                        "事件名为:$AppClick\n" +
                        "事件属性:{ $title : 全埋点,\n" +
                        "         $screen_name : cn.sensorsdata.demo.AutoTrackActivity,\n" +
                        "         $element_type : ImageView,\n" +
                        "         $element_id : imageView_autotrack\n" +
                        "         }", Toast.LENGTH_LONG).show();
                break;
            case R.id.textView_bg:
                if(!isVisible){
                    scrollView_bg.setVisibility(View.GONE);
                    imageView_bg.setVisibility(View.VISIBLE);
                    textView_bg.setText("关闭示例");
                    isVisible=true;
                }else {
                    imageView_bg.setVisibility(View.GONE);
                    scrollView_bg.setVisibility(View.VISIBLE);
                    textView_bg.setText("点击可查看全埋点,$AppClick事件属性，以TextView示例");
                    isVisible=false;
                    sv_auto.smoothScrollTo(0,0);
                }

                break;
            default:
                break;
        }

    }

    /**
     * onCheckedChanged事件
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.checkBox_autotrack:
                Toast.makeText(this, "" +
                        "已尝试向Sensors Analytics发送track事件\n" +
                        "事件名为:$AppClick\n" +
                        "事件属性:{ $title : 全埋点,\n" +
                        "         $screen_name : cn.sensorsdata.demo.AutoTrackActivity,\n" +
                        "         $element_type : CheckBox,\n" +
                        "         $element_id : checkBox_autotrack\n" +
                        "         $element_content : CheckBox\n" +
                        "         }", Toast.LENGTH_LONG).show();
                break;
            case R.id.radioButton_autotrack:
                Toast.makeText(this, "" +
                        "已尝试向Sensors Analytics发送track事件\n" +
                        "事件名为:$AppClick\n" +
                        "事件属性:{ $title : 全埋点,\n" +
                        "         $screen_name : cn.sensorsdata.demo.AutoTrackActivity,\n" +
                        "         $element_type : RadioButton,\n" +
                        "         $element_id : radioButton_autotrack\n" +
                        "         $element_content : RadioButton\n" +
                        "         }", Toast.LENGTH_LONG).show();
                break;
            case R.id.toggleButton_autotrack:
                Toast.makeText(this, "" +
                        "已尝试向Sensors Analytics发送track事件\n" +
                        "事件名为:$AppClick\n" +
                        "事件属性:{ $title : 全埋点,\n" +
                        "         $screen_name : cn.sensorsdata.demo.AutoTrackActivity,\n" +
                        "         $element_type : ToggleButton,\n" +
                        "         $element_id : toggleButton_autotrack\n" +
                        "         $element_content : "+toggleText+"\n" +
                        "         }", Toast.LENGTH_LONG).show();
                break;
            default:
                break;

        }
    }

    /**
     * ListView
     */
    private void initListView() {
         sv_auto = (ScrollView) findViewById(R.id.scrollView_autotrack);
        sv_auto.smoothScrollTo(0, 0);
        ListView listView= (ListView) findViewById(R.id.listView_autotrack);
        //ListView这种忽略无效
        //SensorsDataAPI.sharedInstance().ignoreView(listView);
        String [] arr={"item0","item1","item2","item3","item4","item5","item6","item7","item8","item9"};
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,arr));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AutoTrackActivity.this, "" +
                        "已尝试向Sensors Analytics发送track事件\n" +
                        "事件名为:$AppClick\n" +
                        "事件属性:{ $title : 全埋点,\n" +
                        "         $screen_name : cn.sensorsdata.demo.AutoTrackActivity,\n" +
                        "         $element_type : ListView,\n" +
                        "         $element_id : listView_autotrack\n" +
                        "         $element_position : "+position+"\n" +
                        "         }", Toast.LENGTH_LONG).show();
            }
        });

    }



    /**
     * 设置ActionBar
     */
    @TargetApi(18)
    private void setActionBar() {
        ActionBar actionBar = this.getActionBar();
        if(actionBar!=null){


        actionBar.setTitle("全埋点");
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.left_back);

        int titleId = Resources.getSystem().getIdentifier("action_bar_title",
                "id", "android");
        TextView tvTitle = (TextView) findViewById(titleId);
        int width = getResources().getDisplayMetrics().widthPixels;
        tvTitle.setWidth(width);
        tvTitle.setGravity(Gravity.CENTER);
            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AutoTrackActivity.this.finish();
                }
            });
        }
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
