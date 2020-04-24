package com.zum.networkcheckbaseactivitysample.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NetworkConnectStateReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let{
            if(it.action == NetworkStateCheck.ACTION_CHECK_INTERNET.code){
                //상태 확인
            }
        }
    }
}