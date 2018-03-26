package top.carljay.book

import android.app.Application
import com.carljay.cjlibrary.client.CJClient
import com.carljay.cjlibrary.helper.HttpHelper
import top.carljay.book.db.DbHelper

/**
 * Created by carljay on 2018/1/30.
 */
class ClientApplication : Application(){
    companion object {
        lateinit var db : DbHelper
    }
    override fun onCreate() {
        super.onCreate()
        db = DbHelper(this)
        CJClient.init(this)
        HttpHelper.httpInterface.headMap.put("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36")
    }

}