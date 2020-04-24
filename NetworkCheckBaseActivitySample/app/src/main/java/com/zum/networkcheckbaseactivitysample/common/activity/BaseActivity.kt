package com.zum.networkcheckbaseactivitysample.common.activity

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import com.zum.networkcheckbaseactivitysample.common.network.NetworkConnectionStateMonitor
import com.zum.networkcheckbaseactivitysample.receiver.NetworkConnectStateReceiver

open class BaseActivity:AppCompatActivity(){

    private val networkConnectStateReceiver by lazy{
        NetworkConnectStateReceiver()
    }

    override fun onResume() {
        super.onResume()
        NetworkConnectionStateMonitor.register()

        //네트워크 연결이 되었는지 체크가 필요
        registerReceiver(networkConnectStateReceiver, IntentFilter(Intent.ACTION_MANAGE_NETWORK_USAGE))
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkConnectionStateMonitor.unregister()
        unregisterReceiver(networkConnectStateReceiver)
    }
}