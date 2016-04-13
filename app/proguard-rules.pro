# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/piasy/tools/android-sdk/tools/proguard/proguard-android.txt
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

# app compat-v7
-keep class android.support.v7.widget.SearchView { *; }


# ButterKnife 7
-keep class butterknife.** { *; }
-keep class **$$ViewBinder { *; }
-dontwarn butterknife.internal.**
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}


# FragmentArgs
-keep class com.hannesdorfmann.fragmentargs.** { *; }


# Gson
-keepattributes Signature
-keepattributes *Annotation*
# http://stackoverflow.com/a/23365501/3077508
-keepclassmembers class **AutoParcel_** {
    private <fields>;
}


# retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions


# dagger
-keepclassmembers,allowobfuscation class * {
    @javax.inject.* *;
    @dagger.* *;
    <init>();
}
-keep class javax.inject.** { *; }
-keep class **$$ModuleAdapter
-keep class **$$InjectAdapter
-keep class **$$StaticInjection
-keep class dagger.** { *; }
-dontwarn dagger.internal.codegen.**


# eventbus
-keepclassmembers, includedescriptorclasses class ** {
    public void onEvent*(**);
}
-keepclassmembers, includedescriptorclasses class ** {
    public void onEventMainThread*(**);
}


# xlog
-keep class com.promegu.xlog.** { *; }
-dontwarn javax.lang.**
-dontwarn javax.tools.**


# stetho
-dontwarn org.apache.http.**
-keep class com.facebook.stetho.dumpapp.** { *; }
-keep class com.facebook.stetho.server.** { *; }
-dontwarn com.facebook.stetho.dumpapp.**
-dontwarn com.facebook.stetho.server.**


# leak canary
-keep class org.eclipse.mat.** { *; }
-keep class com.squareup.leakcanary.** { *; }
-dontwarn android.app.Notification


# fabric
-dontwarn com.crashlytics.android.**


# rx
-keep class rx.internal.util.unsafe.** { *; }
-dontwarn sun.misc.**


# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}


# fresco
# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn javax.annotation.**


# BugTags
-keepattributes LineNumberTable,SourceFile

-keep class com.bugtags.library.** {*;}
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient
-dontwarn com.bugtags.library.**
