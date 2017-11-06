package com.example.mijin.hue;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 다은 on 2017-10-18.
 */

public class LoginRequest extends StringRequest {
    final private static String URL="http://uoshue.dothome.co.kr/Login.php";
    private Map<String, String> parameters;

    public LoginRequest(String ID, String password, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("ID", ID);
        parameters.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}