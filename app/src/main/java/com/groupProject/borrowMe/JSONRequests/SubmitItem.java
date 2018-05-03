/* Author: Lau Tsz Chung
 * Submit borrow request
 * */
package com.groupProject.borrowMe.JSONRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SubmitItem extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://myxstyle120.000webhostapp.com/SubmitItem.php";
    private Map<String, String> params;

    public SubmitItem(String Lemail,String Bemail, String id, String ADate,String UDate, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("item_id", id);
        params.put("Lemail", Lemail);
        params.put("Bemail", Bemail);
        params.put("ADate", ADate);
        params.put("UDate", UDate);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
