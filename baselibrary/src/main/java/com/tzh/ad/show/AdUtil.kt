package com.tzh.ad.show

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.bytedance.sdk.openadsdk.AdSlot
import com.bytedance.sdk.openadsdk.CSJAdError
import com.bytedance.sdk.openadsdk.CSJSplashAd
import com.bytedance.sdk.openadsdk.TTAdConfig
import com.bytedance.sdk.openadsdk.TTAdConstant
import com.bytedance.sdk.openadsdk.TTAdNative
import com.bytedance.sdk.openadsdk.TTAdNative.CSJSplashAdListener
import com.bytedance.sdk.openadsdk.TTAdSdk
import com.bytedance.sdk.openadsdk.TTCustomController
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd
import com.bytedance.sdk.openadsdk.TTRewardVideoAd
import com.tzh.ad.util.toDefault


object AdUtil {

    fun init(context: Context, appId : String, listener: InitListener?= null, controller: TTCustomController ?= null){
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
    fun showSpreadAd(splashId : String,view : FrameLayout, listener : MyAdListener, isGone : Boolean = false){
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
    fun showRewardedVideoAd(activity : Activity, rewardId : String, listener : MyRewardedAdListener, isGone : Boolean = false){
        if(isGone){
            listener.close()
            return
        }
        val adNativeLoader = TTAdSdk.getAdManager().createAdNative(activity)
        val adSlot: AdSlot = AdSlot.Builder()
            .setCodeId(rewardId)
            .setOrientation(TTAdConstant.VERTICAL) //横竖屏设置
            .build()
        adNativeLoader.loadRewardVideoAd(adSlot,object : TTAdNative.RewardVideoAdListener{
            override fun onError(i: Int, s: String?) {
                Log.e("Video=======","onError==${i}==${s}")
                listener.onError(s.toDefault("错误为空"))
            }

            override fun onRewardVideoAdLoad(ttRewardVideoAd: TTRewardVideoAd?) {
                Log.e("Video=======","onRewardVideoAdLoad")

            }

            override fun onRewardVideoCached() {

            }

            override fun onRewardVideoCached(ttRewardVideoAd: TTRewardVideoAd?) {
                Log.e("Video=======","onRewardVideoCached")
                if(ttRewardVideoAd!=null){
                    showRewarded(activity,ttRewardVideoAd,listener)
                }else{
                    listener.onError("广告为空")
                }

            }
        })
    }

    private fun showRewarded(activity : Activity,ttRewardVideoAd: TTRewardVideoAd,listener : MyRewardedAdListener){
        ttRewardVideoAd.setRewardAdInteractionListener(object : TTRewardVideoAd.RewardAdInteractionListener{
            override fun onAdShow() {
                Log.e("Video=======","onAdShow")
                //广告展示
                listener.loaded()
            }

            override fun onAdVideoBarClick() {
                Log.e("Video=======","onAdVideoBarClick")
                //广告的下载bar点击回调

            }

            override fun onAdClose() {
                Log.e("Video=======","onAdClose")
                //广告关闭的回调

            }

            override fun onVideoComplete() {
                Log.e("Video=======","onVideoComplete")
                //视频播放完毕的回调

            }

            override fun onVideoError() {
                Log.e("Video=======","onVideoError")
                //视频播放失败的回调
                listener.onError("播放失败")
            }

            override fun onRewardVerify(p0: Boolean, p1: Int, p2: String?, p3: Int, p4: String?) {

            }

            override fun onRewardArrived(isRewardValid : Boolean, rewardType: Int, extraInfo : Bundle?) {
                Log.e("Video=======","onRewardArrived")
                //激励视频播放完毕，验证是否有效发放奖励的回调
                //isRewardValid ：是否发放奖励，true：发奖励；false：不发奖励
                //rewardType：奖励类型，0:基础奖励 >0:进阶奖励
                //extraInfo：奖励的额外参数
                if(isRewardValid){
                    //获取到了奖励
                    listener.onRewardArrived()
                }else{
                    listener.close()
                }
            }

            override fun onSkippedVideo() {

            }
        })
        ttRewardVideoAd.showRewardVideoAd(activity)
    }

    /**
     * 显示插屏广告
     */
    fun showFullScreenVideoAd(activity : Activity, codeId : String, isGone : Boolean = false){
        if(isGone){
            return
        }
        val adNativeLoader = TTAdSdk.getAdManager().createAdNative(activity)
        val adSlot: AdSlot = AdSlot.Builder()
            .setCodeId(codeId)
            .setOrientation(TTAdConstant.ORIENTATION_VERTICAL) //设置横竖屏方向
            .build()
        adNativeLoader.loadFullScreenVideoAd(adSlot,object : TTAdNative.FullScreenVideoAdListener{
            override fun onError(code: Int, message: String?) {
                Log.e("screenAd=======","onError==${code}==${message}")
            }

            override fun onFullScreenVideoAdLoad(ad: TTFullScreenVideoAd?) {
                Log.e("screenAd=======","onFullScreenVideoAdLoad")
            }

            override fun onFullScreenVideoCached() {
                Log.e("screenAd=======","onFullScreenVideoCached---0")

            }

            override fun onFullScreenVideoCached(ad: TTFullScreenVideoAd?) {
                Log.e("screenAd=======","onFullScreenVideoCached")
                ad?.showFullScreenVideoAd(activity)
            }
        })
    }

    interface MyAdListener{
        fun loaded()

        fun close()

        fun onAdTick(millisUnitFinished : Long)

        fun onError(csJAdError: CSJAdError?)
    }

    interface MyRewardedAdListener{
        fun loaded()

        fun close()

        fun onRewardArrived()

        fun onError(s : String)
    }

    /**
     * 初始化监听
     */
    interface InitListener{
        fun success()

        fun fail()
    }
}