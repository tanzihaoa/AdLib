package com.tzh.addemo

import com.tzh.ad.util.AdUtil
import com.tzh.addemo.base.AppBaseActivity
import com.tzh.addemo.databinding.ActivityMainBinding


class MainActivity : AppBaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun initView() {
        binding.v = this
        binding.tvWallpaper.setOnClickListener {
            start()
        }
    }

    override fun initData() {
        AdUtil.showSpreadAd("888827949","5466461","",binding.frameLayout,object : AdUtil.MyAdListener{
            override fun loaded() {

            }

            override fun close() {

            }

            override fun onAdTick(millisUnitFinished: Long) {

            }
        })
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