package com.lazysoul.dksample

import com.lazysoul.dksample.model.Item
import com.lazysoul.dksample.model.ItemType
import com.lazysoul.dksample.model.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class MainViewModel(val view: MainView, val dataStore: DataStore) {

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    private var currentItem: Item by Delegates.observable(Item("kotlin", "#FF0000"))
    { _: KProperty<*>, oldItem: Item, newItem: Item ->
        view.onUpdatedItem(oldItem, newItem)
    }

    private var currentState: NetworkState<Item> by Delegates.observable(NetworkState.Init())
    { _: KProperty<*>, _: NetworkState<Item>, newState: NetworkState<Item> ->
        when (newState) {
            is NetworkState.Init -> view.hideProgress()
            is NetworkState.Loading -> view.showProgress()
            is NetworkState.Success<Item> -> currentItem = newState.item
            is NetworkState.Error -> view.onError(newState.throwable)
        }
    }

    fun requestItemInfo(itemType: ItemType) {
        dataStore.getItemInfo(itemType)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { currentState = NetworkState.Loading() }
            .doOnTerminate { currentState = NetworkState.Init() }
            .subscribe({
                currentState = NetworkState.Success(it)
            }, {
                currentState = NetworkState.Error(it)
            })
            .also {
                compositeDisposable.add(it)
            }
    }
}