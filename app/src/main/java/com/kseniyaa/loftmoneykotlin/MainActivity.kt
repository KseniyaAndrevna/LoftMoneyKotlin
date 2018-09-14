package com.kseniyaa.loftmoneykotlin

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var actionMode: ActionMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val adapter = MainPagesAdapter(supportFragmentManager, this)

        val viewPager = view_pager
        viewPager.adapter = adapter

        tab_layout.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                when (position) {
                    MainPagesAdapter.PAGE_INCOMES, MainPagesAdapter.PAGE_EXPENSES -> btn_fab.show()

                    MainPagesAdapter.PAGE_BALANCE -> btn_fab.hide()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {     }
        })

        btn_fab.setOnClickListener {
            val page = viewPager.currentItem

            var type: String? = null

            when (page) {
                MainPagesAdapter.PAGE_INCOMES -> type = Item.Types.income.toString()
                MainPagesAdapter.PAGE_EXPENSES -> type = Item.Types.expense.toString()
            }

            if (type != null) {
                val intent = Intent(this@MainActivity, AddActivity::class.java)
                intent.putExtra(AddActivity.KEY_TYPE, type)
                startActivityForResult(intent, ItemsFragment.ITEM_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragments = supportFragmentManager.fragments
        for (fragment in fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onSupportActionModeFinished(mode: ActionMode) {
        super.onSupportActionModeFinished(mode)
        tab_layout.setBackgroundColor(resources.getColor(R.color.tab_color_primary))
        btn_fab.show()
    }

    override fun onSupportActionModeStarted(mode: ActionMode) {
        super.onSupportActionModeStarted(mode)
        tab_layout.setBackgroundColor(resources.getColor(R.color.action_mode_back))
        btn_fab.hide()
        actionMode = mode
    }
}

