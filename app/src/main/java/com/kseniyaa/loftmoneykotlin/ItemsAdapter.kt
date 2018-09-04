package com.kseniyaa.loftmoneykotlin

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item.view.*

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private var items = emptyList<Item>()

    fun setItems(items: List<Item>) {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val context = parent.context
        return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.name?.text = items[position].name
        holder.price?.text = items[position].price.toString()
    }

    override fun getItemCount()= items.size

    class ItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val name = itemView?.tv_name
        val price = itemView?.tv_price
    }
}
