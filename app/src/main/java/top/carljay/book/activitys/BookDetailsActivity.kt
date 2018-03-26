package top.carljay.book.activitys

import android.content.Intent
import com.carljay.cjlibrary.helper.ImageHepler
import kotlinx.android.synthetic.main.a_book_details.*
import top.carljay.book.R
import top.carljay.book.bean.BookBean
import top.carljay.book.book.BookHelper
import top.carljay.book.dao.BookshelfDao

/**
 * Created by carljay on 2018/2/3.
 */
class BookDetailsActivity : BaseActivity(){
    override fun initContentView() {
        setContentView(R.layout.a_book_details)
    }

    override fun initView() {
        setupToolbar()
        var book = intent.getSerializableExtra("data") as BookBean
        initData(book)
        getData(book)
    }
    private fun initData(book:BookBean){
        textView.text = book.title
        textView2.text = book.author
        desc.text = book.synopsis
        ImageHepler.load(book.img).to(img)
        textView4.text = book?.status
        reading.setOnClickListener {
            var intent = Intent(this,ChapterContentActivity::class.java)
            intent.putExtra("data",book)
            startActivity(intent)
        }
        add_to_book_shelf.setOnClickListener {
            var id = BookshelfDao.addBookShelf(book)
            if (book.chapterList !=  null)
                BookshelfDao.addBookChapter(id,book.chapterList!!)
        }
    }
    private fun getData(book:BookBean){
        BookHelper.getBookDetails(book,{ b ->
            if (b != null)
                initData(b)
        })
    }
    private fun setupToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}