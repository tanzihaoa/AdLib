package com.tzh.addemo

import android.content.Context
import android.content.Intent
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
//        AdUtil.showSpreadAd("888827949","5466461","",binding.frameLayout,object : AdUtil.MyAdListener{
//            override fun loaded() {
//
//            }
//
//            override fun close() {
//
//            }
//
//            override fun onAdTick(millisUnitFinished: Long) {
//
//            }
//
//            override fun onError(csJAdError: CSJAdError?) {
//
//            }
//        })
    }

    fun toRecycler(){

    }

    fun toImage(){

    }

    fun start(){

    }

    fun saveVideo(){

    }
}