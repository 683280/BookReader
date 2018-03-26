package top.carljay.book.adapter;

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import top.carljay.book.fragments.BookshelfFragment


import java.util.ArrayList

/**
 * Created by 豪杰 on 2016/12/1.
 */

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val array = ArrayList<Fragment>()

    private val titles = arrayOf("书架", "发现", "消息", "好友")

    init {
        array.add(BookshelfFragment())
    }

    override fun getCount(): Int {
        return array.size
    }

    override fun getItem(position: Int): Fragment {

        return array[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}
