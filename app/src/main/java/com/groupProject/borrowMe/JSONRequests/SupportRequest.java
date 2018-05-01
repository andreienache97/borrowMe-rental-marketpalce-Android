package com.groupProject.borrowMe.JSONRequests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Diana on 4/25/2018.
 */

public class SupportRequest extends StringRequest {
    private static final String SUPPORT_REQUEST_URL = "https://myxstyle120.000webhostapp.com/SubmitTicket.php";
    private Map<String, String> params;

    public SupportRequest(String email, String type, String message, Response.Listener<String> listener) {
        super(Request.Method.POST, SUPPORT_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email",email);
        params.put("type", type);
        params.put("message", message);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
