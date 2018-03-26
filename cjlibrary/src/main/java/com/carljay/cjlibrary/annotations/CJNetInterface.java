package com.carljay.cjlibrary.annotations;

import com.carljay.cjlibrary.enums.NetType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by carljay on 2017/3/1.
 */
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CJNetInterface{
    String name() default "";
    String url() ;
    NetType type() default NetType.GET;

}
