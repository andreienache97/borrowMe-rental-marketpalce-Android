package com.groupProject.borrowMe.JSONRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Enache on 25/04/2018.
 * update item details
 */

public class UpdateItemRequest  extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://myxstyle120.000webhostapp.com/updateItem.php";
    private Map<String, String> params;

    public UpdateItemRequest(String title, String price, String description,String item_id, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", title);
        params.put("price", price);
        params.put("Description", description);
        params.put("item_id", item_id);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
