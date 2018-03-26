package com.carljay.cjlibrary.annotations

import android.support.annotation.IdRes
import android.view.View
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by carljay on 2017/3/6.
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD)
@Retention(RetentionPolicy.RUNTIME)
annotation class OnClick(vararg val value: String = arrayOf(""), @IdRes val id: IntArray = intArrayOf(View.NO_ID))
