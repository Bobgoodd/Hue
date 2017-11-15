package com.example.mijin.hue;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Network-LAB-taeu on 2017-11-13.
 */

public class RegisterRequest extends StringRequest {
    final static private String url = "http://uoshue.dothome.co.kr/registerH.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userId, String userEmail, String userName, String userPassword, Response.Listener<String> listener){
        super(Method.POST, url, listener, null);
        parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("userEmail", userEmail);
        parameters.put("userName", userName);
        parameters.put("userPassword", userPassword);
    }

    public Map<String, String> getParams(){
        return parameters;
    }
}

