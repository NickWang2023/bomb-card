# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep Compose
-keep class androidx.compose.** { *; }
-keep class kotlin.Metadata { *; }

# Keep Retrofit
-keep class retrofit2.** { *; }
-keep class com.bombcard.app.data.** { *; }
-keep class com.bombcard.app.ui.** { *; }

# Keep Kotlin coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}