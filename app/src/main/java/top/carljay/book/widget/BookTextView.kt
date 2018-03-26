package top.carljay.book.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.carljay.cjlibrary.helper.UiHelper
import kotlin.collections.ArrayList
/**
 * Created by carljay on 2018/2/4.
 */
abstract class BookTextView  constructor(context: Context,attrs : AttributeSet? = null) : FrameLayout(context,attrs) {
    var contentView : BookContentView

    //当前章节共有多少页，这是第几页
    var chapter_pager_index = 0

    init {
        addView(initView())
        contentView = getBookContentView()
    }
    abstract fun initView(): View
    abstract fun getBookContentView():BookContentView
    abstract fun setTitle(title:String)
    fun setData(text : String,title: String){
        UiHelper.postThreadOnMain {
            contentView.text = text
            setTitle(title)
        }
    }
    fun setTextSize(size:Float){
        contentView.textSize = size
    }
    fun getLineArray(s : String) : ArrayList<String>{
        return contentView.getLineArray(s)
    }
    fun getLineCount1() : Int{
        return contentView.getLineCount1()
    }

}
