package com.lazysoul.dksample

import com.lazysoul.dksample.model.Item

interface MainView {

    fun onUpdatedItem(oldItem: Item, newItem: Item)

    fun showProgress()

    fun hideProgress()

    fun onError(throwable: Throwable?)
}