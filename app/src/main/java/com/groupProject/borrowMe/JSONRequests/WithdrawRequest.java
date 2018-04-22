package com.groupProject.borrowMe.JSONRequests;

/**
 * Created by Enache on 22/04/2018.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class WithdrawRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://myxstyle120.000webhostapp.com/withdraw.php";
    private Map<String, String> params;

    public WithdrawRequest(String email,String balance, String money, Response.Listener<String> listener) {
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
