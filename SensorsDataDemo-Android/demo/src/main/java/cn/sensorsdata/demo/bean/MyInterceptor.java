package cn.sensorsdata.demo.bean;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;


/**
 * Created by yang on 2017/7/13
 */
// 比较经典的应用就是在跳转过程中处理登陆事件，这样就不需要在目标页重复做登陆检查
// 拦截器会在跳转之间执行，多个拦截器会按优先级顺序依次执行
@Interceptor(priority = 8, name = "测试拦截器")
public class MyInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {

        Log.e("MyInterceptor",postcard.getUri()+"");
        callback.onContinue(postcard);  // 处理完成，交还控制权
        // callback.onInterrupt(new RuntimeException("我觉得有点异常"));      // 觉得有问题，中断路由流程

        // 以上两种至少需要调用其中一种，否则不会继续路由
//        if (postcard.getExtra() == CommonStation.CHECK_LOADING && !CommonStation.checkLoading()) {
//            callback.onInterrupt(new RuntimeException("账号未登录"));
//        } else {
//            callback.onContinue(postcard);
//        }
    }

    @Override
    public void init(Context context) {
        Log.e("ARouter","init");
        // 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
    }
}
