package top.carljay.book.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.carljay.cjlibrary.widget.RefreshRecyclerView
import kotlinx.android.synthetic.main.f_main_bookshelf.*
import top.carljay.book.R
import top.carljay.book.adapter.BookshelfAdapter
import top.carljay.book.dao.BookshelfDao

/**
 * Created by carljay on 2018/2/1.
 */
class BookshelfFragment: BaseFragment(), RefreshRecyclerView.OnRefreshListener {

    lateinit var adapter : BookshelfAdapter
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = BookshelfAdapter(context)
        return inflater.inflate(R.layout.f_main_bookshelf,null)
    }
    override fun initView(view: View) {
        try {
            recycler_view.layoutManager = LinearLayoutManager(context)
            recycler_view.adapter = adapter
            recycler_view.onRefreshListener = this
        }catch (e:Exception){
            e.printStackTrace()
        }
        recycler_view.isRefreshing = true
        onRefresh()
    }
    override fun onLoad() :Boolean {
        BookshelfDao.getBookshelfList { data ->
            adapter.data = data
            recycler_view.isRefreshing = false
        }
        return false
    }

    override fun onRefresh() {
        BookshelfDao.getBookshelfList { data ->
            adapter.data = data
            recycler_view.isRefreshing = false
        }
    }
}