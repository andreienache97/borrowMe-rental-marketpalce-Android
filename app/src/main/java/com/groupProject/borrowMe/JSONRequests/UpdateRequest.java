package com.groupProject.borrowMe.JSONRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://myxstyle120.000webhostapp.com/updateUser.php";
    private Map<String, String> params;

    public UpdateRequest(String email, String password, String name, String phone,String address,String city,String postcode, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("name", name);
        params.put("phone", phone );
        params.put("address", address);
        params.put("city", city);
        params.put("postcode", postcode);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

