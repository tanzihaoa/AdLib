package com.tzh.addemo.activity

import android.annotation.SuppressLint
import android.view.KeyEvent
import com.bytedance.sdk.openadsdk.CSJAdError
import com.tzh.ad.show.AdUtil
import com.tzh.addemo.MainActivity
import com.tzh.addemo.R
import com.tzh.addemo.base.AppBaseActivity
import com.tzh.addemo.databinding.ActivityLaunchBinding

@SuppressLint("CustomSplashScreen")
open class LaunchActivity : AppBaseActivity<ActivityLaunchBinding>(R.layout.activity_launch) {

    override fun initView() {
        toMain()
    }

    override fun initData() {

    }

    /**
     * 跳转到主页面
     */
    fun toMain(){
        binding.root.postDelayed({
            loadAd()
        },2000)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 必须添加的标识
     * 当设置开屏可点击时，需要等待跳转页面关闭后，再切换至您的主窗口。故此时需要增加canJumpImmediately判断。 另外，点击开屏还需要在onResume中调用jumpWhenCanClick接口。
     */
    private var canJumpImmediately = false
    private fun loadAd(){
        AdUtil.showSpreadAd("889551332",binding.frameLayout,object : AdUtil.MyAdListener{
            override fun loaded() {

            }

            override fun close() {
                toHome()
            }

            override fun onAdTick(millisUnitFinished: Long) {

            }

            override fun onError(csJAdError: CSJAdError?) {
                toHome()
            }
        })
    }

    fun toHome(){
        if(canJumpImmediately){
            MainActivity.start(this@LaunchActivity)
            finish()
        }else{
            canJumpImmediately = true
        }
    }

    override fun onResume() {
        super.onResume()
        if (canJumpImmediately) {
            toHome()
        }
        canJumpImmediately = true
    }

    override fun onPause() {
        super.onPause()
        canJumpImmediately = false
    }

}