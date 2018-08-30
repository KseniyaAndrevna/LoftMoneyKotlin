package com.kseniyaa.loftmoneykotlin

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.kseniyaa.loftmoneykotlin.utils.newInstance


class MainPagesAdapter internal constructor(fm: FragmentManager, context: Context) : FragmentPagerAdapter(fm) {

    private var pagesTitles: Array<String>? = context.resources.getStringArray(R.array.main_tabs)

    override fun getItem(position: Int): Fragment? {
        return when (position) {

            PAGE_EXPENSES -> newInstance(ItemsFragment.KEY_TYPE,ItemsFragment.TYPE_EXPENSES)

            PAGE_INCOMES -> newInstance(ItemsFragment.KEY_TYPE, ItemsFragment.TYPE_INCOMES)

            PAGE_BALANCE -> BalanceFragment()

            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pagesTitles?.get(position)
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    companion object {
        const val PAGE_EXPENSES = 0
        const val PAGE_INCOMES = 1
        const val PAGE_BALANCE = 2
        const val PAGE_COUNT = 3
    }
}



