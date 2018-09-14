package com.kseniyaa.loftmoneykotlin

interface ItemsAdapterListener {
    fun OnItemClick(item: Item, position: Int)
    fun OnItemLongClick(item: Item, position: Int)
}