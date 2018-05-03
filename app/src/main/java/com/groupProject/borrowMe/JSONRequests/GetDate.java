/* Author: Lau Tsz Chung
* get the dates if the user
*/
package com.groupProject.borrowMe.JSONRequests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetDate extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://myxstyle120.000webhostapp.com/GetDate.php";
    private Map<String, String> params;

    public GetDate(String item_id, Response.Listener<String> listener) {
        super( Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("item_id", item_id);




    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
