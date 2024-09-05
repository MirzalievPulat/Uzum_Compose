package uz.gita.uzumcompose.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkStatusValidator @Inject constructor(@ApplicationContext val context: Context) {

    var isNetworkEnabled: Boolean = false
        private set

    fun listenNetworkStatus(onAvailable: () -> Unit, onLost: () -> Unit) {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                isNetworkEnabled = true
                onAvailable.invoke()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                isNetworkEnabled = false
                onLost.invoke()
            }
        }

        val connectivityManager = context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }
}