package com.carljay.cjlibrary.interfaces

import java.io.InputStream
import java.net.URI

/**
 * Created by carljay on 2017/3/15.
 */

interface CJInputHandler {
    fun getInputStream(uri: URI): InputStream?
}
