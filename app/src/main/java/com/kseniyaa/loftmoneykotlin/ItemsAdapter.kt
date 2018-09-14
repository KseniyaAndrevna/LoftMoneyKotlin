package com.kseniyaa.loftmoneykotlin

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item.view.*
import java.util.*

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private var items: List<Item> = ArrayList()
    var listener: ItemsAdapterListener? = null

    fun setItems(items :List<Item> ) {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val context = parent.context
        return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false))

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.name?.text = items[position].name
        holder.price?.text = items[position].price.toString()
        val item = items[position]
        holder.bind(item, listener, position, selections.get(position))
    }

    override fun getItemCount() = items.size

    private val selections = SparseBooleanArray()

    fun toggleItem(id: Int) {
        if (selections.get(id, false)) {
            selections.put(id, false)
        } else {
            selections.put(id, true)
        }
        notifyItemChanged(id)
    }

    fun clearSelections() {
        selections.clear()
        notifyDataSetChanged()
    }

    fun getSelectedItems(): List<Int> {
        val selected = ArrayList<Int>()
        for (i in 0 until itemCount) {
            val id = items[i].id
            if (selections.get(id)) {
                selected.add(id)
            }
        }
        return selected
    }

    class ItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val name = itemView?.tv_name
        val price = itemView?.tv_price

        fun bind(item: Item, listener: ItemsAdapterListener?, position: Int, selected: Boolean) {
            if (item.type.equals(Item.Types.income.toString())) {
                price?.setTextColor(ContextCompat.getColor(itemView.context, R.color.item_text_price_exp_color))
            }

            itemView.isSelected = selected

            itemView.setOnClickListener {
                listener?.OnItemClick(item, position)
            }

            itemView.setOnLongClickListener {
                listener?.OnItemLongClick(item, position)
                true
            }
        }
    }
}
