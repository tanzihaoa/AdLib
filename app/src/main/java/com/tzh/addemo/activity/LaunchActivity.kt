package com.tzh.addemo.activity

import android.annotation.SuppressLint
import android.view.KeyEvent
import com.qq.e.comm.util.AdError
import com.tzh.ad.util.GDTAdUtil
import com.tzh.addemo.MainActivity
import com.tzh.addemo.R
import com.tzh.addemo.base.AppBaseActivity
import com.tzh.addemo.databinding.ActivityLaunchBinding
import com.tzh.mylibrary.util.OnPermissionCallBackListener
import com.tzh.mylibrary.util.PermissionXUtil

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
        PermissionXUtil.requestAnyPermission(this, mutableListOf<String>().apply {
            add(android.Manifest.permission.READ_PHONE_STATE)
            add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        },object : OnPermissionCallBackListener {
            override fun onAgree() {
                GDTAdUtil.showSpreadAd("4029526733916550",binding.frameLayout,object : GDTAdUtil.GDTAdListener{
                    override fun loaded() {

                    }

                    override fun close() {
                        toHome()
                    }

                    override fun onAdTick(millisUnitFinished: Long) {

                    }

                    override fun onError(error : AdError?) {
                        toHome()
                    }
                })
            }

            override fun onDisAgree() {

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