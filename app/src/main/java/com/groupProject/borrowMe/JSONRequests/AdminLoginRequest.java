package com.groupProject.borrowMe.JSONRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AdminLoginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://myxstyle120.000webhostapp.com/admin.php";
    private Map<String, String> params;

    public AdminLoginRequest(String name, String password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
