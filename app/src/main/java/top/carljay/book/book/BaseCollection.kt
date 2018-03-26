package top.carljay.book.book

import top.carljay.book.bean.BookBean
import top.carljay.book.bean.SearchBookBean
import java.util.regex.Pattern

/**
 * Created by carljay on 2018/2/2.
 */
abstract class BaseCollection{

    abstract fun searchBook(title:String) : ArrayList<SearchBookBean>?
    abstract fun getBookDetails(url:String) : BookBean?
    abstract fun getChapterDetails(url:String) : String?

    fun getContentString(src : String,start : String,end : String):ArrayList<String>?{
        var pat = Pattern.compile("(?<=$start)([\\s\\S]*?)(?=$end)")
        var mat = pat.matcher(src)
        var datas = ArrayList<String>()
        while(mat.find()){
            datas.add(mat.group())
        }
        return datas
    }
}