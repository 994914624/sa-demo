package cn.sensorsdata.demo.test;

import android.content.Context;
import android.webkit.WebView;

import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yang on 2017/10/24
 */

public class SensorsData {


    /**
     * 初始化神策SDK
     *
     * @param context      App 的 Context
     * @param serverURL    用于收集事件的服务地址
     * @param configureUrl 用于获取SDK配置的服务地址
     * @param debugMode    Debug模式 (调试用 DEBUGANDTRACK 模式，线上发版时用 DEBUGOFF 模式)
     */
    public static void init(Context context, String serverURL, String configureUrl, SensorsDataAPI.DebugMode debugMode){
        //SensorsDataAPI.sharedInstance(context,serverURL,configureUrl,debugMode);
    }

    /**
     * login 方法 用于用户关联，在注册成功、登录成功、初始化SDK后(if能获取用户ID)时调用
     *
     * @param loginId 当前用户的 loginId，不能为空，且长度不能大于255
     */
    public static void login(String loginId) {
        SensorsDataAPI.sharedInstance().login(loginId);
    }

    /**
     * registerSuperProperties 方法 用于注册所有事件都有的公共属性
     *
     * @param superProperties 事件公共属性
     */
    public static void registerSuperProperties(JSONObject superProperties) {
        //SensorsDataAPI.sharedInstance().registerSuperProperties(superProperties);
    }

    /**
     * trackInstallation 方法 用于记录激活事件AppInstall，并设置追踪渠道事件的属性。
     *
     * @param eventName  渠道追踪事件的名称
     * @param properties 渠道追踪事件的属性
     */
    public static void trackInstallation(String eventName,JSONObject properties) {
       // SensorsDataAPI.sharedInstance().trackInstallation(eventName,properties);
    }

    /**
     * enableAutoTrack 方法 用于开启SDK 自动采集$AppStart、$AppEnd 事件
     *
     */
    public static void enableAutoTrack() {
        List<SensorsDataAPI.AutoTrackEventType> eventTypeList = new ArrayList<>();
        eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_START);
        eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_END);
        //eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_CLICK);
        eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_VIEW_SCREEN);
        //SensorsDataAPI.sharedInstance().enableAutoTrack(eventTypeList);
    }

    /**
     * track 方法 用于自定义埋点
     *
     * @param eventName  事件名称
     * @param properties 事件的具体属性
     *
     */
    public static void track(String eventName, JSONObject properties) {
       // SensorsDataAPI.sharedInstance().track(eventName, properties);
    }

    /**
     * profileSet 方法 用于设置用户属性
     *
     * @param properties 属性列表
     */
    public static void profileSet(JSONObject properties) {
       // SensorsDataAPI.sharedInstance().profileSet(properties);
    }

    /**
     * profileSet 方法 用于设置用户的首次属性
     *
     * @param properties 属性列表
     */
    public static void profileSetOnce(JSONObject properties) {
       // SensorsDataAPI.sharedInstance().profileSetOnce(properties);
    }

    /**
     * showUpWebView 方法 用户App和 H5的打通，打通后使用同的用户标识ID。
     *
     * @param webView 当前WebView实例对象
     * @param isSupportJellyBean 是否支持API level 16及以下的版本。
     * 因为API level 16及以下的版本, addJavascriptInterface有安全漏洞. true 或 fale 由你们自己决定。
     */
    public static void showUpWebView(WebView webView, boolean isSupportJellyBean) {
        //SensorsDataAPI.sharedInstance().showUpWebView(webView,isSupportJellyBean);
    }



    //-------------------下边是需要在 Application 的 onCreate 方法中调用的代码示例---------------------


    /**
     * Sensors Analytics 采集数据的地址
     */
    private final static String SA_SERVER_URL = "具体的数据接收地址";
    /**
     * Sensors Analytics 配置分发的地址
     */
    private final static String SA_CONFIGURE_URL = "具体的配置分发地址";
    /**
     * Sensors Analytics DEBUG 模式
     * SensorsDataAPI.DebugMode.DEBUG_OFF - 关闭 Debug 模式
     * SensorsDataAPI.DebugMode.DEBUG_ONLY - 打开 Debug 模式，校验数据，但不进行数据导入
     * SensorsDataAPI.DebugMode.DEBUG_AND_TRACK - 打开 Debug 模式，校验数据，并将数据导入到 Sensors Analytics 中
     * 注意！请不要在正式发布的 App 中使用 Debug 模式！
     */
    private final SensorsDataAPI.DebugMode SA_DEBUG_MODE = SensorsDataAPI.DebugMode.DEBUG_AND_TRACK;

    /**
     * 在 Application的 onCreate 方法中调用 initSensors
     */
    private void initSensors(Context context) {

        // 1 初始化神策SDK
        SensorsData.init(context,                               // 传入 Context
                SA_SERVER_URL,                              // 数据接收的 URL
                SA_CONFIGURE_URL,                           // 配置分发的 URL
                SA_DEBUG_MODE);                             // debug模式

        // 2 if如果能取到用户ID，调用 login 传入用户ID,并设置用户属性
        //if(){
            //调用 login 传入用户ID
            SensorsData.login("获取到的用户ID");
            // 设置用户属性(在注册成功服务端返回成功时埋点)
            try {
                JSONObject properties =new JSONObject();
                properties.put("member_id","XXX")//用户的memberID
                        .put("phone_number","XXX");//手机号
                SensorsData.profileSet(properties);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        //}

        // 3 设置公共属性
        try {
            JSONObject properties =new JSONObject();
            properties.put("app_store","具体的商店渠道包的值")//例如：应用宝、小米商店
                    .put("platform","Android")
                    .put("location_city","具体的定位城市");//需要从定位中获取此值(如果用户第一使用App，还没有申请到定位权限时，如何传值)
            SensorsData.registerSuperProperties(properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 4 记录AppInstall激活事件
        SensorsData.trackInstallation("AppInstall",null);;

        // 5 开启 SDK 自动采集App启动、App退出事件
        SensorsData.enableAutoTrack();
    }

    //-------------------下边是用户关联代码示例-------------------------


    private void code1(){
        //在注册成功服务端返回成功)、登录成功(服务端返回成功)时调用login 传入用户ID。
        SensorsData.login("获取到的用户ID");
    }

    //-------------------下边是设置用户属性示例-------------------------

    private void code2(){

        // 设置用户属性(在注册成功服务端返回成功时埋点)
        try {
            JSONObject properties =new JSONObject();
            properties.put("member_id","XXX")//用户的memberID
                        .put("phone_number","XXX")//手机号
                        .put("sign_up_time",new Date());//注册时间
            SensorsData.profileSet(properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //-------------------下边是具体的事件埋点代码示例---------------------

    private void code3(){

        // 埋点 AppViewScreen 事件（）
        try {
            JSONObject properties =new JSONObject();
            properties.put("$screen_name","XXX");//具体的页面标题（例如：首页、目的地、发现、签证大厅、我的）
//                    .put("destination_city","XXX")//只有搜索结果页需要（例如：北京、日本）
//                    .put("product_id",123456)//只有详情页需要（数值类型；）
//                    .put("product_name","XXX")//只有详情页需要（上边黑色标题）
//                    .put("product_type","XXX");//只有搜索结果页、产品详情页需要（例如：全部、跟团游、酒店+、）
            SensorsData.track("$AppViewScreen",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 埋点 ViewSearchListPage 事件 (浏览搜索结果页)
        try {
            JSONObject properties =new JSONObject();
            properties.put("destination_city","XXX");//目的城市
            SensorsData.track("ViewSearchListPage",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 埋点 Search 事件 (此处有两个动作，一个是点击搜索框，一个是点击搜索框之后，用户自行输入或者是选择推荐的热门关键词进行搜索)
        try {
            JSONObject properties =new JSONObject();
            properties.put("$screen_name","XXX")//具体的页面标题
                    .put("search_action","XXX")//点击搜索框、输入或者选择关键词进行搜索(例如：点击搜索框、输入搜索、热门搜索、搜索历史)
                    .put("search_key_word","XXX");//用户在搜索框输入的搜索词、点击的关键词(没有时，传字符串“无”)
            SensorsData.track("Search",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 埋点 SignUpSuccess 事件 (在点击“提交”时埋点)
        try {
            JSONObject properties =new JSONObject();
            properties.put("phone_number","XXX");//电话号码
            SensorsData.track("SignUpSuccess",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 埋点 LoginSuccess 事件 (在点击“登录”时埋点)
        try {
            JSONObject properties =new JSONObject();
            properties.put("login_type","XXX");//（手机号、邮箱、会员卡号）
            SensorsData.track("LoginSuccess",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 埋点 SubmitOrder 事件 (成功跳转到支付页时埋点)
        try {
            JSONObject properties =new JSONObject();
            properties.put("member_id","XXX")//会员id
                    .put("order_id","XXX")//定单id
                    .put("order_amount",100)//订单金额（数值类型）
                    .put("discount_name","XXX")//优惠名称（多个优惠时用|分割）
                    .put("discount_amount",100)//优惠金额（数值类型）
                    .put("point_amount",100)//积分数量（数值类型）
                    .put("product_id",100)//产品id（数值类型）
                    .put("product_name","XXX")//产品名称
                    .put("product_type","XXX");//产品类型
            SensorsData.track("SubmitOrder",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 埋点 SubmitPay 事件 (服务端返回支付成功时埋点)
        try {
            JSONObject properties =new JSONObject();
            properties.put("member_id","XXX")//会员id
                    .put("order_id","XXX")//定单id
                    .put("order_amount",100)//订单金额（数值类型）
                    .put("discount_name","XXX")//优惠名称（多个优惠时用|分割）
                    .put("discount_amount",100)//优惠金额（数值类型）
                    .put("point_amount",100)//积分数量（数值类型）
                    .put("product_id",100)//产品id（数值类型）
                    .put("product_name","XXX")//产品名称
                    .put("product_type","XXX");//产品类型
            SensorsData.track("SubmitPay",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }










        // 埋点 Share 事件 (分享时埋点)
        try {
            JSONObject properties =new JSONObject();
            properties.put("$screen_name","XXX")//具体的页面标题
                    .put("screen_type","XXX")//分享页面类型（活动页、产品详情页、目的页；看具体能取到的值）
                    .put("share_type","XXX")//分享类型（普通分享、截屏分享）
                    .put("share_position","XXX")//分享页面类型（微信、微博）
                    .put("product_id",123456)//产品id（数值类；如果是在商品详情页点击的需要有产品id）
                    .put("product_name","XXX")//产品名称(如果是在商品详情页点击的需要有产品名称）
                    .put("product_type","XXX");//产品类型(如果是在商品详情页点击的需要有产品类型）
            SensorsData.track("Share",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 埋点 Phone 事件 (客服电话时埋点)
        try {
            JSONObject properties =new JSONObject();
            properties.put("$screen_name","XXX")//具体的页面标题
                    .put("phone_type","XXX")//电话类型（客服热线、在线客服、遨游门店）
                    .put("product_id",123456)//产品id（数值类；如果是在商品详情页点击的需要有产品id）
                    .put("product_name","XXX")//产品名称(如果是在商品详情页点击的需要有产品名称）
                    .put("product_type","XXX");//产品类型(如果是在商品详情页点击的需要有产品类型）
            SensorsData.track("Phone",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 埋点 MessageClick 事件 (点击消息按钮，和最里边的具体消息列表时触发时埋点)
        try {
            JSONObject properties =new JSONObject();
            properties.put("$screen_name","XXX");//具体的页面标题
            SensorsData.track("MessageClick",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 埋点 HomePageClick 事件 (首页点击埋点)
        try {
            JSONObject properties =new JSONObject();
            properties.put("block_name","XXX")//模块名称（首页banner、首页频道、首页发现头条、首页发现头条文章、首页王牌签证、首页周周爆款、首页热门目的地、首页特色主题（旅行.生活）、首页旅友圈、首页产品推荐位置、遨游6大旅游保障）
                    .put("click_content","XXX")//点击内容
                    .put("click_position","XXX")//点击位置（轮播1、轮播2、）
                    .put("start_city","XXX")//出发城市
                    .put("destination_city","XXX")//目的城市
                    .put("recommend_column_name","XXX")//产品推荐栏目名称（特价爆款、为你系列、设计师说、嗨玩周末）
                    .put("product_id",123456)//产品id（数值类型）
                    .put("product_name","XXX")//产品名称
                    .put("product_type","XXX");//产品类型
            SensorsData.track("HomePageClick",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 埋点 AppSearchListPageClick 事件 (搜索结果页点击埋点，比如搜索了"日本"到达的页面)
        try {
            JSONObject properties =new JSONObject();
            properties.put("block_name","XXX")//模块名称（全部、自由行、跟团游、半自助、游轮、签证、酒店+、当地玩乐、门票）
                    .put("block_name_next","XXX")//二级模块名称（爆款热卖、红叶狩秋味、……）
                    .put("click_content","XXX")//点击内容
                    .put("start_city","XXX")//出发城市（点击筛选按钮时，如果选了出发城市，为选中的城市）
                    .put("destination_city","XXX")//目的城市
                    .put("click_button_site","XXX")//搜索筛选条件分类（有多个筛选条件时用|分割，例如：圣诞节|5天）
                    .put("product_id",123456)//产品id（数值类型）
                    .put("product_name","XXX")//产品名称
                    .put("product_type","XXX");//产品类型
            SensorsData.track("AppSearchListPageClick",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 埋点 OpenBannerClick 事件 (闪屏页广告点击埋点)
        try {
            JSONObject properties =new JSONObject();
            properties.put("click_content","XXX");//点击内容（广告的名称）
            SensorsData.track("OpenBannerClick",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 埋点 DestinationListPageClick 事件 (目的地页点击埋点)
        try {
            JSONObject properties =new JSONObject();
            properties.put("click_content","XXX");//点击内容（广告的名称）
            SensorsData.track("DestinationListPageClick",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 埋点 AppVisaPageClick 事件 (签证大厅点击埋点)
        try {
            JSONObject properties =new JSONObject();
            properties.put("block_name","XXX")//模块名称（签证大厅banner、签证搜索、热门签证、本周特惠、签证页频道链接）
                    .put("click_content","XXX")//点击内容
                    .put("click_position","XXX");//点击位置（轮播1、轮播2….）
            SensorsData.track("AppVisaPageClick",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 埋点 FindPageClick 事件 (发现页点击埋点)
        try {
            JSONObject properties =new JSONObject();
            properties.put("block_name","XXX")//模块名称（精彩、旅行家、发现搜索、底部栏目）
                    .put("click_content","XXX")//点击内容
                    .put("search_content","XXX");//搜索内容（点击搜索框时才有此字段）
            SensorsData.track("FindPageClick",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 埋点 AppFlightPageClick 事件 (机票频道页点击埋点)
        try {
            JSONObject properties =new JSONObject();
            properties.put("block_name","XXX")//模块名称（机票banner、机票搜索、公告、机票特价甩卖）
                    .put("click_content","XXX")//点击内容
                    .put("form_content","XXX")//表单内容(表中具体详细内容)
                    .put("search_type","XXX")//搜索类型（单程、往返、多程）
                    .put("click_position","XXX");//点击位置（轮播1、轮播2….）
            SensorsData.track("AppFlightPageClick",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 埋点 MyPageClick 事件 (我的页点击埋点)
        try {
            JSONObject properties =new JSONObject();
            properties.put("click_content","XXX");//点击内容
            SensorsData.track("MyPageClick",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 埋点 AppRouteChannelClick 事件 (线路频道页包括：自由行、跟团游、半自助、出境游、国内游、游轮。埋点这些具体页面上的点击事件)
        try {
            JSONObject properties =new JSONObject();
            properties.put("$screen_name","XXX")//具体的页面标题（自由行、跟团游、半自助、出境游、国内游、游轮）
                    .put("block_name","XXX")//模块名称（线路频道banner、线路频道搜索、线路频道返回按钮、线路频道轮播产品推荐、线路频道关键词组推荐、线路频道icon点击、线路频道中部产品推荐、线路标签产品推荐）
                    .put("click_content","XXX")//点击内容
                    .put("click_position","XXX")//点击位置（轮播1、轮播2….）
                    .put("recommend_column_name","XXX")//推荐栏目名称（例如 自由行页面：海岛度假、畅游日本、欧美澳新、亚洲精选）
                    .put("product_id",123456)//产品id（数值类型）
                    .put("product_name","XXX")//产品名称
                    .put("product_type","XXX");//产品类型
            SensorsData.track("AppRouteChannelClick",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 埋点 AppSingleChannelClick 事件 (单项频道页包括：出境wifi、酒店、当地玩乐。埋点这些具体页面上的点击事件)
        try {
            JSONObject properties =new JSONObject();
            properties.put("$screen_name","XXX")//具体的页面标题（出境wifi、酒店、当地玩乐）
                    .put("block_name","XXX")//模块名称（单项频道banner、单项频道搜索、单项频道返回按钮、单项频道关键词组推荐、单项频道icon点击、单项频道中部产品推荐、单项标签产品推荐）
                    .put("click_content","XXX")//点击内容
                    .put("click_position","XXX")//点击位置（轮播1、轮播2….）
                    .put("recommend_column_name","XXX")//推荐栏目名称（例如 出境wifi页：东南亚、港澳台日、欧美澳新、优享海岛……）
                    .put("product_id",123456)//产品id（数值类型）
                    .put("product_name","XXX")//产品名称
                    .put("product_type","XXX");//产品类型
            SensorsData.track("AppSingleChannelClick",properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
