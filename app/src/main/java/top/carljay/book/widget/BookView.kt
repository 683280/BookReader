package top.carljay.book.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.v_book_view.view.*
import top.carljay.book.R
import top.carljay.book.bean.ChapterBean

/**
 * Created by carljay on 2018/1/30.
 */
class BookView constructor(context: Context, attrs: AttributeSet? = null): FrameLayout(context,attrs), BookViewAdapter.BookViewInterface {


    var dataInterface : BookDataInterface? = null

    var adapter : BookViewAdapter
    init {
        var view = LayoutInflater.from(context).inflate(R.layout.v_book_view,null,false)
        var lp = LayoutParams(-1,-1)
        addView(view,lp)
        adapter = BookViewAdapter(vp_view_page,this)
        initView()
    }

    private fun initView() {
        vp_view_page.adapter = adapter
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (h != oldh && w != oldw) {
            adapter.onSizeChanged()
        }
    }

    interface BookDataInterface{
        fun onLoad(position : Int) : ChapterBean
        fun onLoadContent(chapterBean: ChapterBean) : String?
    }
    override fun createView(): BookTextView {
        return BookTextView1(context)
    }

    override fun onLoad(position: Int): BookViewAdapter.ChapterBean {
        var chapter = dataInterface?.onLoad(position)
        return chapter!!
    }
    override fun onLoadContent(chapter: BookViewAdapter.ChapterBean): String? {
        return dataInterface?.onLoadContent(chapter as ChapterBean)
    }
}
