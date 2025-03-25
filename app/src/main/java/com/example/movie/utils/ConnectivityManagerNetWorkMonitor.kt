package com.example.movie.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import android.net.NetworkRequest.Builder
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class ConnectivityManagerNetWorkMonitor @Inject constructor(
    @ApplicationContext private val context: Context,
//    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    ) : NetWorkMonitor {
    override val isOnline: Flow<Boolean> = callbackFlow {
        val connectivityManager = context.getSystemService<ConnectivityManager>()
        if (connectivityManager == null) {
            channel.trySend(false)
            channel.close()
            return@callbackFlow
        }

        // create call back
        val callback = object : NetworkCallback() {

            private val networks = mutableSetOf<Network>()

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                networks.add(network)
                channel.trySend(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                networks.remove(network)
                trySend(networks.isNotEmpty())
            }
        }
        val request = Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, callback)
        channel.trySend(connectivityManager.isCurrentlyConnected())
    }
        .conflate()

    @Suppress("DEPRECATION")
    private fun ConnectivityManager.isCurrentlyConnected() = activeNetwork
        ?.let(::getNetworkCapabilities)
        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
}