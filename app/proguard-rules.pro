# 以上是必须的↓↓↓↓↓↓↓
# -----------------------------------------------------------------------------
-verbose
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses

-optimizationpasses 5
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontoptimize
-ignorewarnings

-renamesourcefileattribute SourceFile
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,LocalVariable*Table,*Annotation*,Synthetic,EnclosingMethod,JavascriptInterface

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclassmembers class **.R$* {
    public static <fields>;
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep class * implements java.io.Serializable {
  public static final android.os.Parcelable$Creator *;
}

-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

-dontwarn android.support.**
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**

-keep public class * extends android.view.View
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.database.sqlite.SQLiteOpenHelper{ *; }

# 以上是必须的↑↑↑↑↑↑↑
# -----------------------------------------------------------------------------


# 以下是本程序相关↓↓↓↓↓↓↓
# -----------------------------------------------------------------------------
-keep  public class cn.common.http.JsonParse
-keep  public class * extends  cn.common.http.base.BaseResponse
-keep  public class * extends  cn.common.ui.BaseDialog
-keep  public class com.buybuyall.market.entity.**{*;}
# 以上是本程序相关↑↑↑↑↑↑↑
# -----------------------------------------------------------------------------

