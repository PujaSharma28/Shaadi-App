package com.example.test.util

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData


/*
 * Internet Util class to update the internet connection to UI
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
object InternetUtil : LiveData<Boolean>() {

    private var application: Application =
        ShaadiApplication.instance
    private var networkCallback: NetworkCallback

    init {
        networkCallback =
            NetworkCallback(this)

    }

    override fun onActive() {
        super.onActive()
        val connectivityManager =
            application.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            updateConnection()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        }

        val filter = IntentFilter()
        filter.addAction(CONNECTIVITY_SERVICE)
        application.registerReceiver(
            networkReceiver, filter)

    }

    override fun onInactive() {
        super.onInactive()
        application.unregisterReceiver(
            networkReceiver
        )

    }

    private val networkReceiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onReceive(context: Context, intent: Intent) {
            updateConnection()
        }
    }

    fun updateConnection() {
        val cm = application.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm.getNetworkCapabilities(cm.activeNetwork)
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        } else {
            TODO("VERSION.SDK_INT < LOLLIPOP")
        }
        postValue(capabilities?.hasCapability(NET_CAPABILITY_INTERNET) == true)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    class NetworkCallback(private val liveData: InternetUtil) :
        ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            liveData.postValue(true)
        }

        override fun onLost(network: Network) {
            liveData.postValue(false)
        }
    }
}