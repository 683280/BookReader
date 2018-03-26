package top.carljay.book.bean

import com.carljay.cjlibrary.uitls.JsonUtils

import java.io.Serializable

class ResponseBean<T> : Serializable {
    var isSuccess: Boolean = false
    var msg: String? = null
    var data: T? = null
    override fun toString(): String {
        return JsonUtils.objectToJson(this)
    }
}
