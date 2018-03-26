package com.carljay.cjlibrary.client

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.View
import com.carljay.cjlibrary.annotations.*
import com.carljay.cjlibrary.defaults.DeclaredOnClickListener
import com.carljay.cjlibrary.helper.CJNetHelper
import com.carljay.cjlibrary.helper.UiHelper
import com.danikula.videocache.HttpProxyCacheServer
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Created by 豪杰 on 2016/12/24.
 */
class CJClient {

     companion object{
         var mContext: Context? = null
         var proxy: HttpProxyCacheServer? = null

         fun getProxy1(): HttpProxyCacheServer {
             if (proxy == null) proxy = newProxy()
             return proxy!!
         }
         fun getContent():Context{
             return mContext!!
         }

         private fun newProxy(): HttpProxyCacheServer {
             return HttpProxyCacheServer(mContext)
         }

         fun init(context: Context) {
             mContext = context
             UiHelper.initHandler()
         }

         fun bind(activity: Activity) {
             bind(activity, activity.window.decorView)
         }

         fun unbind(`object`: Any) {
             CJNetClient.unbind(`object`)
         }

         fun bind(`object`: Any?, view: View?) {
             if (`object` == null || view == null)
                 return
             val clazz = `object`.javaClass
             val methods = clazz.getDeclaredMethods()
             val fields = clazz.getDeclaredFields()
             bindMethod(methods, `object`, view)
             bindField(fields, `object`, view)
         }

         private fun bindMethod(methods: Array<Method>?, `object`: Any, view: View) {
             if (methods != null && methods.size != 0) {
                 for (method in methods) {
                     val get = method.getAnnotation<CJNetInterface>(CJNetInterface::class.java)
                     val onClick = method.getAnnotation<OnClick>(OnClick::class.java)
                     if (get != null) {
                         CJNetClient.addNetObject(`object`, method, get)
                     }
                     if (onClick != null) {
                         val values = onClick.id
                         val tag = onClick.value
                         for (i in values) {
                             val v = view.findViewById(i) ?: continue
                             v.setOnClickListener(DeclaredOnClickListener(view, method.name))
                         }
                         var x = 0
                         for (i in tag) {
                             val v = view.findViewWithTag(i) ?: continue
                             v.id = x++
                             v.setOnClickListener(DeclaredOnClickListener(view, method.name))
                         }
                     }
                 }
             }
         }

         private fun bindField(fields: Array<Field>?, `object`: Any, view: View) {
             if (fields != null && fields.size != 0) {
                 val clazz = View::class.java
                 for (field in fields) {
                     if (!clazz!!.isAssignableFrom(field.type)) {
                         continue
                     }
                     //                field.getDeclaringClass()
                     val bindView = field.getAnnotation<BindView>(BindView::class.java)
                     field.isAccessible = true
                     val v: View?
                     if (bindView != null) {
                         v = view.findViewById(bindView.value)
                     } else {
                         v = view.findViewWithTag(field.name)
                     }
                     if (v == null)
                         continue
                     try {
                         field.set(`object`, v)
                     } catch (e: IllegalAccessException) {
                         e.printStackTrace()
                     }

                 }
             }
         }
    }
}