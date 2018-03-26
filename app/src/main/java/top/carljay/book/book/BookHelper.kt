package top.carljay.book.book

import com.carljay.cjlibrary.helper.ThreadHelper
import com.carljay.cjlibrary.helper.UiHelper
import top.carljay.book.bean.BookBean
import top.carljay.book.bean.SearchBookBean

/**
 * Created by carljay on 2018/2/2.
 */
class BookHelper{
    companion object {
        var collections = HashMap<String,BaseCollection>()
        init {
            var yite = YiteCollection()
            collections[yite.TAG] = yite
        }
        fun searchBook(title:String,a : (ArrayList<SearchBookBean>?) -> Unit) {
            ThreadHelper.execute {
                var books = collections["yite"]?.searchBook(title)
                UiHelper.postThreadOnMain {
                    a(books)
                }
            }
        }
        fun getBookDetails(book : BookBean,f : (BookBean?) -> Unit){
            ThreadHelper.execute {
                var b = collections[book.type]?.getBookDetails(book.url)
                UiHelper.postThreadOnMain {
                    f(b)
                }
            }
        }
        fun getChapterDetails(url : String,book : BookBean) : String?{
            return collections[book.type]?.getChapterDetails(url)
        }
    }
}