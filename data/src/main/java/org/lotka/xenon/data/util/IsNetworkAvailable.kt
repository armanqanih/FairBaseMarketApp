package org.lotka.xenon.data.util

import android.content.Context
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

import android.net.ConnectivityManager

    @RequiresApi(Build.VERSION_CODES.M)
      fun IsNetworkAvailable (context:Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
