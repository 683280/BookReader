package com.carljay.cjlibrary.widget

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.carljay.cjlibrary.R
import kotlinx.android.synthetic.main.cj_refresh_recycler_view.view.*

/**
 * Created by carljay on 2018/2/2.
 */
open class RefreshRecyclerView (context:Context,attrs: AttributeSet? = null):
        FrameLayout(context,attrs){

    var adapter : Adapter<*>
        set(adapter){
            recycler_view.adapter = adapter
        }
        get(){
            return recycler_view.adapter
        }
    var layoutManager: RecyclerView.LayoutManager
        set(layoutManager){
            recycler_view.layoutManager = layoutManager
        }
        get(){
            return recycler_view.layoutManager
        }
    var onRefreshListener : OnRefreshListener? = null
        set(value) = swipe_refresh_layout.setOnRefreshListener(value)
    var isRefreshing :Boolean = false
        set(value){
            swipe_refresh_layout.isRefreshing = value
        }

    lateinit var recycler_view : RecyclerView
    init {
        var root_view = View.inflate(context, R.layout.cj_refresh_recycler_view,null)
        with(root_view){
            recycler_view = id_recycler_view
            val s = intArrayOf(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorPrimaryDesc)
            swipe_refresh_layout.setColorSchemeResources(*s)
        }
        addView(root_view)
    }

    interface OnRefreshListener:SwipeRefreshLayout.OnRefreshListener{
        fun onLoad() : Boolean
    }
}