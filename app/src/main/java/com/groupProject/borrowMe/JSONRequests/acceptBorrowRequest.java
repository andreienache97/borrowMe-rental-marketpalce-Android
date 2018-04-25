package com.groupProject.borrowMe.JSONRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Enache on 25/04/2018.
 */

public class acceptBorrowRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://myxstyle120.000webhostapp.com/acceptBorrowRequest.php";
    private Map<String, String> params;

    public acceptBorrowRequest(String borrow_id,String item_id, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Borrow_ID", borrow_id);
        params.put("item_id", item_id);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
