package com.groupProject.borrowMe.JSONRequests;


import com.android.volley.toolbox.StringRequest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddMessageRequest extends StringRequest {

    private static final String ADD_MESSAGE_REQUEST_URL =  "https://myxstyle120.000webhostapp.com/add_message.php";
    private Map<String, String> params;

    public AddMessageRequest(String email_from, String email_to, String message, String itemId, Response.Listener<String> listener) {
        super( Method.POST, ADD_MESSAGE_REQUEST_URL, listener, null );
        params = new HashMap<>();
        params.put( "from", email_from );
        params.put( "to", email_to );
        params.put( "message", message );
        params.put( "item_id", itemId);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
