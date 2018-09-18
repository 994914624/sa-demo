# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/yang/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 5 # 指定代码的压缩级别
-dontusemixedcaseclassnames # 是否使用大小写混合
-dontpreverify # 混淆时是否做预校验
-verbose # 混淆时是否记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/* # 混淆时所采用的算法
-keep public class * extends android.app.Activity # 保持哪些类不被混淆
-keep public class * extends android.app.Application # 保持哪些类不被混淆
-keep public class * extends android.app.Service # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService # 保持哪些类不被混淆

-dontwarn org.json.**
-keep class org.json.** {*;}

-dontwarn com.sensorsdata.analytics.android.**
-keep class com.sensorsdata.analytics.android.** {
*;
}
-keep class **.R$* {
    <fields>;
}
-keepnames class * implements android.view.View$OnClickListener
-keep public class * extends android.content.ContentProvider
-keepnames class * extends android.view.View
-keep class * extends android.app.Fragment {
 public void setUserVisibleHint(boolean);
 public void onHiddenChanged(boolean);
 public void onResume();
 public void onPause();
}
-keep class android.support.v4.app.Fragment {
 public void setUserVisibleHint(boolean);
 public void onHiddenChanged(boolean);
 public void onResume();
 public void onPause();
}
-keep class * extends android.support.v4.app.Fragment {
 public void setUserVisibleHint(boolean);
 public void onHiddenChanged(boolean);
 public void onResume();
 public void onPause();
}
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }
-keep class cn.sensorsdata.demo.databinding.** {
    <fields>;
    <methods>;
}

#ARouter
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

-keep class com.github.mzule.activityrouter.router.** { *; }
#Fragmentation
-keep class * extends android.support.v4.app.FragmentManager{ *; }

#talkingdata
-dontwarn com.tendcloud.tenddata.**
-keep class com.tendcloud.** {*;}
-keep public class com.tendcloud.tenddata.** { public protected *;}
-keepclassmembers class com.tendcloud.tenddata.**{
public void *(***);
}
-keep class com.talkingdata.sdk.TalkingDataSDK {public *;}
-keep class com.apptalkingdata.** {*;}
-keep class dice.** {*; }
-dontwarn dice.**


-dontwarn org.greenrobot.greendao.**
-keep class org.greenrobot.greendao.**{*;}

-dontwarn com.growingio.android.sdk.**
-keep class com.growingio.android.sdk.**{*;}

-dontwarn com.alibaba.android.arouter.**
-keep class com.alibaba.android.arouter.**{*;}


-dontwarn com.igexin.assist.**
-keep class com.igexin.assist.**{*;}


-dontwarn org.jacoco.agent.**
-keep class org.jacoco.agent.**{*;}


-dontwarn com.alibaba.android.arouter.**
-keep class com.alibaba.android.arouter.**{*;}


-dontwarn com.growingio.android.sdk.**
-keep class com.growingio.android.sdk.**{*;}


-dontwarn com.igexin.**
-keep class com.igexin.**{*;}


-dontwarn com.igexin.assist.**
-keep class com.igexin.assist.**{*;}


-dontwarn org.aspectj.**
-keep class org.aspectj.**{*;}

-dontwarn com.tencent.smtt.**
-keep class om.tencent.smtt.**{*;}

#友盟推送
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn org.apache.thrift.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**
-dontwarn com.meizu.**

-keepattributes *Annotation*

-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class com.meizu.** {*;}
-keep class org.apache.thrift.** {*;}

-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}

-keep public class **.R$*{
   public static final int *;
}

-keep class com.umeng.commonsdk.** {*;}
-keep class com.umeng.error.UMError{ public ; }

-keep class com.umeng.error.UMErrorCatch{ public ; }

-keep class com.umeng.error.UMErrorDataManger{ public ; }

-keep class com.umeng.error.BatteryUtils{ public ; }

#（可选）避免Log打印输出
-assumenosideeffects class android.util.Log {
   public static *** v(...);
   public static *** d(...);
   public static *** i(...);
   public static *** w(...);
 }
