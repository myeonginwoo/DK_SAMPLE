package com.lazysoul.dksample.model

sealed class MultiNetworkState<out A, out B> {
    class Init : MultiNetworkState<Nothing, Nothing>() {
        override fun equals(other: Any?): Boolean = other is Init
    }

    class Loading : MultiNetworkState<Nothing, Nothing>() {
        override fun equals(other: Any?): Boolean = other is Loading
    }

    data class Success<out A, out B>(val itemA: A, val itemB: B) : MultiNetworkState<A, B>()

    class Error(val throwable: Throwable?) : MultiNetworkState<Nothing, Nothing>() {
        override fun equals(other: Any?): Boolean = other is Error
    }
}