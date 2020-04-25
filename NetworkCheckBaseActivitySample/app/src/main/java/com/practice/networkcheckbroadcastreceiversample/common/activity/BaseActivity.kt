package com.practice.networkcheckbroadcastreceiversample.common.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.practice.networkcheckbroadcastreceiversample.common.network.NetworkConnectionStateMonitor

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    lateinit var mBinding: B
        private set

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, layoutId)
        onAfterBinding(mBinding)
    }

    protected abstract fun onAfterBinding(mBinding: B)

    override fun onResume() {
        super.onResume()
        NetworkConnectionStateMonitor.register(
            onConnected = {
                Toast.makeText(this, "연결이 되었습니다.", Toast.LENGTH_SHORT).show()
            }, onDisConnected = {
                Toast.makeText(this, "연결이 해제되었습니다.", Toast.LENGTH_SHORT).show()
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkConnectionStateMonitor.unregister()
    }
}