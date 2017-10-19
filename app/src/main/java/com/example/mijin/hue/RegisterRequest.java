package com.example.mijin.hue;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 다은 on 2017-10-18.
 */

public class RegisterRequest extends StringRequest {
    final private static String URL="http://uoshue.dothome.co.kr/Register.php";
    private Map<String, String> parameters;

    public RegisterRequest(String ID, String password, String email, String name, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("ID", ID);
        parameters.put("password", password);
        parameters.put("email", email);
        parameters.put("name", name);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}