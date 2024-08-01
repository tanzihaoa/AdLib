package com.tzh.ad.show

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bytedance.sdk.openadsdk.AdSlot
import com.bytedance.sdk.openadsdk.TTAdConstant
import com.bytedance.sdk.openadsdk.TTAdNative
import com.bytedance.sdk.openadsdk.TTAdSdk
import com.bytedance.sdk.openadsdk.TTRewardVideoAd
import com.tzh.ad.util.toDefault

object RewardedVideoAdUtil {
    var mRewardVideoAd : TTRewardVideoAd ?= null
    /**
     * 加载激励视频
     * @param rewardId 激励视频ID
     * @param isShow 加载完是否直接展示
     */
    fun loadRewardedVideoAd(activity : Activity, rewardId : String, listener : AdUtil.MyRewardedAdListener, isShow : Boolean = false){
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
                    mRewardVideoAd = ttRewardVideoAd
                    if(isShow){
                        showRewardedVideoAd(activity,rewardId,listener)
                    }
                }else{
                    listener.onError("广告为空")
                }

            }
        })
    }

    fun showRewardedVideoAd(activity : Activity,rewardId : String,listener : AdUtil.MyRewardedAdListener){
        if(mRewardVideoAd != null){
            mRewardVideoAd?.setRewardAdInteractionListener(object : TTRewardVideoAd.RewardAdInteractionListener{
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
                    mRewardVideoAd = null
                    loadRewardedVideoAd(activity,rewardId,listener)
                }

                override fun onVideoComplete() {
                    Log.e("Video=======","onVideoComplete")
                    //视频播放完毕的回调

                }

                override fun onVideoError() {
                    Log.e("Video=======","onVideoError")
                    //视频播放失败的回调
                    listener.onError("播放失败")
                    mRewardVideoAd = null
                    loadRewardedVideoAd(activity,rewardId,listener)
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
            mRewardVideoAd?.showRewardVideoAd(activity)
        }else{
            loadRewardedVideoAd(activity,rewardId,listener,true)
            Toast.makeText(activity,"广告加载中...",Toast.LENGTH_SHORT).show()
        }
    }
}