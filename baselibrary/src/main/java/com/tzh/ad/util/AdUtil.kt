package com.tzh.ad.util

import android.app.Activity
import android.content.Context
import android.widget.FrameLayout
import com.beizi.fusion.AdListener
import com.beizi.fusion.BannerAd
import com.beizi.fusion.BannerAdListener
import com.beizi.fusion.BeiZiCustomController
import com.beizi.fusion.BeiZis
import com.beizi.fusion.RewardedVideoAd
import com.beizi.fusion.RewardedVideoAdListener
import com.beizi.fusion.SplashAd

object AdUtil {

    fun init(context: Context,appId : String){
        //广告初始化
        BeiZis.init(context,appId,object : BeiZiCustomController() {
            /**
             * 是否允许SDK主动使用地理位置信息
             *
             * @return true可以获取，false禁止获取。默认为true
             */
            override fun isCanUseLocation(): Boolean {
                return true
            }

            /**
             * 是否允许SDK主动使用ACCESS_WIFI_STATE权限
             *
             * @return true可以使用，false禁止使用。默认为true
             */
            override fun isCanUseWifiState(): Boolean {
                return true
            }

            /**
             * 是否允许SDK主动使用手机硬件参数，如：imei，imsi
             *
             * @return true可以使用，false禁止使用。默认为true
             */
            override fun isCanUsePhoneState(): Boolean {
                return true
            }

            /**
             * 是否能使用Oaid
             *
             * @return true可以使用，false禁止使用。默认为true
             */
            override fun isCanUseOaid(): Boolean {
                return true
            }

            /**
             * 是否能使用Gaid
             *
             * @return true可以使用，false禁止使用。默认为true
             */
            override fun isCanUseGaid(): Boolean {
                return true
            }
        })
    }

    /**
     * 显示开屏广告
     */
    fun showSpreadAd(splashId : String,view : FrameLayout,listener : MyAdListener,isGone : Boolean = false){
        var splashAd : SplashAd?= null
        if(isGone){
            listener.close()
            return
        }
        splashAd = SplashAd(view.context,null,splashId,object : AdListener {
            override fun onAdLoaded() {
                //加载成功
                splashAd?.show(view)
            }

            //广告展示
            override fun onAdShown() {
                listener.loaded()
            }

            //广告加载错误
            override fun onAdFailedToLoad(code : Int) {
                listener.close()
            }

            //广告关闭
            override fun onAdClosed() {
                listener.close()
            }

            //广告倒计时
            override fun onAdTick(time : Long) {
                listener.onAdTick(time)
            }

            //广告点击
            override fun onAdClicked() {

            }
        },5000)
        splashAd.loadAd(view.width,view.height)//第一个参数是宽度，第二个参数是高度
    }

    /**
     * 显示Banner广告
     */
    fun showBannerAd(splashId : String,view : FrameLayout,isGone : Boolean = false){
        val splashAd : BannerAd?
        if(isGone){
            return
        }
        splashAd = BannerAd(view.context,splashId,object : BannerAdListener {
            override fun onAdFailed(p0: Int) {

            }

            override fun onAdLoaded() {

            }

            override fun onAdShown() {

            }

            override fun onAdClosed() {
                //移除广告
                if (view.childCount > 0) {
                    view.removeAllViews()
                }
            }

            override fun onAdClick() {

            }

        },5000)
        val width = Util.px2dip(view.context, view.width.toFloat()).toFloat()
        val height = Util.px2dip(view.context, view.height.toFloat()).toFloat()
        splashAd.loadAd(width,height,view)//第一个参数是宽度，第二个参数是高度
    }

    /**
     * 显示激励视频
     */
        fun showRewardedVideoAd(activity : Activity,splashId : String,listener : MyAdListener,isGone : Boolean = false){
        var mRewardedVideoAd : RewardedVideoAd ?= null
        if(isGone){
            listener.close()
            return
        }
        mRewardedVideoAd = RewardedVideoAd(activity,splashId,object : RewardedVideoAdListener{
            /**
             * 获得奖励，在该回调中做奖励操作
             */
            override fun onRewarded() {
                listener.close()

            }
            /**
             * 加载失败
             * @param i 错误码
             */
            override fun onRewardedVideoAdFailedToLoad(i: Int) {

            }

            /**
             * 广告加载成功
             */
            override fun onRewardedVideoAdLoaded() {
                //广告加载成功直接显示
                if (mRewardedVideoAd != null && mRewardedVideoAd?.isLoaded == true) {
                    mRewardedVideoAd?.showAd(activity)
                }
            }

            /**
             * 广告展示
             */
            override fun onRewardedVideoAdShown() {

            }

            /**
             * 广告关闭
             */
            override fun onRewardedVideoAdClosed() {

            }

            /**
             * 广告点击
             */
            override fun onRewardedVideoClick() {

            }

            override fun onRewardedVideoComplete() {

            }
        },10000,1)
        mRewardedVideoAd.loadAd()
    }

    interface MyAdListener{
        fun loaded()

        fun close()

        fun onAdTick(millisUnitFinished : Long)
    }
}