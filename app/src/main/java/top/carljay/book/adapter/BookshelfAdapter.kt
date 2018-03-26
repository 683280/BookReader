package top.carljay.book.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.carljay.cjlibrary.helper.ImageHepler
import top.carljay.book.R
import kotlinx.android.synthetic.main.i_main_item.view.*
import top.carljay.book.activitys.ChapterContentActivity
import top.carljay.book.bean.BookBean

/**
 * Created by carljay on 2018/1/30.
 */
class BookshelfAdapter (context: Context) : BaseAdapter<BookBean, BookshelfAdapter.ViewHolder>(){
    init {
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(View.inflate(context, R.layout.i_main_item,null))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        with(holder!!.itemView){
            var book = getData(position)!!
            holder.itemView.tag = book
            ImageHepler.load(book.img).to(img)
            title.text = book.title
            desc.text = book.synopsis
        }
    }

    inner class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener { view ->
                var book = view.tag as BookBean
                var intent = Intent(itemView.context,ChapterContentActivity::class.java)
                intent.putExtra("data",book)
                itemView.context.startActivity(intent)
            }
        }
    }
}