package com.carljay.cjlibrary.uitls;

/**
 * Created by carljay on 2017/3/21.
 */

public class LongUtils {

    public static long stringAdd(String ...strs) {
        if (strs == null)
            return 0;
        int i = 0;
        for (String str:strs) {
            if (str == null)
                continue;
            stringToLong(str);
        }
        return i;
    }
    public static long stringToLong(String str){
        try {
            return Long.valueOf(str.trim());
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
