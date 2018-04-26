package com.groupProject.borrowMe.JSONRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Enache on 25/04/2018.
 */

public class RequestItemName extends StringRequest{

    private static final String LOGIN_REQUEST_URL = "https://myxstyle120.000webhostapp.com/getItemName.php";
    private Map<String, String> params;

    public RequestItemName(String item_id, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("item_id", item_id);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
