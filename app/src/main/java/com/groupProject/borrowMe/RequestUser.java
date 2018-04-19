package com.groupProject.borrowMe;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Enache on 18/04/2018.
 */

public class RequestUser extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "https://myxstyle120.000webhostapp.com/getUser.php";
    private Map<String, String> params;

    public RequestUser(String email, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

