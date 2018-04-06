package com.lazysoul.dksample.model

sealed class NetworkState {
    class Init : NetworkState()
    class Loading : NetworkState()
    class Success<out T>(val item: T) : NetworkState()
    class Error(val throwable: Throwable?) : NetworkState()
}