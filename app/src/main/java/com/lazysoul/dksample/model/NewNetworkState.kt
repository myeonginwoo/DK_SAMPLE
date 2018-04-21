package com.lazysoul.dksample.model

data class NewNetworkState<out A, out B>(val state1: NetworkState<A>, val state2: NetworkState<B>) {
    fun getMultiState(): MultiNetworkState<A, B> = when {
        state1 is NetworkState.Error -> MultiNetworkState.Error(state1.throwable)
        state2 is NetworkState.Error -> MultiNetworkState.Error(state2.throwable)
        state1 is NetworkState.Loading || state2 is NetworkState.Loading -> MultiNetworkState.Loading()
        state1 is NetworkState.Init && state2 is NetworkState.Success -> MultiNetworkState.Loading()
        state1 is NetworkState.Success && state2 is NetworkState.Init -> MultiNetworkState.Loading()
        state1 is NetworkState.Success && state2 is NetworkState.Success ->
            MultiNetworkState.Success(state1.item, state2.item)
        else -> MultiNetworkState.Init()
    }
}