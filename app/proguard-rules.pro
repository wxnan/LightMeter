# Add project specific ProGuard rules here.
# You can control set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment following
# and specify fully qualified class name to JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep line number information, uncomment this to
# hide original source file name.
#-renamesourcefileattribute SourceFile

# Gson rules
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Keep data model classes for Gson serialization
-keep class com.example.lightmeter.data.** { *; }
-keepclassmembers class com.example.lightmeter.data.** { *; }

# Keep enum classes
-keepclassmembers enum com.example.lightmeter.data.** {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}