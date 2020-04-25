package com.practice.networkcheckbroadcastreceiversample

import android.content.Intent
import com.practice.networkcheckbroadcastreceiversample.common.activity.BaseActivity
import com.practice.networkcheckbroadcastreceiversample.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onAfterBinding(mBinding: ActivityMainBinding) {
        with(mBinding) {
            view = this@MainActivity
        }
    }

    fun onClickBtn(){
        startActivity(Intent(this@MainActivity, MainActivity2::class.java))
    }
}
