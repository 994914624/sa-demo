package cn.sensorsdata.demo.yang;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
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
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import cn.jpush.android.data.c;
import cn.sensorsdata.demo.R;
import cn.sensorsdata.demo.bean.TestToJS;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * Created by yang on 2017/12/15
 */
@Route(path = "/yangwebview/activity")
public class YangWebViewActivity extends BaseActivity {


    private static final String TAG ="YangWebViewActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yang_web_view);

        initWebView();

        //getIntent().getSerializableExtra();

    }


    /**
     * WebVeiw
     */
    private LinearLayout ll_webview_container = null;
    private WebView webView = null;

    @TargetApi(11)
    private void initWebView() {

        webView = new WebView(getApplicationContext());

        //允许调试
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        //打通App和H5
        //SensorsDataAPI.sharedInstance().showUpWebView(webView,true);


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

        Log.d("yyy","getUserAgentString1:"+webSettings.getUserAgentString());
        //webSettings.setUserAgentString("qqqqqqqqq");

        //Log.d("yyy","getUserAgentString2:"+webSettings.getUserAgentString());

        new Date();

        //复写shouldOverrideUrlLoading()方法，使得 打开网页时不调用系统浏览器， 而是在本WebView中显示
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {

                //注入 JavaScript SDK 代码
                injectSensorsDataJSSDK();
                //webView.loadUrl("javascript:"+JS_CODE);
                //设定加载结束的操作
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                Log.e("###1",""+url);

                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作

                //加入js code
                //webView.loadUrl("javascript:"+JS_CODE);
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
                if(!TextUtils.isEmpty(message))
                    Toast.makeText(YangWebViewActivity.this,"11"+message,Toast.LENGTH_SHORT).show();
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Uri uri = Uri.parse(message);
                Log.e("###2",""+message);
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

        //webView.loadUrl("https://github.com/994914624/py/blob/master/web/templates/jssdk.html");
        //webView.loadUrl("file:///android_asset/JS_SDK_test.html");
        webView.loadUrl("file:///android_asset/index.html");
        //webView.loadUrl("https://rp.yypt.com/redpacket/applycard.do?no=ACT0000004201");

        Log.d("yyy","uu:"+ SensorsDataUtils.getUserAgent(this));
    }

    @Override
    @TargetApi(11)
    protected void onPause() {
        super.onPause();
        webView.onPause();
        webView.pauseTimers();
    }

    @Override
    @TargetApi(11)
    protected void onResume() {
        super.onResume();
        webView.resumeTimers();
        webView.onResume();
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



    private final static String JS_CODE = "(function(para) {\n" +
            "  var p = para.sdk_url, n = para.name, w = window, d = document, s = 'script',x = null,y = null;\n" +
            "  w['sensorsDataAnalytic201505'] = n;\n" +
            "  w[n] = w[n] || function(a) {return function() {(w[n]._q = w[n]._q || []).push([a, arguments]);}};\n" +
            "  var ifs = ['track','quick','register','registerPage','registerOnce','trackSignup', 'trackAbtest', 'setProfile','setOnceProfile','appendProfile', 'incrementProfile', 'deleteProfile', 'unsetProfile', 'identify','login','logout','trackLink','clearAllRegister','getAppStatus'];\n" +
            "  for (var i = 0; i < ifs.length; i++) {\n" +
            "    w[n][ifs[i]] = w[n].call(null, ifs[i]);\n" +
            "  }\n" +
            "  if (!w[n]._t) {\n" +
            "    x = d.createElement(s), y = d.getElementsByTagName(s)[0];\n" +
            "    x.async = 1;\n" +
            "    x.src = p;\n" +
            "    x.setAttribute('charset','UTF-8');\n" +
            "    y.parentNode.insertBefore(x, y);\n" +
            "    w[n].para = para;\n" +
            "  }\n" +
            "})({\n" +
            "  sdk_url: 'https://static.sensorsdata.cn/sdk/test/test2.js?5',\n" +
            "  heatmap_url: 'https://static.sensorsdata.cn/sdk/1.9.13/heatmap.min.js',\n" +
            "  name: 'sa',\n" +
            "  use_app_track:true,\n"+
            "  web_url: 'https://test2-zouyuhan.cloud.sensorsdata.cn/?project=yangzhankun',\n" +
            "  server_url: 'https://test2-zouyuhan.cloud.sensorsdata.cn:4006/sa?token=386e4bed00b5701e&project=yangzhankun',\n" +
            "  heatmap:{}\n" +
            "});\n" +
            "sa.quick('autoTrack');";


    /**
     * 注入方法
     */
    private String JSResponse;
    public void injectSensorsDataJSSDK() {
        new Thread(new Runnable() {
            ByteArrayOutputStream fromFile;
            InputStream in;

            @Override
            public void run() {
                URL url;
                try {
//                    url = new URL("https://static.sensorsdata.cn/sdk/test/test2.js?5");
//                    in = url.openStream();
                    in = getAssets().open("inject_js_sdk.js");

                    int dataBlock;
                    byte arr[] = new byte[1024];
                    fromFile = new ByteArrayOutputStream();
                    while ((dataBlock = in.read(arr)) != -1) {
                        fromFile.write(arr, 0, dataBlock);
                    }
                    JSResponse = fromFile.toString();
                    webView.post(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl("javascript:" + JSResponse);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                        if (fromFile != null) {
                            fromFile.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

     class TestWebView extends WebView {

        public TestWebView(Context context) {
            super(context);
        }

        @Override
        public void addJavascriptInterface(Object object, String name) {
            super.addJavascriptInterface(object, name);
        }
    }
}
