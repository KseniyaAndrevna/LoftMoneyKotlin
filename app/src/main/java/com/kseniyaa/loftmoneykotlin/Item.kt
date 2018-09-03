package com.kseniyaa.loftmoneykotlin

class Item(var name: String?, var price: Int?) {

    companion object {
        const val TYPE_EXPENSES = "expense"
        const val TYPE_INCOMES = "income"
    }
}
