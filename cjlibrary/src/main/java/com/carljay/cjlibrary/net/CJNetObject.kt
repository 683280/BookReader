package com.carljay.cjlibrary.net

import com.carljay.cjlibrary.client.CJNetClient
import com.carljay.cjlibrary.enums.NetType
import com.carljay.cjlibrary.helper.UiHelper
import com.carljay.cjlibrary.uitls.HttpUtils
import java.lang.Exception

import java.lang.reflect.Method
import java.lang.reflect.Type

class CJNetObject(var `object`: Any, var methodName: String) {
    var netObject = NetObject()
    var beanType: Type? = null
    var isMain = true
    var tags: Array<Any>? = null
    fun put(key: String, value: String): CJNetObject {
        netObject.map.put(key, value)
        return this
    }

    fun put(map: Map<String, String>): CJNetObject {
        netObject.map.putAll(map)
        return this
    }

    fun m(type: NetType): CJNetObject {
        this.netObject.methodType = type
        return this
    }

    fun data(data: String): CJNetObject {
        this.netObject.data = data
        return this
    }

    fun data(data: Map<*, *>): CJNetObject {
        this.netObject.data = HttpUtils.mapToHttpData(data)
        return this
    }

    fun with(vararg data: Any): CJNetObject {
        this.tags = arrayOf(data)
        return this
    }

    fun to(type: Type): CJNetObject {
        this.beanType = type
        return this
    }

    fun to(type: Class<*>): CJNetObject {
        this.beanType = type
        return this
    }

    fun on(isMain: Boolean): CJNetObject {
        this.isMain = isMain
        return this
    }

    fun call(): Int {
        return CJNetClient.call(this)
    }

    operator fun invoke(method: Method, data: Any) {
        if (isMain) {
            UiHelper.postThreadOnMain (Runnable { invoke2(method, data) })
        } else {
            invoke2(method, data)
        }

    }

    private fun invoke2(method: Method, data: Any) {
        method.isAccessible = true
        try {
            val length = method.parameterTypes.size
            if (length == 0)
                method.invoke(`object`)
            else if (tags == null) {
                method.invoke(`object`, data)
            } else {
                val objects = arrayOfNulls<Any>(length)
                objects[0] = data
                for (i in 0..length - 1 - 1)
                    objects[i + 1] = tags!![i]
                method.invoke(`object`, *objects)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}