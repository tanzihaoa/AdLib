package com.tzh.addemo.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.beizi.fusion.BeiZiCustomController
import com.beizi.fusion.BeiZis

class MyApplication : Application() {

    companion object {

        lateinit var mContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        //广告初始化
        BeiZis.init(this,"20159",object : BeiZiCustomController() {
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