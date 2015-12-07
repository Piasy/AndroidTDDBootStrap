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
-dontobfuscate

# butterknife
-keepattributes *Annotation*
-keep class butterknife.** { *; }
-keep class **$$ViewInjector { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
-dontwarn butterknife.internal.**


# retrofit
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.** { *; }
-keep class com.google.inject.** { *; }
-keep class org.apache.http.** { *; }
-keep class org.apache.james.mime4j.** { *; }
-keep class javax.inject.** { *; }
-keep class retrofit.** { *; }
-dontwarn retrofit.**
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
-dontwarn com.squareup.okhttp.internal.huc.**
-dontwarn retrofit.appengine.UrlFetchClient
-dontwarn rx.**
-dontwarn okio.**
-keep class java.lang.invoke.** { *; }
-keep class java.lang.reflect.** { *; }


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


# kotlin
-dontwarn org.w3c.dom.events.**


# leak canary
-dontwarn com.squareup.leakcanary.**
