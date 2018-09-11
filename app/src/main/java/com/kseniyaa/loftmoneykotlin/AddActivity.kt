package com.kseniyaa.loftmoneykotlin

import android.content.Intent
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

        btn_add.setOnClickListener{
            val name = et_name.text.toString()
            val price = et_price.text.toString()

            val type = intent.extras!!.getString(KEY_TYPE)

            val item = Item(name, Integer.parseInt(price), type)

            val intent = Intent()
            intent.putExtra(KEY_ITEM, item)

            setResult(RESULT_OK, intent)
            finish()
        }
    }
    companion object {
        const val KEY_TYPE = "type"
        const val KEY_ITEM = "item"
    }
}
