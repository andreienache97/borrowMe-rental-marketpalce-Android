package com.groupProject.borrowMe.JSONRequests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Top_upRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://myxstyle120.000webhostapp.com/TopUp.php";
    private Map<String, String> params;

    public Top_upRequest(String email,String balance, String money, Response.Listener<String> listener) {
        super( Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put( "balance", balance );
        params.put( "money", money );



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
