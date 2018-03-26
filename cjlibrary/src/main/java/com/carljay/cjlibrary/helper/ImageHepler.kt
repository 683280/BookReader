package com.carljay.cjlibrary.helper

import com.carljay.cjlibrary.image.ImageObject
import java.net.URI

/**
 * Created by carljay on 2018/2/1.
 */


class ImageHepler{
    var content = UiHelper.context
    companion object {

        fun load(uri: String): ImageObject {
            return ImageObject(uri)
        }

        fun load(uri: URI): ImageObject {
            return ImageObject(uri)
        }
    }
}