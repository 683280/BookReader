package com.carljay.cjlibrary.http

import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by carljay on 2018/2/1.
 */
class HttpDownload {
    companion object {

        fun downloadFile(uri: String, file: String) {
            var `is`: InputStream? = null
            var os: OutputStream? = null
            try {
                val url = URL(uri)
                val conn = url.openConnection() as HttpURLConnection
                val contentLength = conn.contentLength
                val length: Int
                if (contentLength == 0) {
                    length = 40 * 1024
                } else {
                    length = contentLength / 100
                }
                val temp = File(file + ".temp")
                if (!temp.exists()) {
                    temp.createNewFile()
                }
                `is` = conn.inputStream
                os = FileOutputStream(temp)
                var len: Int
                val bytes = ByteArray(length)
                len = `is`!!.read(bytes)
                while (len > 0) {
                    os.write(bytes, 0, len)
                    len = `is`!!.read(bytes)
                }
                temp.renameTo(File(file))
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (`is` != null) {
                    try {
                        `is`.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                if (os != null) {
                    try {
                        os.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }
}