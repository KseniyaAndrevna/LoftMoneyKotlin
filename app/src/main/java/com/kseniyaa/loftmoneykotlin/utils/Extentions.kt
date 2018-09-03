package com.kseniyaa.loftmoneykotlin.utils

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import com.kseniyaa.loftmoneykotlin.ItemsFragment

fun EditText.afterTextChanged(btn_add: Button, et_name: EditText, et_price: EditText) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            btn_add.isEnabled = !(et_name.text.isEmpty() || et_price.text.isEmpty())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun newInstance(KEY_TYPE: String, type: String): ItemsFragment {
    val fragment = ItemsFragment()
    val bundle = Bundle()
    bundle.putString(KEY_TYPE, type)
    return fragment
}
