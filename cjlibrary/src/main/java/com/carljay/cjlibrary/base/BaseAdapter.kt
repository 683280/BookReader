package com.carljay.cjlibrary.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.carljay.cjlibrary.R
import com.carljay.cjlibrary.helper.UiHelper

/**
 * Created by CarlJay on 2016/12/18.
 */

abstract class BaseAdapter<T, DH : RecyclerView.ViewHolder> : RecyclerView.Adapter<DH>(), View.OnClickListener {

    private var mData: MutableList<T>? = null
    var listener: OnItemClickListener? = null
        set(listener) {
            field = listener
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

    fun addData(position: Int, data: ArrayList<T>?) {
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

    var data: MutableList<T>?
        get() = if (mData == null) null else mData
        set(data) {
            mData = data
            notifyDataSetChanged()
        }

    fun deleteItem(position: Int) {
        mData!!.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        if (mData == null)
            mData = ArrayList<T>()
        mData!!.add(item)
        notifyDataSetChanged()
    }

    fun addItem(item: T, position: Int) {
        if (mData == null)
            mData = ArrayList<T>()
        mData!!.add(position, item)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        mData!!.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (mData != null) mData!!.size else 0
    }

    val inflater: LayoutInflater
        get() = LayoutInflater.from(context)
    val context: Context
        get() = UiHelper.context!!

    fun onItemInit(holder: RecyclerView.ViewHolder, position: Int) {
        if (this.listener != null) {
            holder.itemView.setTag(R.string.app_name, position)
            holder.itemView.setOnClickListener(this)
        }
    }

    override fun onClick(view: View) {
        if (this.listener != null) {
            val position = view.getTag(R.string.app_name) as Int
            this.listener!!.onItemClick(view, position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}