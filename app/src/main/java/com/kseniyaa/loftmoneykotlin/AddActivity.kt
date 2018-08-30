package com.kseniyaa.loftmoneykotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kseniyaa.loftmoneykotlin.utils.afterTextChanged
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        et_name.afterTextChanged(btn_add,et_name,et_price)
        et_price.afterTextChanged(btn_add,et_name,et_price)
    }
}
