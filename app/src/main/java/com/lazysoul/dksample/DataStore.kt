package com.lazysoul.dksample

import com.lazysoul.dksample.model.Item
import com.lazysoul.dksample.model.ItemType
import io.reactivex.Observable

class DataStore {

    fun getItemInfo(itemType: ItemType): Observable<Item> = Observable.just(Item(itemType.toString(), when(itemType){
        ItemType.ITEM_A -> "#FF0000"
        ItemType.ITEM_B -> "#00FF00"
        ItemType.ITEM_C -> "#0000FF"
        ItemType.ITEM_D -> "#000000"
    }))

}