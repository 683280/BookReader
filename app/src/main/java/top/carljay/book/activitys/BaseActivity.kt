package top.carljay.book.activitys

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by carljay on 2018/2/1.
 */
open class BaseActivity : AppCompatActivity(){
    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView()
        initView()
        initData()
    }

    open fun initData() {

    }

    open fun initView() {

    }

    open fun initContentView(){

    }
}