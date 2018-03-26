package com.carljay.cjlibrary.http;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by carljay on 2017/3/13.
 */

public class HttpHandler {

    public static void httpPost(HttpObject object){
        try {
            URI uri = new URI(object.url);
            uri.getHost();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
