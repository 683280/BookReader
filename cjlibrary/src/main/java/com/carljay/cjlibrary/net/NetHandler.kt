package com.carljay.cjlibrary.net


import com.carljay.cjlibrary.enums.NetType
import com.carljay.cjlibrary.helper.HttpHelper
import com.carljay.cjlibrary.uitls.JsonUtils
import com.carljay.cjlibrary.uitls.TextUtils

import java.lang.reflect.Type

/**
 * Created by carljay on 2017/3/3.
 */

class NetHandler {

    companion object {

    fun netToObject(`object`: NetObject, type: Type?): Any? {
        val data = netToString(`object`)
        if (TextUtils.isEmpty(data)) {
            return null
        } else {
            if (type == null)
                return data
            if (type === String::class)
                return data
            return JsonUtils.jsonToBean<Any>(type, data)
        }
    }

    fun netToString(obj: NetObject): String? {
        when (obj.methodType) {
            NetType.GET -> return HttpHelper.httpGet(obj.getUrl())
            NetType.POST -> return HttpHelper.httpPost(obj.getUrl(), obj.data)
            else -> return null
        }
    }
    }
}
