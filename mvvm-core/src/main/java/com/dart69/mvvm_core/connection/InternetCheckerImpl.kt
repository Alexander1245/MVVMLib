package com.dart69.mvvm_core.connection

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.content.getSystemService

interface InternetChecker {
    fun isInternetEnabled(): Boolean

    class Default(
        private val context: Context
    ) : InternetChecker {
        @RequiresApi(Build.VERSION_CODES.M)
        @RequiresPermission(value = "android.permission.ACCESS_NETWORK_STATE")
        override fun isInternetEnabled(): Boolean {
            val manager = context.getSystemService<ConnectivityManager>()
            val activeNetwork = manager?.activeNetwork
            manager?.getNetworkCapabilities(activeNetwork).also {
                return it != null && it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            }
        }
    }
}
