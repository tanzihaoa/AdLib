使用方式
implementation 'com.github.tanzihaoa:AdLib:v1.0.0'

需要加入混淆
# 倍孜混淆
-dontwarn com.beizi.fusion.**
-dontwarn com.beizi.ad.**
-keep class com.beizi.fusion.** {*; }
-keep class com.beizi.ad.** {*; }

-keep class com.qq.e.** {
public protected *;
}

-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

-dontwarn  org.apache.**

# 百度广告渠道混淆
-dontwarn com.baidu.mobads.sdk.api.**
-keepclassmembers class * extends android.app.Activity {
public void *(android.view.View);
}

-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}

-keep class com.baidu.mobads.** { *; }
-keep class com.style.widget.** {*;}
-keep class com.component.** {*;}
-keep class com.baidu.ad.magic.flute.** {*;}
-keep class com.baidu.mobstat.forbes.** {*;}

# 穿山甲广告渠道混淆
-keepclassmembers class * {
*** getContext(...);
*** getActivity(...);
*** getResources(...);
*** startActivity(...);
*** startActivityForResult(...);
*** registerReceiver(...);
*** unregisterReceiver(...);
*** query(...);
*** getType(...);
*** insert(...);
*** delete(...);
*** update(...);
*** call(...);
*** setResult(...);
*** startService(...);
*** stopService(...);
*** bindService(...);
*** unbindService(...);
*** requestPermissions(...);
*** getIdentifier(...);
}

-keep class com.bytedance.pangle.** {*;}
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep class com.bytedance.frameworks.** { *; }

-keep class ms.bd.c.Pgl.**{*;}
-keep class com.bytedance.mobsec.metasec.ml.**{*;}

-keep class com.ss.android.**{*;}

-keep class com.bytedance.embedapplog.** {*;}
-keep class com.bytedance.embed_dr.** {*;}

-keep class com.bykv.vk.** {*;}


# 快手广告渠道混淆
-keep class org.chromium.** {*;}
-keep class org.chromium.** { *; }
-keep class aegon.chrome.** { *; }
-keep class com.kwai.**{ *; }
-dontwarn com.kwai.**
-dontwarn com.kwad.**
-dontwarn com.ksad.**
-dontwarn aegon.chrome.**

# GroMore混淆
-keep class bykvm*.**
-keep class com.bytedance.msdk.adapter.**{ public *; }
-keep class com.bytedance.msdk.api.** {
public *;
}
-keep class com.bytedance.msdk.base.TTBaseAd{*;}
-keep class com.bytedance.msdk.adapter.TTAbsAdLoaderAdapter{
public *;
protected <fields>;
}

# MTG混淆
-keep class com.mbridge.** {*; }
-keep interface com.mbridge.** {*; }
-dontwarn com.mbridge.**
-keep class **.R$* { public static final int mbridge*; }

#视频播发
-keep public class cn.jzvd.JZMediaSystem {*; }
-keep public class cn.jzvd.demo.CustomMedia.CustomMedia {*; }
-keep public class cn.jzvd.demo.CustomMedia.JZMediaIjk {*; }
-keep public class cn.jzvd.demo.CustomMedia.JZMediaSystemAssertFolder {*; }

-keep class tv.danmaku.ijk.media.player.** {*; }
-dontwarn tv.danmaku.ijk.media.player.*
-keep interface tv.danmaku.ijk.media.player.** { *; }

#一键登录
-dontwarn com.cmic.sso.sdk.**
-keep class com.cmic.sso.**{*;}
-dontwarn com.sdk.**
-keep class com.sdk.** { *;}
-keep class cn.com.chinatelecom.account.**{*;}
-keep public class * extends android.view.View
-keep class com.netease.nis.quicklogin.entity.**{*;}
-keep class com.netease.nis.quicklogin.listener.**{*;}
-keep class com.netease.nis.quicklogin.QuickLogin{
public <methods>;
public <fields>;
}
-keep class com.netease.nis.quicklogin.helper.UnifyUiConfig{*;}
-keep class com.netease.nis.quicklogin.helper.UnifyUiConfig$Builder{
public <methods>;
public <fields>;
}
-keep class com.netease.nis.quicklogin.utils.LoginUiHelper$CustomViewListener{
public <methods>;
public <fields>;
}
-keep class com.netease.nis.basesdk.**{
public *;
protected *;
}

# AdLib
