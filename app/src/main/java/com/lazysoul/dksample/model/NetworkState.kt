package com.lazysoul.dksample.model

sealed class NetworkState<out T> {
    class Init : NetworkState<Nothing>()
    class Loading : NetworkState<Nothing>()
    class Success<out T>(val item: T) : NetworkState<T>()
    class Error(val throwable: Throwable?) : NetworkState<Nothing>()
}