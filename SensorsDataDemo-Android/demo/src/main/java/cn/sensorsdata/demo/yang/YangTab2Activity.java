package cn.sensorsdata.demo.yang;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sensorsdata.analytics.android.sdk.ScreenAutoTracker;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sensorsdata.demo.R;
import cn.sensorsdata.demo.bean.TestNDK;
import cn.sensorsdata.demo.bean.TestToJS;

import static android.view.KeyEvent.KEYCODE_BACK;

@Route(path = "/yangtab2/activity")
public class YangTab2Activity extends BaseActivity   {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yang_tab2);

        //Log.e("####","123");



        initTab();



    }



    private void initTab() {
        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();
        TabWidget tabWidget = tabHost.getTabWidget();

        tabHost.addTab(tabHost.newTabSpec("tab1##首页##cn.sensorsdata.demo.HomeTab")
                .setIndicator("tab1", getResources().getDrawable(R.mipmap.ic_launcher))
                .setContent(R.id.view1));

        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator("tab3")
                .setContent(R.id.view3));

        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator(TestNDK.myMethodString())
                .setContent(R.id.view2));

        /**
         *监听
         */
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                Log.e("####","1234");
                //SensorsDataAPI.sharedInstance().trackTimerEnd("testTim");
                Toast.makeText(YangTab2Activity.this, "" + s, Toast.LENGTH_SHORT).show();
                if("tab2".equals(s)){
                    SensorsDataAPI.sharedInstance().logout();
                    Toast.makeText(YangTab2Activity.this, "logout" + s, Toast.LENGTH_SHORT).show();
                }
            }
        });




        final int tabs = tabWidget.getChildCount();

        final int tabWidth = 90;
        final int tabHeight = 45;

        for (int i = 0; i < tabs; i++) {

        }
    }







}
