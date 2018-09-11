package com.kseniyaa.loftmoneykotlin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(var name: String?, var price: Int, val type: String?) : Parcelable{

    enum class Types {
        expense, income
    }
}

