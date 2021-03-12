# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /usr/local/Cellar/android-sdk/23.0.2/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.kts.
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

-keep  class com.mvvm_clean.user_details.core.** { *; }

-dontwarn javax.annotation.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keepclasseswithmembers interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8

-dontwarn **
-ignorewarnings
-dontwarn io.realm.**


-keepclassmembers class rx.internal.util.unsafe.** {
    long producerIndex;
    long consumerIndex;
}

-keep class retrofit2.** { *; }
