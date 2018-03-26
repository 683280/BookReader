package com.carljay.cjlibrary.uitls;

/**
 * Created by carljay on 2017/3/21.
 */

public class IntegerUtils {
    public static int stringAdd(String ...strs) {
        if (strs == null)
            return 0;
        int i = 0;
        for (String str:strs) {
            if (str == null)
                continue;
            stringToInt(str);
        }
        return i;
    }
    public static int stringToInt(String str){
        try {
            return Integer.valueOf(str.trim());
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
