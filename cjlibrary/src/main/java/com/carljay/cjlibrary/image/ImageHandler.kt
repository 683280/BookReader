package com.carljay.cjlibrary.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Message
import android.util.LruCache
import android.widget.ImageView

import com.carljay.cjlibrary.defaults.DefaultInputHandler
import com.carljay.cjlibrary.helper.ThreadHelper
import com.carljay.cjlibrary.interfaces.CJInputHandler
import com.carljay.cjlibrary.uitls.CodeUtils

import java.io.InputStream
import java.net.URI
import java.util.Collections
import java.util.LinkedList

/**
 * Created by carljay on 2017/3/15.
 */

class ImageHandler private constructor() : Handler(), Runnable {

    //同步的List
    private val msg = Collections.synchronizedList(LinkedList<ImageObject>())

    private var inputHandler: CJInputHandler? = DefaultInputHandler()

    init {
        //图片下载线程数
        for (i in 0..THREAD_SIZE) {
            ThreadHelper.execute(this)
        }
    }

    //处理ImageObject
    private fun handleImageObject(obj: ImageObject) {
        val bitmap = getBitmapForUri(obj.uri)
        lruCache.put(obj.toString(), bitmap)
        val message = Message.obtain()
        message.obj = obj
        sendMessage(message)
    }

    private fun getBitmapForUri(uri: URI): Bitmap? {
        var bitmap : Bitmap? = lruCache.get(CodeUtils.md5Password(uri.toString()))
        if (bitmap == null) {
            if (inputHandler == null)
                return bitmap
            val `is` = inputHandler!!.getInputStream(uri) ?: return bitmap
            bitmap = getBitmapForInputStream(`is`)
        }
        return bitmap
    }

    private fun getBitmapForInputStream(`is`: InputStream): Bitmap? {
        return BitmapFactory.decodeStream(`is`)
    }

    @Synchronized override fun run() = synchronized(lock) {
        while (true) {
            synchronized(lock) {
                if (msg.size > 0) {
                    val imageObject = msg[0]
                    msg.removeAt(0)
                    handleImageObject(imageObject)
                } else {
                    try {
                        lock.wait()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }

    override fun handleMessage(msg: Message) {
        val obj = msg.obj as ImageObject
        val bitmap = lruCache.get(obj.toString())
        if (bitmap != null) {
            for (iv in obj.imageViews!!) {
                iv.setImageBitmap(bitmap)
            }
        }
    }

    companion object {

        private val mInstance = ImageHandler()

        private val lock = java.lang.Object()

        private val THREAD_SIZE = 4

        private val lruCache = object : LruCache<String, Bitmap>(1024 * 1024 * 18) {

            //必须重写此方法，来测量Bitmap的大小
            override fun sizeOf(key: String, value: Bitmap): Int {
                return value.rowBytes * value.height
            }
        }

        @Synchronized fun handle(obj: ImageObject) = synchronized(lock) {
            synchronized(lock) {
                try {
                    lock.notifyAll()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                mInstance.msg.add(0, obj)
            }
        }

        fun setInputHandler(inputHandler: CJInputHandler) {
            mInstance.inputHandler = inputHandler
        }
    }
}
