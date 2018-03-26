package com.carljay.cjlibrary.defaults

import com.carljay.cjlibrary.interfaces.HttpInterface
import com.carljay.cjlibrary.other.MyString

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by carljay on 2017/3/1.
 */

class DefaultHttpInterface : HttpInterface {

    val headMap = HashMap<String,String>()
    override fun httpGet(urlAddress: String): String? {
        try {
            val url = URL(urlAddress)

            val conn = url.openConnection() as HttpURLConnection
            conn.connectTimeout = 30 * 1000
            for (i in headMap.entries){
                conn.addRequestProperty(i.key,i.value)
            }
            val `is` = conn.inputStream
            val ms = MyString()
            var len: Int
            val bytes = ByteArray(10 * 1024)
            len = `is`.read(bytes)
            while (len > 0) {
                ms.append(bytes, 0, len)
                len = `is`.read(bytes)
            }
            return ms.toString()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    override fun httpPost(urlAddress: String, data: String): String? {
        try {
            val url = URL(urlAddress)// 创建连接
            val connection = url
                    .openConnection() as HttpURLConnection
            connection.doOutput = true
            connection.doInput = true
            connection.useCaches = false
            connection.instanceFollowRedirects = true
            connection.requestMethod = "POST" // 设置请求方式
            connection.setRequestProperty("Connection", "Keep-Alive")
            connection.setRequestProperty("Charset", "UTF-8")
            connection.setRequestProperty("Accept", "application/json;charset=UTF-8") // 设置接收数据的格式
            connection.setRequestProperty("Content-length", data.toByteArray().size.toString() + "") // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8") // 设置发送数据的格式
            connection.connect()

            val out = connection.outputStream // utf-8编码
            out.write(data.toByteArray())
            out.flush()

            val code = connection.responseCode
            if (code != 200)
                return null
            // 读取响应
            //            int length = (int) connection.getContentLength();// 获取长度
            val `is` = connection.inputStream

            val `in` = BufferedReader(InputStreamReader(`is`))
            val sb = StringBuffer("")
            var line: String
            val NL = System.getProperty("line.separator")
            line = `in`.readLine()
            while (line != null) {
                sb.append(line + NL)
                line = `in`.readLine()
            }
            `in`.close()
            out.close()
            return sb.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }
}
