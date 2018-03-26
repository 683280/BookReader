package com.carljay.cjlibrary.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.SystemClock
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.AbsListView

import com.carljay.cjlibrary.R
import com.carljay.cjlibrary.helper.ThreadHelper
import com.carljay.cjlibrary.helper.UiHelper
import com.carljay.cjlibrary.uitls.ViewUtils


/**
 * Created by Administrator on 2016/12/18.
 */

class MySwipeRefreshLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : SwipeRefreshLayout(context, attrs), AbsListView.OnScrollListener, SwipeRefreshLayout.OnRefreshListener {

    var listener: OnMyRefreshListener? = null

    private var listview: AbsListView? = null

    private var isBottom: Boolean = false

    private var offset: Int = 0

    private val paint = Paint()

    private var isDown = false

    private val colors: IntArray
    private var canDown = true

    init {
        this.setOnRefreshListener(this)
        val s = intArrayOf(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorPrimaryDesc)
        setColorSchemeResources(*s)
        setProgressViewOffset(true, 0, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, getContext().resources.displayMetrics).toInt())
        colors = IntArray(s.size)
        for (i in s.indices) {
            colors[i] = getContext().resources.getColor(s[i])
        }
    }

    fun showWait() {
        setProgressViewOffset(true,0,UiHelper.dpToPx(10).toInt())
        isRefreshing = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = childCount

        for (i in 0..size - 1) {
            val view = getChildAt(i)
            if (view is AbsListView) {
                listview = view
                listview!!.setOnScrollListener(this)
                ViewUtils.removeListViewShadow(listview)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (isBottom) {
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> downY = ev.y
                MotionEvent.ACTION_MOVE -> {
                    if (canDown) {
                        offset = (downY - ev.y).toInt()
                        if (offset >= 400) {
                            offset = 400
                        }
                        postInvalidate()
                    }
                }
                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                    if (canDown == false)
                    if (offset >= 400 && !isDown && listener != null && !isRefreshing) {
                        isDown = true
                        ThreadHelper.runOnThread(runnable)
                        listener!!.onTopRefresh()
                    }
                    offset = 0
                    postInvalidate()
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        try {
            super.onLayout(changed, left, top, right, bottom)
        } catch (e: Exception) {
        }

        //        view.layout(left,top,right,bottom);

    }

    private var downY: Float = 0.toFloat()


    override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {

    }

    override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {

        isBottom = visibleItemCount + firstVisibleItem == totalItemCount
    }

    override fun onRefresh() {
        if (listener != null) {
            listener!!.onTopRefresh()
        }
    }

    override fun onDraw(canvas: Canvas) {

    }

    private val rects = arrayOfNulls<Rect>(2)

    private var init = true

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)

        if (init) {
            for (i in rects.indices) {
                rects[i] = Rect()
                val rect = rects[i]!!
                rect.top = (height - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, context.resources.displayMetrics)).toInt()
                rect.bottom = height
                rect.left = width / 2
                rect.right = width / 2
            }
            init = false
        }
        if (!end) {
            try {
                rects[0]!!.top = (height - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, context.resources.displayMetrics)).toInt()
                rects[0]!!.bottom = height

                rects[1]!!.top = rects[0]!!.top
                rects[1]!!.bottom = height
                paint.color = colors[if (rectColor - 1 < 0) colors.size - 1 else rectColor - 1]
                canvas.drawRect(rects[0], paint)
                paint.color = colors[rectColor]
                canvas.drawRect(rects[1], paint)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return
        }
        paint.color = resources.getColor(R.color.colorPrimaryDesc)
        val width = width / 2
        val padding = (width * (offset / 400.0f)).toInt()
        val y = (height - UiHelper.dpToPx(3)).toInt()
        val startX = width - padding
        val stopX = width + padding
        canvas.drawRect(startX.toFloat(), y.toFloat(), stopX.toFloat(), height.toFloat(), paint)

    }

    private val runnable = MyRunnable()
    fun setCanDown(b: Boolean) {
        canDown = b
    }

    override fun setRefreshing(refreshing: Boolean) {
        isDown = refreshing
        super.setRefreshing(refreshing)
    }

    private var rectColor = 0
    private var end = false

    internal inner class MyRunnable : Runnable {

        override fun run() {
            end = false
            val width = width
            val i = (width / 50.0f).toInt()
            val r = width / 2
            while (true) {
                val rect_1 = rects[1]!!
                rect_1.left -= i
                rect_1.right += i
                if (rect_1.left <= 0) {
                    rects[0]!!.left = 0
                    rects[0]!!.right = width
                    rect_1.left = r
                    rect_1.right = r
                    rectColor = if (++rectColor > colors.size - 1) 0 else rectColor
                    if (!isDown) {
                        for (n in rects.indices) {
                            rects[n]!!.right = r
                            rects[n]!!.left = r
                        }
                        rectColor = 0
                        end = true
                        return
                    }
                }

                SystemClock.sleep(10)
                postInvalidate()
            }
        }
    }

    interface OnMyRefreshListener {
        fun onTopRefresh()
        fun onBottomLoad()
    }
}
