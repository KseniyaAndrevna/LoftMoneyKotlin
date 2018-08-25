package com.kseniyaa.loftmoneykotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import com.pawegio.kandroid.textWatcher
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        fun watch(editText: EditText) {
            editText.textWatcher {
                afterTextChanged { btn_add.isEnabled = !(et_name.text.isEmpty() || et_price.text.isEmpty()) }
            }
        }
        watch(et_name)
        watch(et_price)
    }
}

