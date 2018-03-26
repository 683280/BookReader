package com.carljay.cjlibrary.client

import com.carljay.cjlibrary.annotations.CJNetInterface
import com.carljay.cjlibrary.helper.ThreadHelper
import com.carljay.cjlibrary.net.CJNetObject
import com.carljay.cjlibrary.net.NetHandler
import com.carljay.cjlibrary.uitls.TextUtils

import java.lang.reflect.Method
import java.lang.reflect.Type
import java.util.HashMap

/**
 * Created by carljay on 2017/3/1.
 */

class CJNetClient {

    companion object{
    internal var maps: MutableMap<Any, MutableMap<String, CJObject>> = HashMap()




    fun addNetObject(obj: Any, method: Method, cjNetInterface: CJNetInterface) {
        var map: MutableMap<String, CJObject>? = maps[obj]
        if (map == null) {
            map = HashMap<String, CJObject>()
            maps.put(obj, map)
        }
        val o = CJObject()
        val types = method.parameterTypes
        if (types.size == 0) {

        } else {
            o.clazzName = types[0].name
        }
        o.inter = cjNetInterface
        o.method = method
        var name = cjNetInterface.name
        if (TextUtils.isEmpty(name)) {
            name = method.name
        }
        map.put(name, o)
    }

    fun unbind(`object`: Any) {
        maps.remove(`object`)
    }

    fun call(netObject: CJNetObject): Int {
        val map = maps[netObject.`object`] ?: return -1
        val obj = map[netObject.methodName] ?: return -2
        var type1: Type? = netObject.beanType
        if (type1 == null) {
            try {
                type1 = Class.forName(obj.clazzName)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }

        }
        val type = type1
        netObject.netObject.url = obj.inter!!.url
        netObject.netObject.methodType = obj.inter!!.type
        ThreadHelper.execute {
            val data = NetHandler.netToObject(netObject.netObject, type)
            netObject.invoke(obj.method!!, data!!)

        }
        return 0
    }
    }
    class CJObject {
        internal var method: Method? = null
        internal var inter: CJNetInterface? = null
        internal var clazzName: String? = null
    }

}
