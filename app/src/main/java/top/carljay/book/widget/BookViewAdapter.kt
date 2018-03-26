package top.carljay.book.widget

import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import com.carljay.cjlibrary.helper.ThreadHelper
import java.io.Serializable

/**
 * Created by carljay on 2018/2/4.
 */
class BookViewAdapter(vp:ViewPager,bookInterface :BookViewInterface): PagerAdapter(), ViewPager.OnPageChangeListener {

    private var line_count = 0
    private var vp : ViewPager = vp
    private val VIEW_SIZE = 4
    private var loadType = 0
    var bookInterface :BookViewInterface = bookInterface
    var title = ""
    //当前章节有多少页
    var chapter_pager_size = 0
    //当前是第几章
    var chapter_index = 0
    var isInit = false
    //获取数据队列
    var array = ArrayList<BookTextView>(VIEW_SIZE)
    var line_datas = ArrayList<String>()
    var chapter_data : ChapterBean? = null
        set(value) {
            field = value
            if (value != null) {
                title = value.title!!
                ThreadHelper.execute {
                    chapter_text = if (value.content == null)
                        bookInterface.onLoadContent(value)
                    else
                        value.content
                }
            }
        }
    private var chapter_text : String? = null
        set(value) {
            field = value
            if (!isInit) return
            line_datas.clear()
            if (value == null)
                field = ""
            line_datas.addAll(array[0].getLineArray(field!!))
            chapter_pager_size = Math.ceil(line_datas.size / 1.0 / line_count).toInt()
            offset = if (loadType < 0){vp.currentItem - chapter_pager_size + 1}else{vp.currentItem}
            updateViewText()
        }
    init {
        for (i in 0 until VIEW_SIZE){
            var b = bookInterface?.createView()
            array.add(b)
        }
        vp.adapter = this
        vp.addOnPageChangeListener(this)
        array[0].viewTreeObserver.addOnGlobalLayoutListener {
            if (!isInit){
                isInit = true
                onSizeChanged()
                if (chapter_text != null){
                    chapter_text = chapter_text
                }
            }
        }
    }
    fun onSizeChanged(){
        line_count = array[0].getLineCount1()
    }
    private fun setText(contentView: BookTextView){
        var position = contentView.chapter_pager_index
        if (position >= chapter_pager_size){
            contentView.setData("","")
            return
        }
        if (position < 0){
            contentView.setData("","")
            return
        }
        var start = position * line_count
        var end = start + line_count
        if (end > line_datas.size)
            end = line_datas.size

        var datas = line_datas.subList(start,end)
        var sb = StringBuilder()
        for (i in datas){
            sb.append(i)
        }
        contentView.setData(sb.toString(),title)
    }
    private fun updateViewText(){
        if (loadType < 0){
            var v = getView(vp.currentItem)
            v.chapter_pager_index = chapter_pager_size - 1
            setText(v)
            v = getView(vp.currentItem - 1)
            v.chapter_pager_index = chapter_pager_size - 2
            setText(v)
        }else{
            var v = getView(vp.currentItem)
            v.chapter_pager_index = 0
            setText(v)
            v = getView(vp.currentItem + 1)
            v.chapter_pager_index = 1
            setText(v)
        }
    }
    private fun load(position:Int){
        ThreadHelper.execute {
            var data = bookInterface?.onLoad(position)
            chapter_data = data
        }
    }

    override fun onPageSelected(position: Int) {
        var i = position - offset
        if (i >= chapter_pager_size){
            loadType = 0
            offset = position
            getView(position - 1).setData("","")
            load(++chapter_index)
        }else if(i < 0){
            loadType = -1
            offset = position
            getView(position + 1).setData("","")
            load(--chapter_index)
        }
    }
    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun isViewFromObject(view: View?, obj: Any?): Boolean {
        return view == obj
    }
    override fun getCount(): Int {
        return if (array.size > 3) Int.MAX_VALUE else 0
    }
    fun getView(position: Int):BookTextView{
        return array[position % array.size]
    }
    private var offset = 0
    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        var v = getView(position)
        v.chapter_pager_index = position - offset
        setText(v)
        container?.addView(v)
        return v
    }
    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container?.removeView(`object` as View?)
    }

    interface BookViewInterface{
        fun createView():BookTextView
        fun onLoad(position: Int):ChapterBean
        fun onLoadContent(chapter : ChapterBean):String?
    }
    open class ChapterBean : Serializable{
        var title : String? = null
        var content : String? = null
    }
}