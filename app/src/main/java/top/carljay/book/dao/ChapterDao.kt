package top.carljay.book.dao

import com.carljay.cjlibrary.helper.ThreadHelper
import com.carljay.cjlibrary.helper.UiHelper
import top.carljay.book.ClientApplication
import top.carljay.book.bean.BookBean
import top.carljay.book.bean.ChapterBean
import top.carljay.book.book.BookHelper

/**
 * Created by carljay on 2018/2/2.
 */
class ChapterDao {
    companion object {
        fun getBookChapter(book:BookBean,f : (ArrayList<ChapterBean>) -> Unit){
            ThreadHelper.execute {

                var read = ClientApplication.db.readableDatabase
                var cursor = read.rawQuery("select * from chapter where book_id=?", arrayOf("$book.id"))
                if (cursor.count > 0) {
                    var datas = ArrayList<ChapterBean>(cursor.count)
                    while (cursor.moveToNext()) {
                        var chapter = ChapterBean()
                        var title = cursor.getString(2)
                        var url = cursor.getString(3)
                        chapter.title = title
                        chapter.url = url
                        datas.add(chapter)
                    }
                    f(datas)
                }else{
                    BookHelper.getBookDetails(book,{ chapter ->
                        var datas = chapter?.chapterList ?: return@getBookDetails
                        ThreadHelper.execute {
                            f(datas)
                        }
                    })
                }
            }

        }
    }
}