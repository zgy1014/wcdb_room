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
 -keep class com.google.android.material.** {*;}
 -keep class androidx.** {*;}
 -keep public class * extends androidx.**
 -keep interface androidx.** {*;}
 -dontwarn com.google.android.material.**
 -dontnote com.google.android.material.**
 -dontwarn androidx.**


-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.**{*;}
-keep   class com.amap.api.trace.**{*;}
 #定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.loc.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}

 # 搜索
-keep   class com.amap.api.services.**{*;}

# 2D地图
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}

#  导航
-keep class com.amap.api.navi.**{*;}
-keep class com.autonavi.**{*;}


-keep class com.huawei.**{*;}
-keep class com.meizu.**{*;}
-keep class com.xiaomi.**{*;}
-keep class android.os.SystemProperties
-keep class com.coloros.** {*;}
-keep class com.google.** {*;}
-keep class org.apache.thrift.**{*;}

-dontwarn com.huawei.**
-dontwarn com.meizu.**
-dontwarn com.xiaomi.**
-dontwarn android.os.SystemProperties
-dontwarn com.coloros.**
-dontwarn com.google.**
-dontwarn org.apache.thrift.**

-dontwarn com.vivo.push.**
-keep class com.vivo.push.**{*; }
-keep class com.vivo.vms.**{*; }
-keep class com.huawei.**{*;}
-keep class com.meizu.**{*;}
-keep class com.xiaomi.**{*;}
-keep class android.os.SystemProperties
-keep class com.coloros.** {*;}
-keep class com.google.** {*;}
-keep class org.apache.thrift.**{*;}
-keep class com.tencent.wcdb.** {*;}


-dontwarn com.huawei.**
-dontwarn com.meizu.**
-dontwarn com.xiaomi.**
-dontwarn android.os.SystemProperties
-dontwarn com.coloros.**
-dontwarn com.google.**
-dontwarn org.apache.thrift.**


-dontwarn com.vivo.push.**
-keep class com.vivo.push.**{*; }
-keep class com.vivo.vms.**{*; }
#huawei
-ignorewarning
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep class com.hianalytics.android.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}
-keep public class * extends android.app.Service
-keep class com.heytap.msp.** { *;}
-keep class com.wcdb.tool.service.XiaoMiMessageReceiver {*;}