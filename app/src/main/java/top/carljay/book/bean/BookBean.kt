package top.carljay.book.bean

import java.io.Serializable

/**
 * Created by carljay on 2018/1/30.
 */
open class BookBean : Serializable {
    var id = 0
    var img : String = "" //图片
    var title : String = "" // 书名
    var synopsis : String = "" //简介
    var lastChapter : String = "" // 最新的章节
    var type = "" //采集的网站
    var author = ""//作者
    var url = "" //详情地址
    var chapterList : ArrayList<ChapterBean>? = null
    var status : String = ""
    var time = 0
    var chapter_index = 0
    var chapter_pager_index = 0
}