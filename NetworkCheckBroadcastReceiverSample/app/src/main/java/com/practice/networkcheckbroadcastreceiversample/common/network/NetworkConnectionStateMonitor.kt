package com.practice.networkcheckbroadcastreceiversample.common.network

import android.content.Context
import android.net.*
import android.util.Log
import com.practice.networkcheckbroadcastreceiversample.common.CommonApplication

object NetworkConnectionStateMonitor : ConnectivityManager.NetworkCallback() {

    private val connectivityManager by lazy {
        CommonApplication.getInstance()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val networkRequest by lazy {
        NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    }

    private lateinit var onConnected: () -> Unit
    private lateinit var onDisConnected: () -> Unit

    fun register(onConnected: () -> Unit, onDisConnected: () -> Unit) {
        this.onConnected = onConnected
        this.onDisConnected = onDisConnected

        connectivityManager.registerNetworkCallback(networkRequest, this)
    }


    fun unregister() {
        connectivityManager.unregisterNetworkCallback(this)
    }

    override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
        super.onBlockedStatusChanged(network, blocked)
        Log.d("debugTest", "onBlockedStatusChanged")
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        Log.d("debugTest", "onCapabilitiesChanged")
    }

    override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties)
        Log.d("debugTest", "onLinkPropertiesChanged")
    }

    override fun onUnavailable() {
        super.onUnavailable()
        Log.d("debugTest", "onUnavailable")
    }

    override fun onLosing(network: Network, maxMsToLive: Int) {
        super.onLosing(network, maxMsToLive)
        Log.d("debugTest", "onLosing")
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        Log.d("debugTest", "onAvailable")

        //연결될때 처리
        onConnected.invoke()
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        Log.d("debugTest", "onLost")

        //연결해제될때 처리
        onDisConnected.invoke()

    }


}