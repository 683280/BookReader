package top.carljay.book.book

import com.carljay.cjlibrary.helper.HttpHelper
import top.carljay.book.bean.BookBean
import top.carljay.book.bean.ChapterBean
import top.carljay.book.bean.SearchBookBean

/**
 * Created by carljay on 2018/2/2.
 */
class YiteCollection : BaseCollection(){


    val BASE_URL = "https://www.yite.cc/"
    val SEARCH_URL = BASE_URL + "search.php?q=&?"
    val TAG = "yite"
    override fun searchBook(title: String) : ArrayList<SearchBookBean>? {
        var html: String? = HttpHelper.httpGet(SEARCH_URL, arrayOf(title)) ?: return null
        var bs = getContentString(html!!,"<div class=\"item\">","</dl>") ?: return null
        var books = ArrayList<SearchBookBean>(bs.size)
        for (b in bs!!){
            var book = SearchBookBean()
            book.title = getContentString(b,"title=\"","\"")!![1]
            book.author = getContentString(b,"<span>","</span>")!![0]
            book.synopsis = getContentString(b,"<span>","</span>")!![1]
            book.img = getContentString(b,"src=\"","\"")!![0]
            book.url = getContentString(b,"<a href=\"","\"")!![0]
            book.type = TAG
            books.add(book)
        }
        return books
    }

    override fun getBookDetails(url: String) : BookBean? {
        var html: String? = HttpHelper.httpGet(url) ?: return null
        var status = getContentString(html!!,"status_text\">","<") ?: return null
        var title = getContentString(html!!,"<h1>","</h1>") ?: return null
        var img = getContentString(html!!,"img\"><img src=\"","\"") ?: return null
        var author = getContentString(html!!,"作者：","<") ?: return null
        var desc = getContentString(html!!,"des\">","<") ?: return null
        var d = getContentString(html!!,"<div id=\"list\">","</div>") ?: return null
        var chapters = getContentString(d[0],"<li>","</li>") ?: return null
        var book = BookBean()
        book.title = title[0]
        book.img = img[0]
        book.status = status[0]
        book.author = author[0]
        book.synopsis = desc[0]
        book.chapterList = ArrayList(chapters.size)
        for(c in chapters){
            var chapter = ChapterBean()
            var u = getContentString(c,"=\"","\"")
            var t = getContentString(c,"\">","<")
            chapter.title = t!![0]
            chapter.url = url + u!![0]
            book.chapterList?.add(chapter)
        }
        book.type = TAG
        return book
    }

    override fun getChapterDetails(url: String) : String?{
        var html: String? = HttpHelper.httpGet(url) ?: return null
        var content = getContentString(html!!,"<div id=\"content\">","</div>") ?: return null
        var reg = Regex("\\<[\\S\\s]+?\\>")
        var data = content[0].replace(reg,"\n   ")
        return data
    }
}