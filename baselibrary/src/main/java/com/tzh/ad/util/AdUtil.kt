package com.tzh.ad.util

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.bytedance.sdk.openadsdk.AdSlot
import com.bytedance.sdk.openadsdk.CSJAdError
import com.bytedance.sdk.openadsdk.CSJSplashAd
import com.bytedance.sdk.openadsdk.TTAdConfig
import com.bytedance.sdk.openadsdk.TTAdConstant
import com.bytedance.sdk.openadsdk.TTAdNative.CSJSplashAdListener
import com.bytedance.sdk.openadsdk.TTAdSdk
import com.bytedance.sdk.openadsdk.TTCustomController


object AdUtil {

    fun init(context: Context,appId : String,listener: InitListener ?= null,controller: TTCustomController ?= null){
        //广告初始化
        TTAdSdk.init(context,TTAdConfig.Builder()
            .appId(appId)
            .supportMultiProcess(true)
            .allowShowNotify(true) //是否允许sdk展示通知栏提示,若设置为false则会导致通知栏不显示下载进度
            .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI)
            .supportMultiProcess(true)
            .customController(controller)
            .build()
        )
        TTAdSdk.start(object : TTAdSdk.Callback{
            override fun success() {
                listener?.success()
                Log.e("=======","广告初始化成功")
            }

            override fun fail(code: Int, msg: String?) {
                listener?.fail()
                Log.e("=======","广告初始化成功错误===${code}====${msg}")
            }
        })
    }

    /**
     * 显示开屏广告
     */
    fun showSpreadAd(splashId : String,appId : String,appKey : String,view : FrameLayout,listener : MyAdListener,isGone : Boolean = false){
        if(isGone){
            listener.close()
            return
        }

        //第二步、创建AdSlot
        val adSlot = AdSlot.Builder()
            .setCodeId(splashId)
            .setImageAcceptedSize(view.width, view.height)

            .build()

        // 第三步，请求广告
        val adNativeLoader = TTAdSdk.getAdManager().createAdNative(view.context)


        val mCSJSplashInteractionListener = object : CSJSplashAd.SplashAdListener{
            override fun onSplashAdShow(csJSplashAd: CSJSplashAd?) {

            }

            override fun onSplashAdClick(csJSplashAd: CSJSplashAd?) {

            }

            override fun onSplashAdClose(csJSplashAd: CSJSplashAd?, i: Int) {
                Log.e("=======","广告关闭")
                listener.close()
            }
        }

        adNativeLoader.loadSplashAd(adSlot, object : CSJSplashAdListener{
            override fun onSplashLoadSuccess(csjSplashAd: CSJSplashAd?) {

            }

            override fun onSplashLoadFail(csjAdError : CSJAdError?) {
                Log.e("=======","广告加载失败===${csjAdError?.msg}====${csjAdError?.code}")
                listener.onError(csjAdError)
            }

            override fun onSplashRenderSuccess(csJSplashAd: CSJSplashAd?) {
                Log.e("=======","广告加载成功")
                listener.loaded()
                csJSplashAd?.setSplashAdListener(mCSJSplashInteractionListener)
                val splashView: View? = csJSplashAd?.splashView
                view.removeAllViews()
                view.addView(splashView)
            }

            override fun onSplashRenderFail(csJSplashAd: CSJSplashAd?, csJAdError: CSJAdError?) {
                Log.e("=======","广告加载失败===${csJAdError?.msg}====${csJAdError?.code}")
                listener.onError(csJAdError)
            }
        }, 3500)
    }

    /**
     * 显示Banner广告
     */
    fun showBannerAd(splashId : String,view : FrameLayout,isGone : Boolean = false){

    }

    /**
     * 显示激励视频
     */
    fun showRewardedVideoAd(activity : Activity,splashId : String,listener : MyAdListener,isGone : Boolean = false){


    }

    interface MyAdListener{
        fun loaded()

        fun close()

        fun onAdTick(millisUnitFinished : Long)

        fun onError(csJAdError: CSJAdError?)
    }

    /**
     * 初始化监听
     */
    interface InitListener{
        fun success()

        fun fail()
    }
}