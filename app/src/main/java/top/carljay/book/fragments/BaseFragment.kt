package top.carljay.book.fragments;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.carljay.cjlibrary.client.CJClient
import com.carljay.cjlibrary.helper.CJNetHelper
import com.carljay.cjlibrary.view.MySwipeRefreshLayout


/**
 * Created by 豪杰 on 2016/12/1.
 */

abstract class BaseFragment : android.support.v4.app.Fragment(),MySwipeRefreshLayout.OnMyRefreshListener,View.OnClickListener {

    private var rootView: View? = null
    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = createView(inflater,container,savedInstanceState)
        if (rootView == null)
            return null
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initView(rootView!!)
        initData(savedInstanceState)
    }
    open fun initData(savedInstanceState: Bundle?) {

    }

    open fun initView(view: View) {

    }

    open fun getData() {

    }

    open fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    override fun onTopRefresh() {
    }

    override fun onBottomLoad() {
    }
    override fun onClick(v: View?) {
    }
}
