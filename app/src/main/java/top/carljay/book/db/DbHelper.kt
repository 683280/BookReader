package top.carljay.book.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by carljay on 2018/2/2.
 */
class DbHelper (context:Context) : SQLiteOpenHelper(context,"book",null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS book_shelf (id integer primary key autoincrement,title varchar(16),img varchar(50),synopsis varchar(100),url varchar(50),chapter_index int,chapter_pager_index int,type varchar(8), time timestamp)")
        db?.execSQL("CREATE TABLE IF NOT EXISTS chapter (id integer primary key autoincrement,book_id int,title varchar(20),url varchar(30))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}