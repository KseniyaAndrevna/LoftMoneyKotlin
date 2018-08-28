package com.kseniyaa.loftmoneykotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_item.*
import java.util.ArrayList

class ItemActivity : AppCompatActivity() {

    private val items: ArrayList<Item> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = ItemsAdapter(items, this)

        recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        fun addItems() {
            items.add(Item("Молоко", "70"))
            items.add(Item("Зубная щётка", "70"))
            items.add(Item("Сковородка с антипригарным покрытием", "4500"))
            items.add(Item("Стол кухонный", "2000"))
            items.add(Item("Велосипед", "5000"))
            items.add(Item("Кружка", "100"))
            items.add(Item("Палатка", "3000"))
            items.add(Item("Рюкзак", "2999"))
        }

        addItems()
    }
}
