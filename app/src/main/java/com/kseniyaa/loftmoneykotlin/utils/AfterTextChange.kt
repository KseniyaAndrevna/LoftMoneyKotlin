package com.kseniyaa.loftmoneykotlin.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_add.view.et_name
import kotlinx.android.synthetic.main.activity_add.view.btn_add
import kotlinx.android.synthetic.main.activity_add.view.et_price
//import kotlinx.android.synthetic.main.activity_add.view.*



fun EditText.afterTextChanged() {

    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            btn_add.isEnabled = !(et_name.text.isEmpty() || et_price.text.isEmpty())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    })
}
