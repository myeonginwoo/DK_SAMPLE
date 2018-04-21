package com.lazysoul.dksample

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.lazysoul.dksample.model.Item
import com.lazysoul.dksample.model.ItemType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainViewModel = MainViewModel(this, DataStore())

        bt_a.setOnClickListener {
            mainViewModel.requestItemInfo(ItemType.ITEM_A)
        }

        bt_b.setOnClickListener {
            mainViewModel.requestItemInfo(ItemType.ITEM_B)
        }

        bt_c.setOnClickListener {
            mainViewModel.requestItemInfo(ItemType.ITEM_C)
        }
    }

    override fun onUpdatedItem(oldItem: Item, newItem: Item) {
        tv_item.text = "${oldItem.title} -> ${newItem.title}"
        iv_item.setBackgroundColor(Color.parseColor(newItem.color))
    }

    override fun showProgress() {
        pb_item.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pb_item.visibility = View.GONE
    }

    override fun onError(throwable: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
