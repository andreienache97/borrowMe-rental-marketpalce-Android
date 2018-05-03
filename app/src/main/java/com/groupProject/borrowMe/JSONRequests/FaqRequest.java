package com.groupProject.borrowMe.JSONRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Diana on 5/1/2018.
 * Submit the questions to database
 */

public class FaqRequest extends StringRequest {
    private static final String FAQ_REQUEST_URL = "https://myxstyle120.000webhostapp.com/Faq.php";
    private Map<String, String> params;

    public FaqRequest(String email, String question, Response.Listener<String> listener) {
        super(Method.POST, FAQ_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("question",question);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

