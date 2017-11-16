package com.example.mijin.hue;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 다은 on 2017-11-16.
 */

public class IdCheckRequest extends StringRequest {
    final static private String url = "http://uoshue.dothome.co.kr/idCheckJ.php";
    private Map<String, String> parameters;

    public IdCheckRequest(String userId, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        parameters = new HashMap<>();
        parameters.put("userId", userId);
    }

    public Map<String, String> getParams(){
        return parameters;
    }
}
