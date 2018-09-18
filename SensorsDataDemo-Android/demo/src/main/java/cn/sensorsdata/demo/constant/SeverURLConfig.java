package cn.sensorsdata.demo.constant;

import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

/**
 * Created by yang on 2017/11/8
 */

public class SeverURLConfig {

    //我的数据接收地址
    public final static String SA_MY = "http://test2-zouyuhan.cloud.sensorsdata.cn:8006/sa?project=yangzhankun&token=386e4bed00b5701e";
    //分析师
    public final static String SA_ANALYST = "http://saasdemo.cloud.sensorsdata.cn:8006/sa?project=default&token=102672f0642fd2e9";

    //
    public final static String AAA = "https://sensors.139xy.cn:4006/sa?";
    //yang ao
    public final static String YANG_AO = "http://47.95.67.57:8106/sa?project=default";

    /**
     * Debug 模式
     */
    private  final static SensorsDataAPI.DebugMode DEBUG_ONLY = SensorsDataAPI.DebugMode.DEBUG_ONLY;
    private  final static SensorsDataAPI.DebugMode DEBUG_AND_TRACK = SensorsDataAPI.DebugMode.DEBUG_AND_TRACK;
    private  final static SensorsDataAPI.DebugMode DEBUG_OFF = SensorsDataAPI.DebugMode.DEBUG_OFF;


}
