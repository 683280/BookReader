package top.carljay.book.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.carljay.cjlibrary.helper.ImageHepler
import kotlinx.android.synthetic.main.i_search_item.view.*
import top.carljay.book.R
import top.carljay.book.activitys.BookDetailsActivity
import top.carljay.book.bean.SearchBookBean

/**
 * Created by carljay on 2018/1/31.
 */
class SearchBookAdapter : BaseAdapter<SearchBookBean, SearchBookAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(parent?.context).inflate(R.layout.i_search_item,parent,false)
        v.setOnClickListener { v ->
            var i = v.tag as Int
            var intent = Intent(parent?.context, BookDetailsActivity::class.java)
            intent.putExtra("data",getData(i))
            parent?.context?.startActivity(intent)
        }
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        with(holder!!.itemView){
            var book = getData(position)!!
            title.text = book.title
            author.text = book.author
            desc.text = book.synopsis
            ImageHepler.load(book.img).to(img)
            holder.itemView.tag = position
        }
    }

    inner class ViewHolder (viewItem : View) : RecyclerView.ViewHolder(viewItem){
    }
}