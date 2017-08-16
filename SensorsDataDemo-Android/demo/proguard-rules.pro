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



-dontwarn com.sensorsdata.analytics.android.sdk.**
-keep class com.sensorsdata.analytics.android.sdk.** {
*;
}
-keep class **.R$* {
    <fields>;
}
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