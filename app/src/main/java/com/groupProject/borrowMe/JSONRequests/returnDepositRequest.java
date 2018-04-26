package com.groupProject.borrowMe.JSONRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Enache on 25/04/2018.
 */

public class returnDepositRequest extends StringRequest{

    private static final String REGISTER_REQUEST_URL = "https://myxstyle120.000webhostapp.com/returnDeposit.php";
    private Map<String, String> params;

    public returnDepositRequest(String lender_email,String balance,String money, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", lender_email);
        params.put( "balance", balance );
        params.put( "money", money );

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
