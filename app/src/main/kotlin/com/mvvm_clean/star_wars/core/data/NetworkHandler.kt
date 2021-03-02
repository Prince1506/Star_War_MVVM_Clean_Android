package com.mvvm_clean.star_wars.core.data

import android.content.Context
import android.net.NetworkCapabilities
import android.os.Build
import com.mvvm_clean.star_wars.core.domain.extension.connectivityManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Injectable class which returns information about the network connection state.
 */
@Suppress("DEPRECATION")
@Singleton
class NetworkHandler
@Inject constructor(private val context: Context) {

    //  Helper Methods-----------------------------------------
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.connectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {

            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }
    }
}