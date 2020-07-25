# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#所有的native方法不被混淆
-keepclasseswithmembers class * {
    native <methods>;
}

#自定义View构造方法不混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context,android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context,android.util.AttributeSet,int);
}

#枚举不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#四大组件不能混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

#Design包不混淆
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

#确保JavaBean不被混淆-否则Gson将无法将数据解析成具体对象
-keep class com.jiangxk.zhengyuansmallclassroom.model.** { *; }
-keep class com.jiangxk.common.common.model.** { *; }

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}

#bugly 混淆
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#穿山甲 广告
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
-keep class com.pgl.sys.ces.* {*;}

 #混淆时是否记录日志
 -verbose
 #1.apk包内所有 class 的内部结构
 -dump class_files.txt
 #2.未混淆的类和成员
 -printseeds seeds.txt
 #3.列出从 apk 中删除的代码
 -printusage unused.txt
 #4.混淆前后的映射
 -printmapping mapping.txt
