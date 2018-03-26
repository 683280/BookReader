package com.carljay.cjlibrary.image

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import android.util.LruCache
import android.widget.ImageView

import com.carljay.cjlibrary.client.CJClient
import com.carljay.cjlibrary.helper.UiHelper
import com.carljay.cjlibrary.helper.DownloadHelper
import com.carljay.cjlibrary.helper.ThreadHelper
import com.carljay.cjlibrary.http.HttpDownload
import com.carljay.cjlibrary.uitls.CodeUtils

import java.io.File
import java.io.InputStream

/**
 * Created by 豪杰 on 2016/12/24.
 */

object CJImageHelper {

    private val lruCache = object : LruCache<String, Bitmap>(1024 * 1024 * 18) {

        //必须重写此方法，来测量Bitmap的大小
        override fun sizeOf(key: String, value: Bitmap): Int {
            return value.rowBytes * value.height
        }
    }
    private var tempDir: File? = null

    fun start(config: CJImageConfig) {
        var bitmap: Bitmap? = null
        val md5: String
        when (config.loadType) {
            1//网络图片
            -> {
                md5 = CodeUtils.md5Password(config.img_url)
                bitmap = getBitmapForMemory(md5)
                val file = getFile(md5)!!
                if (bitmap == null)
                    bitmap = getBitmapForLocal(file)
                if (bitmap == null) {
                    HttpDownload.downloadFile(config.img_url!!, file)
                    bitmap = getBitmapForLocal(file)
                }
            }
            2//本地图片
            -> {
                md5 = CodeUtils.md5Password(config.file)
                bitmap = getBitmapForMemory(md5)
                if (bitmap == null)
                    bitmap = getBitmapForLocal(config.file!!)
            }
            3//资源图片
            -> {
                md5 = CodeUtils.md5Password(config.resId.toString())
                bitmap = getBitmapForMemory(md5)
                if (bitmap == null)
                    getBitmapForRes(config.resId)
            }
        }
        if (bitmap == null) return
        val bitmap1 = bitmap
        ThreadHelper.runOnThread{
            val imageViews = config.imageView
            for (imageView in imageViews!!) {
                //                imageView.setImageDrawable(new CJDrawable(bitmap1));
                imageView.setImageBitmap(bitmap1)
            }
        }
    }

    private fun getBitmapForMemory(md5: String): Bitmap {
        return lruCache.get(md5)
    }

    private fun getBitmapForLocal(file: String): Bitmap? {

        if (File(file).exists()) {
            return BitmapFactory.decodeFile(file)
        }
        return null
    }

    private fun getBitmapForRes(resId: Int): Bitmap? {
        if (resId != 0) {
            //            BitmapRegionDecoder
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.RGB_565
            //            options.inJustDecodeBounds = false;
            options.inSampleSize = 2   //width，hight设为原来的十分一
            //            options.inPurgeable = true;
            //            options.inInputShareable = true;
            return readBitMap(UiHelper.context, resId)
        } else
            return null
    }

    fun readBitMap(context: Context, resId: Int): Bitmap {
        val opt = BitmapFactory.Options()
        opt.inPreferredConfig = Bitmap.Config.RGB_565
        opt.inPurgeable = true
        opt.inInputShareable = true
        // 获取资源图片
        val `is` = context.resources.openRawResource(resId)
        return BitmapFactory.decodeStream(`is`, null, opt)
    }

    fun decodeSampledBitmapFromResource(res: Resources, resId: Int,
                                        reqWidth: Int, reqHeight: Int): Bitmap {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

    fun calculateInSampleSize(options: BitmapFactory.Options,
                              reqWidth: Int, reqHeight: Int): Int {
        // 源图片的高度和宽度
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        return inSampleSize
    }

    private fun getFile(url: String): String? {
        if (tempDir == null) {

            val cacheDir = CJClient.getContent().cacheDir
            tempDir = File(cacheDir, "temp")
            if (!tempDir!!.exists())
                tempDir!!.mkdir()
        }
        val s = CodeUtils.md5Password(url) ?: return null
        return tempDir!!.absolutePath + "/" + s
    }
}
