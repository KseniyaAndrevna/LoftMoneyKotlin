package com.kseniyaa.loftmoneykotlin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(var id: Int, var name: String?, var price: Int, var type: String?) : Parcelable {

    enum class Types {
        expense, income
    }
}
