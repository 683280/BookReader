package com.carljay.cjlibrary.image

import android.widget.ImageView

import com.carljay.cjlibrary.R
import com.carljay.cjlibrary.helper.ThreadHelper

/**
 * Created by 豪杰 on 2016/12/24.
 */

class CJImageConfig {

    internal var img_url: String? = null
    internal var resId: Int = 0
    internal var file: String? = null
    var imageView: MutableList<ImageView>? = null
        private set

    internal var loadType = 0

    fun to(vararg imageViews: ImageView) {
        this.imageView = imageViews.toMutableList()
        for (imageView in imageViews) {
            imageView.setImageResource(R.color.material_white)
        }

        ThreadHelper.execute{ CJImageHelper.start(this) }
    }

    fun load(url: String): CJImageConfig {
        img_url = url
        loadType = 1
        return this
    }

    fun loadFile(url: String): CJImageConfig {
        file = url
        loadType = 2
        return this
    }

    fun load(resId: Int): CJImageConfig {
        this.resId = resId
        loadType = 3
        return this
    }
}
