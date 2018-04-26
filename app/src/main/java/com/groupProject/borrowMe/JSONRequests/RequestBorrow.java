package com.groupProject.borrowMe.JSONRequests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Enache on 25/04/2018.
 */

public class RequestBorrow extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "https://myxstyle120.000webhostapp.com/borrowDetails.php";
    private Map<String, String> params;

    public RequestBorrow(String borrow_id, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Borrow_ID", borrow_id);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
