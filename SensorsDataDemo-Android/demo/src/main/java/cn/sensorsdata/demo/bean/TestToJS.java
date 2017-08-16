package cn.sensorsdata.demo.bean;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by yang on 2017/6/16.
 */

public class TestToJS extends Object {
    Context context;
    public TestToJS(Context context) {
        this.context=context;
    }

    @JavascriptInterface
    public void javaMethod(String str){
        Toast.makeText(context,"js调用"+str,Toast.LENGTH_SHORT).show();
    }
}
