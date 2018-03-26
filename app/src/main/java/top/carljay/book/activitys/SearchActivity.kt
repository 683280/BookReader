package top.carljay.book.activitys

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import com.carljay.cjlibrary.helper.HttpHelper
import kotlinx.android.synthetic.main.a_search_book.*
import top.carljay.book.R
import top.carljay.book.adapter.SearchBookAdapter
import top.carljay.book.bean.ResponseBean
import top.carljay.book.bean.SearchBookBean
import top.carljay.book.config.Config
import android.support.v7.widget.SearchView
import android.view.MenuItem
import top.carljay.book.book.BookHelper
import top.carljay.book.book.YiteCollection


/**
 * Created by carljay on 2018/1/31.
 */
class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {


    var adapter = SearchBookAdapter()
    var searchView : SearchView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_search_book)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
        setupToolbar()
    }
    private fun search_book(title : String){
        recycler_view.isRefreshing = true
        BookHelper.searchBook(title,{ data ->
            adapter.data = data
            recycler_view.isRefreshing = false
        })

    }
    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
//        supportActionBar?.
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search,menu) != null

        searchView = menu?.findItem(R.id.ab_search)?.actionView as SearchView

        searchView?.onActionViewExpanded()

        searchView?.setOnQueryTextListener(this)
        searchView?.queryHint = "书名、作者"
        return super.onCreateOptionsMenu(menu)
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        search_book(query!!)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}