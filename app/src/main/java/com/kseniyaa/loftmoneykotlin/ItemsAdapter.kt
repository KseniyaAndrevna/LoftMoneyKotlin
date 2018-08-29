package com.kseniyaa.loftmoneykotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item.view.*

class ItemsAdapter(private val items: List<Item>, private val context: Context) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.name?.text = items[position].name
        holder.price?.text = items[position].price
    }

    override fun getItemCount()= items.size

    class ItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val name = itemView?.tv_name
        val price = itemView?.tv_price
    }
}
