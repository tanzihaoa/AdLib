package com.tzh.addemo.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.tzh.ad.util.AdUtil
import com.tzh.ad.util.GDTAdUtil

class MyApplication : Application() {

    companion object {

        lateinit var mContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        //广告初始化
        AdUtil.init(this,"5529380")
//        GDTAdUtil.init(this,"1206884666")
    }

    /**
     * 这里会在onCreate之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param base
     */
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}