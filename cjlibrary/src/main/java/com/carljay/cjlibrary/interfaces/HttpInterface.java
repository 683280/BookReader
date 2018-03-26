package com.carljay.cjlibrary.interfaces;

/**
 * Created by carljay on 2017/3/1.
 */

public interface HttpInterface {

    String httpGet(String url);
    String httpPost(String url, String data);
}
