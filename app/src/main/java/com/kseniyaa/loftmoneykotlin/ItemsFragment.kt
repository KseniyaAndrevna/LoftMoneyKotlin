package com.kseniyaa.loftmoneykotlin

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_items.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemsFragment : Fragment() {

    var type: String? = ""
    private var api: Api? = null
    var adapter = ItemsAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = arguments
        type = args?.getString(KEY_TYPE)

        api = (activity!!.application as App).api

        loadItems()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler.adapter = adapter
        refresh.setColorSchemeColors(
                ContextCompat.getColor(requireContext(), R.color.refresh_first),
                ContextCompat.getColor(requireContext(), R.color.refresh_second),
                ContextCompat.getColor(requireContext(), R.color.refresh_third),
                ContextCompat.getColor(requireContext(), R.color.refresh_fourth))
        refresh.setOnRefreshListener { loadItems() }

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy < 0 && !btn_fab.isShown)
                    btn_fab.show()
                else if (dy > 0 && btn_fab.isShown)
                    btn_fab.hide()
            }
        })
    }

    private fun loadItems() {
        val call: Call<ItemsData>? = api?.getItems(type)

        call?.enqueue(object : Callback<ItemsData> {

            override fun onResponse(call: Call<ItemsData>, response: Response<ItemsData>) {
                refresh.isRefreshing = false
                val data = response.body()
                val items: List<Item>? = data?.data
                items?.let { adapter.setItems(it as MutableList<Item>) }
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ItemsData>, t: Throwable) {
                refresh.isRefreshing = false
            }
        })
    }

    companion object {
        const val KEY_TYPE = "type"
        const val ITEM_REQUEST_CODE = 100
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((requestCode == ITEM_REQUEST_CODE) and (resultCode == RESULT_OK)) {
            val item = data!!.getParcelableExtra<Item>(AddActivity.KEY_ITEM)
            println(item.type)
            if (item.type.equals(type)) {
                adapter.addItem(item)
            }
        }
    }
}



