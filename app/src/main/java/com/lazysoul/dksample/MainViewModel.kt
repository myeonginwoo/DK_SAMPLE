package com.lazysoul.dksample

import com.lazysoul.dksample.model.Item
import com.lazysoul.dksample.model.ItemType
import com.lazysoul.dksample.model.MultiNetworkState
import com.lazysoul.dksample.model.NetworkState
import com.lazysoul.dksample.model.NewNetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class MainViewModel(val view: MainView, val dataStore: DataStore) {

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    private var state1: NetworkState<Item> by Delegates.observable(NetworkState.Init(),
        { _: KProperty<*>, _: NetworkState<Item>, newState: NetworkState<Item> ->
            currentNetworkState = currentNetworkState.copy(state1 = newState)
        })

    private var state2: NetworkState<Item>by Delegates.observable(NetworkState.Init(),
        { _: KProperty<*>, _: NetworkState<Item>, newState: NetworkState<Item> ->
            currentNetworkState = currentNetworkState.copy(state2 = newState)
        })

    private var currentNetworkState: NewNetworkState<Item, Item>
        by Delegates.observable(NewNetworkState(NetworkState.Init(), NetworkState.Init()))
        { _: KProperty<*>, _: NewNetworkState<Item, Item>, newState: NewNetworkState<Item, Item> ->
            multiNetworkState = newState.getMultiState()
        }

    private var multiNetworkState: MultiNetworkState<Item, Item>by Delegates.observable(MultiNetworkState.Init())
    { _: KProperty<*>, _: MultiNetworkState<Item, Item>, newState: MultiNetworkState<Item, Item> ->
        when (newState) {
            is MultiNetworkState.Init -> view.hideProgress()
            is MultiNetworkState.Loading -> view.showProgress()
            is MultiNetworkState.Success<Item, Item> -> {
                view.onUpdatedItem(newState.itemA, newState.itemB)
                currentNetworkState = currentNetworkState.copy(NetworkState.Init(), NetworkState.Init())
            }
            is MultiNetworkState.Error -> {
                compositeDisposable.clear()
                view.onError(newState.throwable)
                currentNetworkState = currentNetworkState.copy(NetworkState.Init(), NetworkState.Init())
            }
        }
    }

    fun requestItemInfo(itemType: ItemType) {
        requestItemInfo1(itemType)
        requestItemInfo2(itemType)
    }

    private fun requestItemInfo1(itemType: ItemType) {
        dataStore.getItemInfo(itemType)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { state1 = NetworkState.Loading() }
            .subscribe({
                state1 = NetworkState.Success(it)
            }, {
                state1 = NetworkState.Error(it)
            })
            .also {
                compositeDisposable.add(it)
            }
    }

    private fun requestItemInfo2(itemType: ItemType) {
        dataStore.getItemInfo(itemType)
            .delay(5, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { state2 = NetworkState.Loading() }
            .subscribe({
                state2 = NetworkState.Success(it)
            }, {
                state2 = NetworkState.Error(it)
            })
            .also {
                compositeDisposable.add(it)
            }
    }
}