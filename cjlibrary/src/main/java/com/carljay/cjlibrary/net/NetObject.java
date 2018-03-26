package com.carljay.cjlibrary.net;

import com.carljay.cjlibrary.enums.NetType;
import com.carljay.cjlibrary.uitls.HttpUtils;
import com.carljay.cjlibrary.uitls.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NetObject{
    public static void test(){}
        public NetType methodType = NetType.GET;
        public String url;
        String data;
        Map<String,String> map = new HashMap<>();
        public String getUrl() {
                String url = replaceUrl(this.url);
                return TextUtils.isEmpty(data) ? url : methodType == NetType.GET ? HttpUtils.mosaicUrl(url,data) : url;
        }
        public String replaceUrl(String url){
            Set<String> set = map.keySet();
            for (String key:set) {
                String value = map.get(key);
                key = "{" + key + "}";
                url = url.replace(key,value);
            }
            return url;
        }
}