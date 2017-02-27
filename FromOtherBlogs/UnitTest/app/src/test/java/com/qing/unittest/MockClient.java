package com.qing.unittest;


import android.net.Uri;

import java.io.IOException;

/**
 * Created by qing on 27/02/17.
 */

public class MockClient implements Client {
    @Override
    public Response execute(Request request) throws IOException {
        Uri uri = Uri.parse(request.getUrl());
        String responseString = "";
        if(uri.getPath().equals("/path/of/interest")) {
            responseString = "返回的json1";//这里是设置返回值
        } else {
            responseString = "返回的json2";
        }
        return new Response(request.getUrl(), 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json", responseString.getBytes()));
    }
}
