package com.carljay.cjlibrary.helper

import android.util.Log

import com.carljay.cjlibrary.defaults.DefaultHttpInterface
import com.carljay.cjlibrary.uitls.JsonUtils
import com.google.gson.reflect.TypeToken

import java.lang.reflect.Type

/**
 * Created by 豪杰 on 2016/12/9.
 */

object HttpHelper {

    val httpInterface = DefaultHttpInterface()

    fun httpGet(urlAddress: String,datas : Array<Any>? = null): String? {
        var u = if (datas == null) urlAddress else urlMosaic(urlAddress,datas)
        Log.e("httpGet", u)
        return httpInterface.httpGet(u)
    }

    fun httpPost(urlAddress: String, data: String): String? {
        return httpInterface.httpPost(urlAddress, data)
    }

    fun httpGetBean(url: String, callBack: HttpBeanCallBack<*>?) {
        ThreadHelper.execute {
            var data = httpGet(url)
            if (callBack != null) {
                UiHelper.postThreadOnMain(Runnable {
                    if (data == null) {
                        callBack.onFailCallback("连接失败！")
                    } else {
                        callBack.onData(data!!)
                    }
                    callBack.onEnd()
                })
            }
        }
    }
    fun httpGetBean(url: String,datas : Array<Any>, callBack: HttpBeanCallBack<*>?) {
        var u = urlMosaic(url,datas)
        httpGetBean(u,callBack)
    }
    fun urlMosaic(url: String,datas : Array<Any>):String{
        var u : String = url
        for (data in datas){
            u = u.replaceFirst("&?",data.toString())
        }
        return u
    }
    fun httpGetBeanSync(url: String, type: Type): Any? {
        val data = httpGet(url)
        if (data == null) {
            return null
        } else {
            return JsonUtils.jsonToBean<Type>(type, data)
        }
    }

    fun httpPostBeanSync(url: String, re: String, type: Type): Any? {
        val data = httpPost(url, re)
        if (data == null) {
            return null
        } else {
            return JsonUtils.jsonToBean<Any>(type, data)
        }
    }

    fun httpGetSync(url: String, callback: HttpCallback?) {
        ThreadHelper.execute {
            val data = httpGet(url)
            if (callback != null)
                UiHelper.postThreadOnMain (Runnable{
                    if (data == null) callback.onFailCallback() else callback.onSuccessCallback(data)
                    callback.onEnd()
                })
        }
    }

    abstract class HttpBeanCallBack<T> : TypeToken<T>() {

        abstract fun onSuccess(data: T)
        fun onData(data: String){
            var t = JsonUtils.jsonToBean<T>(type,data)
            if (t == null)
                onFailCallback("数据解析失败！")
            else
                onSuccess(t)

        }
        open fun onFailCallback(error: String) {}
        open fun onEnd() {}
    }

    abstract class HttpCallback {
        abstract fun onSuccessCallback(data: String)
        fun onFailCallback() {}
        fun onEnd() {}
    }
}
