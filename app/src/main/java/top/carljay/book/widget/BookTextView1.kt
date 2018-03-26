package top.carljay.book.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import top.carljay.book.R
import kotlinx.android.synthetic.main.i_book_text_view.view.*

/**
 * Created by carljay on 2018/2/9.
 */
class BookTextView1 (context: Context): BookTextView(context) {
    override fun setTitle(t: String) {
        title.text = t
    }


    override fun initView(): View {
        return LayoutInflater.from(context).inflate(R.layout.i_book_text_view,null,false)
    }

    override fun getBookContentView(): BookContentView {
        return book_content_view as BookContentView
    }
}