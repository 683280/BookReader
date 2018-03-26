package com.carljay.cjlibrary.image

import android.widget.ImageView
import com.carljay.cjlibrary.uitls.CodeUtils
import java.net.URI

/**
 * Created by carljay on 2017/3/15.
 */

class ImageObject{
    var imageViews: List<ImageView>? = null
    var drawable: CJImageDrawable? = null
    var uri : URI

    constructor(url: String){
        uri = URI.create(url)
    }
    constructor(uri: URI){
        this.uri = uri
    }

    fun to(vararg imageView: ImageView) {
        this.imageViews = imageView.toList()
        ImageHandler.handle(this)
    }

    override fun toString(): String {
        return CodeUtils.md5Password(uri.toString())
    }
}
