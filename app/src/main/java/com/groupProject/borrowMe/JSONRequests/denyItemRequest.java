package com.groupProject.borrowMe.JSONRequests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Enache on 19/04/2018.
 * deny an item
 */

public class denyItemRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://myxstyle120.000webhostapp.com/denyItem.php";
    private Map<String, String> params;

    public denyItemRequest(String id, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("item_id", id);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }



}