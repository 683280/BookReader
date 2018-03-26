package com.carljay.cjlibrary.defaults

import com.carljay.cjlibrary.interfaces.CJInputHandler

import java.io.InputStream
import java.net.URI
import java.net.URL

/**
 * Created by carljay on 2017/3/15.
 */

class DefaultInputHandler : CJInputHandler {

    override fun getInputStream(uri: URI): InputStream? {
        try {
            val url = uri.toURL()
            return url.openStream()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}
