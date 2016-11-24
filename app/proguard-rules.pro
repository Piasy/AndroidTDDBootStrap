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

-keepclassmembers,allowobfuscation class com.github.piasy.bootstrap.BootstrapApp {
    <init>(android.app.Application);
}
-keepnames class com.github.piasy.bootstrap.BootstrapApp


# RecyclerViewPager
-keep class com.lsjwzh.widget.recyclerviewpager.**
-dontwarn com.lsjwzh.widget.recyclerviewpager.**
