package com.tzh.ad.util

import android.content.Context
import android.util.Log
import android.widget.FrameLayout
import com.qq.e.ads.splash.SplashAD
import com.qq.e.ads.splash.SplashADListener
import com.qq.e.comm.managers.GDTAdSdk
import com.qq.e.comm.util.AdError


/**
 * 优量汇广告
 */
object GDTAdUtil {
    //广告初始化
    fun init(context: Context, appId : String,listener: AdUtil.InitListener?= null){
        GDTAdSdk.initWithoutStart(context,appId)
        GDTAdSdk.start(object : GDTAdSdk.OnStartListener{
            override fun onStartSuccess() {
                Log.e("=======GDT","广告初始化成功")
                listener?.success()
            }

            override fun onStartFailed(e : Exception?) {
                Log.e("=======GDT","广告初始化成功错误====${e?.message}")
                listener?.fail()
            }
        })
    }

    /**
     * 显示开屏广告
     */
    fun showSpreadAd(splashId : String, view : FrameLayout, listener : GDTAdListener, isGone : Boolean = false) {
        if (isGone) {
            listener.close()
            return
        }
        val splashAD = SplashAD(view.context, splashId, object : SplashADListener{
            override fun onADDismissed() {
                listener.close()
                Log.e("=======GDT","onADDismissed")
            }

            override fun onNoAD(e : AdError?) {
                listener.onError(e)
                listener.close()
                Log.e("=======GDT", e?.errorCode.toString() + "===" + e?.errorMsg)
            }

            override fun onADPresent() {
                Log.e("=======GDT","onADPresent")

            }

            override fun onADClicked() {

                Log.e("=======GDT","onADClicked")
            }

            override fun onADTick(millisUntilFinished : Long) {
                listener.onAdTick(millisUntilFinished)
                Log.e("=======GDT","onADTick")
            }

            override fun onADExposure() {

                Log.e("=======GDT","onADExposure")
            }

            override fun onADLoaded(p0: Long) {
                Log.e("=======GDT","onADLoaded")
            }
        })
        splashAD.showAd(view)
    }



    interface GDTAdListener{
        fun loaded()

        fun close()

        fun onAdTick(millisUnitFinished : Long)

        fun onError(error : AdError?)
    }
}