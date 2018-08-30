package com.kseniyaa.loftmoneykotlin


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MainPagesAdapter(supportFragmentManager, this)

        val viewPager = view_pager
        viewPager.adapter = adapter

        tab_layout.setupWithViewPager(viewPager)
    }
}
