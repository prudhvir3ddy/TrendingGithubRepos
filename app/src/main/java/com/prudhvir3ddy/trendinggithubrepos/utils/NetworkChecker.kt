package com.prudhvir3ddy.trendinggithubrepos.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.util.Log

class NetworkChecker {

  companion object {
    lateinit var cm: ConnectivityManager
    lateinit var mCallback: ConnectivityManager.NetworkCallback

    private const val TAG = "NETWORK"

    fun checkInternet(context: Context) {

      cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

      val request: NetworkRequest = NetworkRequest.Builder().build()
      mCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
          super.onAvailable(network)
          Log.d(TAG, "available")
        }

        override fun onLost(network: Network) {
          super.onLost(network)
          Log.d(TAG, "lost")
        }

        override fun onUnavailable() {
          super.onUnavailable()
          Log.d(TAG, "unavailable")
        }
      }
      cm.registerNetworkCallback(request, mCallback)
    }

    fun unregisterCallback() {
      cm.unregisterNetworkCallback(mCallback)
    }
  }
}

