package com.kseniyaa.loftmoneykotlin

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import kotlinx.android.synthetic.main.fragment_items.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemsFragment : Fragment() {

    var type: String? = ""
    private var api: Api? = null
    var adapter = ItemsAdapter()
    var actionMode: ActionMode? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = arguments
        type = args?.getString(KEY_TYPE)

        api = (activity?.application as App).api
        adapter.listener = AdapterListener()

        loadItems()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mainActivity = this.activity as MainActivity?
        val fab: FloatingActionButton? = mainActivity?.findViewById(R.id.btn_fab)

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
                fab?.let {
                    if (dy < 0 && it.isShown)
                        it.show()
                    else if (dy > 0 && it.isShown)
                        it.hide()
                }
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

    internal inner class AdapterListener : ItemsAdapterListener {

        override fun OnItemClick(item: Item, position: Int) {
            if (actionMode == null) {
                return
            }
            toggleItem(item.id)
           // val item1:Item = item
            actionMode?.title = getString(R.string.action_mode_title) + adapter.getSelectedItems().size
            println(adapter.getSelectedItems().size)
        }

        override fun OnItemLongClick(item: Item, position: Int) {
            if (actionMode != null) {
                return
            }
            (activity as AppCompatActivity).startSupportActionMode(ActionModeCallback())
            toggleItem(item.id)
            actionMode?.title = getString(R.string.action_mode_title) + 1
        }

        private fun toggleItem(position: Int) {
            adapter.toggleItem(position)
        }

        internal inner class ActionModeCallback : ActionMode.Callback {

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                if (item?.itemId == R.id.menu_item_delete) {
                    // showConfirmationDialog()
                    return true
                }
                return false
            }

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                actionMode = mode
                return true
            }
            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                val inflater = MenuInflater(requireContext())
                inflater.inflate(R.menu.menu_action_mode, menu)
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                adapter.clearSelections()
                actionMode = null
            }
        }
    }
}



