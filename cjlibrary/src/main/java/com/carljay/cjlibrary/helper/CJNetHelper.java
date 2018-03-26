package com.carljay.cjlibrary.helper;

import com.carljay.cjlibrary.net.CJNetObject;

/**
 * Created by carljay on 2017/3/1.
 */

public class CJNetHelper {

    public static CJNetObject get(Object object, String method){
        return new CJNetObject(object,method);
    }

}
