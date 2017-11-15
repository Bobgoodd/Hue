package com.example.mijin.hue;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Network-LAB-taeu on 2017-11-13.
 */

public class LoginRequest extends StringRequest {
    final static private String url = "http://uoshue.dothome.co.kr/loginH.php";
    private Map<String, String> parameters;

    public LoginRequest(String userId, String userPassword, Response.Listener<String> listener){
        super(Method.POST, url, listener, null);
        parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("userPassword", userPassword);
    }

    public Map<String, String> getParams(){
        return parameters;
    }
}

