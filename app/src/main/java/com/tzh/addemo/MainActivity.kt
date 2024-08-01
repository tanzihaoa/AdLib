package com.tzh.addemo

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.bytedance.sdk.openadsdk.CSJAdError
import com.tzh.ad.show.AdUtil
import com.tzh.ad.show.RewardedVideoAdUtil
import com.tzh.addemo.base.AppBaseActivity
import com.tzh.addemo.databinding.ActivityMainBinding


class MainActivity : AppBaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    companion object{
        fun start(context : Context){
            context.startActivity(Intent(context,MainActivity::class.java))
        }
    }

    val listener by lazy {
        object : AdUtil.MyRewardedAdListener{
            override fun loaded() {

            }

            override fun close() {
                Log.e("video=====","关闭")
            }

            override fun onRewardArrived() {
                Log.e("video=====","获取奖励成功")
                Toast.makeText(this@MainActivity,"获取奖励成功",Toast.LENGTH_SHORT).show()
            }

            override fun onError(s: String) {

            }
        }
    }

    override fun initView() {
        binding.v = this
        binding.tvWallpaper.setOnClickListener {
            start()
        }

    }

    override fun initData() {

    }

    fun toRecycler(){
        RewardedVideoAdUtil.showRewardedVideoAd(this,"959168404",listener)
    }

    fun toImage(){

    }

    fun start(){
        AdUtil.showFullScreenVideoAd(this,"959316816")
    }

    fun saveVideo(){

    }
}