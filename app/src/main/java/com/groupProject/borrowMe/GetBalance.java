package com.groupProject.borrowMe;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetBalance extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://myxstyle120.000webhostapp.com/GetBalance.php";
    private Map<String, String> params;

    public GetBalance(String email, Response.Listener<String> listener) {
        super( Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);




    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
