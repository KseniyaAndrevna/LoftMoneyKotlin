package com.kseniyaa.loftmoneykotlin

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.kseniyaa.loftmoneykotlin.utils.afterTextChanged
import kotlinx.android.synthetic.main.activity_add.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddActivity : AppCompatActivity() {

    private var api: Api? = null
    private var sharedPreferences: SharedPreferences? = null
    private var auth_token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        et_name.afterTextChanged(btn_add, et_name, et_price)
        et_price.afterTextChanged(btn_add, et_name, et_price)
        api = (application as App).api

        val type = intent.extras!!.getString(KEY_TYPE)

        btn_add.setOnClickListener {
            val name = et_name.text.toString()
            val price = et_price.text.toString()

            val item = Item(0,name, Integer.parseInt(price), type)
            createItems(item)


        }
    }

    private fun createItems(item: Item) {
        getTokenValue()
        val call = api?.createItem(item, auth_token)
        call?.enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                finish()
            }
            override fun onFailure(call: Call<Item>, t: Throwable) {
                println(t)
            }
        })
    }

    private fun getTokenValue() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        auth_token = sharedPreferences?.getString(AuthActivity.SAVE_TOKEN, "")
    }

    companion object {
        const val KEY_TYPE = "type"
    }
}
