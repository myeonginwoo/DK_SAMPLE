package com.lazysoul.dksample

import com.lazysoul.dksample.model.Item
import com.lazysoul.dksample.model.MultiNetworkState
import com.lazysoul.dksample.model.NetworkState
import com.lazysoul.dksample.model.NewNetworkState
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class NetworkStateTest {

    private val item = Item("kotlin", "#FFFFFF")

    @Test
    fun state1IsInitTest() {
        assertEquals(MultiNetworkState.Init(),
            NewNetworkState(NetworkState.Init(), NetworkState.Init()).getMultiState())
        assertEquals(MultiNetworkState.Loading(),
            NewNetworkState(NetworkState.Init(), NetworkState.Loading()).getMultiState())
        assertEquals(MultiNetworkState.Loading(),
            NewNetworkState(NetworkState.Init(), NetworkState.Success(item)).getMultiState())
        assertEquals(MultiNetworkState.Error(NullPointerException()),
            NewNetworkState(NetworkState.Init(), NetworkState.Error(NullPointerException())).getMultiState())
    }

    @Test
    fun state1IsLoadingTest() {
        assertEquals(MultiNetworkState.Loading(),
            NewNetworkState(NetworkState.Loading(), NetworkState.Init()).getMultiState())
        assertEquals(MultiNetworkState.Loading(),
            NewNetworkState(NetworkState.Loading(), NetworkState.Loading()).getMultiState())
        assertEquals(MultiNetworkState.Loading(),
            NewNetworkState(NetworkState.Loading(), NetworkState.Success(item)).getMultiState())
        assertEquals(MultiNetworkState.Error(NullPointerException()),
            NewNetworkState(NetworkState.Loading(), NetworkState.Error(NullPointerException())).getMultiState())
    }

    @Test
    fun state1IsSuccessTest() {
        assertEquals(MultiNetworkState.Loading(),
            NewNetworkState(NetworkState.Success(item), NetworkState.Init()).getMultiState())
        assertEquals(MultiNetworkState.Loading(),
            NewNetworkState(NetworkState.Success(item), NetworkState.Loading()).getMultiState())
        assertEquals(MultiNetworkState.Success(item, item),
            NewNetworkState(NetworkState.Success(item), NetworkState.Success(item)).getMultiState())
        assertEquals(MultiNetworkState.Error(NullPointerException()),
            NewNetworkState(NetworkState.Success(item), NetworkState.Error(NullPointerException())).getMultiState())
    }

    @Test
    fun state1IsErrorTest() {
        assertEquals(MultiNetworkState.Error(NullPointerException()),
            NewNetworkState(NetworkState.Error(NullPointerException()), NetworkState.Init()).getMultiState())
        assertEquals(MultiNetworkState.Error(NullPointerException()),
            NewNetworkState(NetworkState.Error(NullPointerException()), NetworkState.Loading()).getMultiState())
        assertEquals(MultiNetworkState.Error(NullPointerException()),
            NewNetworkState(NetworkState.Error(NullPointerException()), NetworkState.Success(item)).getMultiState())
        assertEquals(MultiNetworkState.Error(NullPointerException()),
            NewNetworkState(NetworkState.Error(NullPointerException()),
                NetworkState.Error(NullPointerException())).getMultiState())
    }
}