package top.carljay.book.activitys

import android.content.Intent
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import top.carljay.book.R
import top.carljay.book.adapter.MainPagerAdapter

class MainActivity : BaseActivity(){
    var adapter = MainPagerAdapter(supportFragmentManager)

    override fun initContentView(){
        setContentView(R.layout.activity_main)
    }

    override fun initView() {
        tab_layout.setupWithViewPager(vp_view_page)
        vp_view_page.adapter = adapter
        nested_scroll_view.isFillViewport = true
        collapsing_tolbar_layout.isTitleEnabled = false
        setupToolbar()
    }
    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            supportActionBar!!.setTitle(R.string.app_name)
            toolbar.setOnMenuItemClickListener { menu ->
                when(menu.itemId){
                    R.id.search->{
                        startActivity(Intent(this,SearchActivity::class.java))
                    }
                }
                true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return menuInflater.inflate(R.menu.menu_main,menu) != null
    }
}
