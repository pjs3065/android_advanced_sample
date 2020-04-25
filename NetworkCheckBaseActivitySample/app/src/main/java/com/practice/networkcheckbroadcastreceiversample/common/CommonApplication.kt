package com.practice.networkcheckbroadcastreceiversample.common

import android.app.Application

class CommonApplication : Application() {
    companion object {
        @Volatile
        private var instance: CommonApplication? = null

        @JvmStatic
        fun getInstance(): CommonApplication = instance
            ?: synchronized(this) {
            instance
                ?: CommonApplication().also {
                instance = it
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}