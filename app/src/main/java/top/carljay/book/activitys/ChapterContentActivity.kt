package top.carljay.book.activitys

import kotlinx.android.synthetic.main.a_chapter_content.*
import top.carljay.book.R
import top.carljay.book.bean.BookBean
import top.carljay.book.bean.ChapterBean
import top.carljay.book.book.BookHelper
import top.carljay.book.dao.ChapterDao
import top.carljay.book.widget.BookView

/**
 * Created by carljay on 2018/2/4.
 */
class ChapterContentActivity : BaseActivity(), BookView.BookDataInterface {


    var book : BookBean? = null
    override fun initContentView() {
        setContentView(R.layout.a_chapter_content)
    }

    override fun initView() {
        book_view.dataInterface = this
    }

    override fun initData() {
        book = intent.getSerializableExtra("data") as BookBean
        if (book?.chapterList == null || book?.chapterList!!.size == 0){
            ChapterDao.getBookChapter(book!!,{ data ->
                book?.chapterList = data
                var chapter = data[book!!.chapter_index]
                book_view.adapter.chapter_data = chapter
            })
        }
    }

    override fun onLoad(position: Int): ChapterBean {
        var chapter = book?.chapterList!![position]
        return chapter
    }
    override fun onLoadContent(chapterBean: ChapterBean): String? {
        var data = BookHelper.getChapterDetails(chapterBean.url,book!!)
        return data
    }
}