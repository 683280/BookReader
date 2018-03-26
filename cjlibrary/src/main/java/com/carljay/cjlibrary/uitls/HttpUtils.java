package com.carljay.cjlibrary.uitls;

import java.util.Map;

/**
 * Created by carljay on 2017/3/2.
 */

public class HttpUtils {

    public static String mapToHttpData( Map map) {
        String url= "";
        if (map != null) {
            int i = 0;
            for (Object o : map.keySet()) {
                String s;
                if (i == 0)
                    s = "";
                else
                    s = "&";
                i = 1;
                url += s + o.toString() + "=" + map.get(o).toString();
            }
        }
        url.replace(" ","%20");

        return url;
    }
    public static String mosaicUrl(String url,String data){
        return url.indexOf("?") == -1 ? url + "?" + data : url + "&" + data;
    }
    public static String mosaicUrl(String url,Map map){
        String data = mapToHttpData(map);
        return mosaicUrl(url,data);
    }
}
