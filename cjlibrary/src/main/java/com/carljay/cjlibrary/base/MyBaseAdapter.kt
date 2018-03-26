package com.carljay.cjlibrary.base

import android.content.Context
import android.view.View
import android.widget.BaseAdapter

import com.carljay.cjlibrary.helper.UiHelper

import java.util.ArrayList

/**
 * Created by 豪杰 on 2016/3/18.
 */
abstract class MyBaseAdapter<T> : BaseAdapter() {

    protected var mData: MutableList<T>? = null
    private var listener: AdapterListener? = null

    private var isNoData = true
    private var noDataView: View? = null
    private var showNoDataView = false

    var data: MutableList<T>?
        get() = if (mData == null) null else mData
        set(data) {
            mData = data
            notifyDataSetChanged()
        }

    protected val context: Context
        get() = UiHelper.context

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    fun setNoDataView(view: View) {
        noDataView = view
        notifyDataSetChanged()
    }

    fun openNoDataView() {
        showNoDataView = true
        notifyDataSetChanged()
    }

    fun closeNoDataView() {
        showNoDataView = false
        notifyDataSetChanged()
    }

    fun addData(data: MutableList<T>?) {
        if (mData == null) {
            mData = data
        } else {
            if (data != null)
                mData!!.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun addData(position: Int, data: MutableList<T>?) {
        if (mData == null) {
            mData = data
        } else {
            if (data != null)
                mData!!.addAll(position, data)
        }
        notifyDataSetChanged()
    }

    fun clean() {
        if (mData != null) {
            this.mData!!.clear()
            notifyDataSetChanged()
        }
    }

    fun getData(position: Int): T? {
        return if (mData == null) null else mData!![position]
    }

    fun deleteItem(position: Int) {
        mData!!.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        if (mData == null)
            mData = ArrayList()
        mData!!.add(item)
        notifyDataSetChanged()
    }

    fun addItem(item: T, position: Int) {
        if (mData == null)
            mData = ArrayList()
        mData!!.add(position, item)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        mData!!.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        val count = if (mData == null) 0 else mData!!.size
        isNoData(count)
        return if (showNoDataView) if (count == 0) 1 else count else count
    }

    protected fun isNoData(count: Int) {
        if (listener != null) {
            if (count == 0 && isNoData == false) {
                isNoData = true
                listener!!.onNoData(true)
            } else if (count != 0 && isNoData) {
                isNoData = false
                listener!!.onNoData(false)
            }
        }
    }

    fun setDataListener(listener: AdapterListener) {
        this.listener = listener
    }

    interface AdapterListener {
        fun onNoData(`is`: Boolean)
    }
}
