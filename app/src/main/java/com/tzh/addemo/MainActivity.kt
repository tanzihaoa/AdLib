package com.tzh.addemo

import android.content.Context
import android.content.Intent
import com.luck.picture.lib.utils.ToastUtils
import com.tzh.ad.util.AdUtil
import com.tzh.addemo.base.AppBaseActivity
import com.tzh.addemo.databinding.ActivityMainBinding


class MainActivity : AppBaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    companion object{
        fun start(context : Context){
            context.startActivity(Intent(context,MainActivity::class.java))
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
        AdUtil.showRewardedVideoAd(this,"958230124",object : AdUtil.MyRewardedAdListener{
            override fun loaded() {

            }

            override fun close() {

            }

            override fun onRewardArrived() {
                ToastUtils.showToast(this@MainActivity,"获取奖励成功")
            }

            override fun onError(s: String) {

            }
        })
    }

    fun toImage(){

    }

    fun start(){

    }

    fun saveVideo(){

    }
}