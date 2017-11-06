package cn.sensorsdata.demo.yang;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sensorsdata.analytics.android.sdk.ScreenAutoTracker;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sensorsdata.demo.R;
import cn.sensorsdata.demo.bean.TestNDK;
import cn.sensorsdata.demo.bean.TestToJS;

import static android.view.KeyEvent.KEYCODE_BACK;

@Route(path = "/yangtab2/activity")
public class YangTab2Activity extends AppCompatActivity implements ScreenAutoTracker {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yang_tab2);
        ButterKnife.bind(this);

        //Log.e("####","123");

        initWebView();


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


    @OnClick(R.id.bt_bn)
    void click(View view){

        Toast.makeText(this,"bt_bn",Toast.LENGTH_SHORT).show();
    }






    /**
     * WebVeiw
     */
    private LinearLayout ll_webview_container = null;
    private WebView webView = null;

    @TargetApi(11)
    private void initWebView() {
        webView = new WebView(getApplicationContext());

        // 打通H5
//        try {
//            SensorsDataAPI.sharedInstance().showUpWebView(webView,false,new JSONObject().put("platform","Android-H5"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        ll_webview_container = (LinearLayout) findViewById(R.id.ll_webView_container);

        ll_webview_container.addView(webView);
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式



        //复写shouldOverrideUrlLoading()方法，使得 打开网页时不调用系统浏览器， 而是在本WebView中显示
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                Log.e("###",""+url);

                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //设定加载结束的操作
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                //设定加载资源的操作 每一个资源（比如图片）的加载都会调用一次。
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                //加载页面的服务器出现错误时（如404）调用,这个时候我们的app就需要加载一个本地的错误提示页面
            }
        });

        /**
         *WebChromeClient类 助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等
         */
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                    //progress.setText(progress);加载进度
                } else {
                }
            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                //titleview.setText(title); 网页的Title
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Toast.makeText(YangTab2Activity.this,"点击",Toast.LENGTH_SHORT).show();
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Uri uri = Uri.parse(message);
                Log.e("###",""+message);
                if(uri.getScheme().equals("js")){
                    Log.e("WW","W");
                    return false;
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });

        /**
         * 给JS暴露方法
         */

        webView.addJavascriptInterface(new TestToJS(this),"test");

        //webView.loadUrl("http://www.baidu.com");
        //webView.loadUrl("file:///android_asset/JS_SDK_test.html");
        webView.loadUrl("http://60.191.59.19:7070/PerMobileServerPro/weixindemo.html");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }

    // back 键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView != null) {
            if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public String getScreenUrl() {
        return "yang/tab2Activity";
    }

    @Override
    public JSONObject getTrackProperties() throws JSONException {
        return null;
    }
}
