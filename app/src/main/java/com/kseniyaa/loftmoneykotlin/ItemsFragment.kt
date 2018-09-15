package com.kseniyaa.loftmoneykotlin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
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
    private var sharedPreferences: SharedPreferences? = null
    private var authToken: String? = null


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

    companion object {
        const val KEY_TYPE = "type"
        const val ITEM_REQUEST_CODE = 100
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loadItems()
    }


    internal inner class AdapterListener : ItemsAdapterListener {

        override fun OnItemClick(item: Item, position: Int) {
            if (actionMode == null) {
                return
            }
            toggleItem(item.id, position)
            actionMode?.title = getString(R.string.action_mode_title) + adapter.getSelectedItems().size
            println(adapter.getSelectedItems().size)
        }

        override fun OnItemLongClick(item: Item, position: Int) {
            if (actionMode != null) {
                return
            }
            (activity as AppCompatActivity).startSupportActionMode(ActionModeCallback())
            toggleItem(item.id, position)
            actionMode?.title = getString(R.string.action_mode_title) + 1
        }

        private fun toggleItem(id: Int, position: Int) {
            adapter.toggleItem(id, position)
        }

        internal inner class ActionModeCallback : ActionMode.Callback {

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                if (item?.itemId == R.id.menu_item_delete) {
                    showConfirmationDialog()
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

        private fun showConfirmationDialog() {
            ConfirmDeleteDialog(object : ConfirmDeleteDialog.Listener {
                override fun onDeleteConfirmed() {
                    removeSelectedItems()
                }
            }).show(fragmentManager, null)
        }
    }

    private fun getTokenValue() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        authToken = sharedPreferences?.getString(AuthActivity.SAVE_TOKEN, "")
    }

    private fun removeSelectedItems() {
        val selected = adapter.getSelectedItems()
        for (i in selected.indices) {
            removeItem(selected[i])
        }
        actionMode?.finish()
    }

    fun loadItems() {
        getTokenValue()
        val call = api?.getItems(type, authToken)

        call?.enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                refresh.isRefreshing = false
                val items = response.body()
                items?.let { adapter.setItems(it) }
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                println(t)
                refresh.isRefreshing = false
            }
        })
    }

    private fun removeItem(id: Int) {
        val call = api?.deleteItem(id, authToken)
        call?.enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                loadItems()
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {}
        })
    }
}



