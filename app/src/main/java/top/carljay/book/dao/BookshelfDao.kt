package top.carljay.book.dao

import com.carljay.cjlibrary.helper.ThreadHelper
import com.carljay.cjlibrary.helper.UiHelper
import top.carljay.book.ClientApplication
import top.carljay.book.bean.BookBean
import top.carljay.book.bean.ChapterBean

/**
 * Created by carljay on 2018/2/2.
 */
class BookshelfDao{
    companion object {
        fun getBookshelfList(f : (ArrayList<BookBean>?) -> Unit){
            ThreadHelper.execute {
                var read = ClientApplication.db.readableDatabase
                var cursor = read.rawQuery("select * from book_shelf",null)
                var datas = ArrayList<BookBean>()
                while (cursor.moveToNext()) {
                    var book = BookBean()
                    val id = cursor.getInt(0) //获取第一列的值,第一列的索引从0开始
                    val title = cursor.getString(1)
                    val img = cursor.getString(2)
                    val synopsis = cursor.getString(3)
                    val url = cursor.getString(4)
                    val chapter_index = cursor.getInt(5)
                    val chapter_pager_index = cursor.getInt(6)
                    val type = cursor.getString(7)
                    val time = cursor.getInt(8)
                    book.id = id
                    book.title = title
                    book.img = img
                    book.synopsis = synopsis
                    book.url = url
                    book.chapter_index = chapter_index
                    book.chapter_pager_index = chapter_pager_index
                    book.type = type
                    book.time = time
                    datas.add(book)
                }
                read.close()
                UiHelper.postThreadOnMain {
                    f(datas)
                }
            }

        }
        fun addBookShelf(book:BookBean):Int{
            var write = ClientApplication.db.writableDatabase
            write.execSQL("insert into book_shelf (title,img,synopsis,url,chapter_index,chapter_pager_index,type) values(?,?,?,?,?,?,?)"
            , arrayOf(book.title,book.img,book.synopsis,book.url,book.chapter_index,book.chapter_pager_index,book.type))
            var cur = ClientApplication.db.readableDatabase.rawQuery("select LAST_INSERT_ROWID() ",null)
            cur.moveToFirst()
            var id = cur.getInt(0)
            write.close()
            return id
        }
        fun addBookChapter(id:Int,chapters:ArrayList<ChapterBean>){
            ThreadHelper.execute {
                var write = ClientApplication.db.writableDatabase
                for (chapter in chapters){
                    write.execSQL("insert into chapter (book_id,title,url) values(?,?,?)"
                            , arrayOf(id,chapter.title,chapter.url))
                }
                write.close()
            }
        }
    }

}