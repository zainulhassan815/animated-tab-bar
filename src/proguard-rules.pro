# Add any ProGuard configurations specific to this
# extension here.

-keep public class com.dreamers.bottombar.BottomBar {
    public *;
 }
-keeppackagenames gnu.kawa**, gnu.expr**

-optimizationpasses 4
-allowaccessmodification
-mergeinterfacesaggressively

-repackageclasses 'com/dreamers/bottombar/repack'
-flattenpackagehierarchy
-dontpreverify

-dontwarn androidx.recyclerview.widget.**
-dontwarn com.google.android.flexbox.**

-keepnames class com.dreamers.bottombar.library**