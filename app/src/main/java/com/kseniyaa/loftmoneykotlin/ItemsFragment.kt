package com.kseniyaa.loftmoneykotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_items.*
import java.util.*

class ItemsFragment : Fragment() {

    private val items: ArrayList<Item> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = arguments

        if (args?.getInt(KEY_TYPE, TYPE_UNKNOWN) == TYPE_UNKNOWN) throw IllegalStateException("Unknown type")

        addItems()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = ItemsAdapter(items, requireContext())

        recycler.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    private fun addItems() {
        items.add(Item("Молоко", "70"))
        items.add(Item("Зубная щётка", "70"))
        items.add(Item("Сковородка с антипригарным покрытием", "4500"))
        items.add(Item("Стол кухонный", "2000"))
        items.add(Item("Велосипед", "5000"))
        items.add(Item("Кружка", "100"))
        items.add(Item("Палатка", "3000"))
        items.add(Item("Рюкзак", "2999"))
    }

    companion object {
        const val KEY_TYPE = "type"
        const val TYPE_EXPENSES = 1
        const val TYPE_INCOMES = 2
        const val TYPE_UNKNOWN = -1
    }
}
