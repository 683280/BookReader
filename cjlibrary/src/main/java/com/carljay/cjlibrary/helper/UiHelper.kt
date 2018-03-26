package com.carljay.cjlibrary.helper

import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.util.TypedValue

import com.carljay.cjlibrary.client.CJClient

/**
 * Created by 豪杰 on 2016/11/29.
 */
object UiHelper {
    private var handler: Handler? = null

    fun initHandler() {
        handler = Handler()
    }

    fun postThreadOnMain(runnable: Runnable, d: Int) {
        handler!!.postDelayed(runnable, d.toLong())
    }

    fun postThreadOnMain(runnable: Runnable) {
        handler!!.postDelayed(runnable, 0)
    }
    fun postThreadOnMain(runnable: ()->Unit) {
        handler!!.postDelayed(runnable, 0)
    }

    fun dpToPx(dp: Int): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics)
    }
    fun spToPx(dp: Int): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp.toFloat(), context.resources.displayMetrics)
    }

    val context: Context
        get() = CJClient.mContext!!
    val resources: Resources
        get() = context.resources

}
