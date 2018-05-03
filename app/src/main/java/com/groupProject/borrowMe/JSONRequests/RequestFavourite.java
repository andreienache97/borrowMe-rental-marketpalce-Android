package com.groupProject.borrowMe.JSONRequests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Enache on 25/04/2018.
 */

public class RequestFavourite extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "https://myxstyle120.000webhostapp.com/FavouriteDetails.php";
    private Map<String, String> params;

    public RequestFavourite(String email, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
