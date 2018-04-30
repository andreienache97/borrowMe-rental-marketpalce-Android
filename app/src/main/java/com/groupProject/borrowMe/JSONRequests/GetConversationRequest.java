package com.groupProject.borrowMe.JSONRequests;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class GetConversationRequest extends StringRequest {

    private static final String GET_MESSAGES_REQUEST_URL = "https://myxstyle120.000webhostapp.com/get_conversation.php";

    public GetConversationRequest(String email1, String email2, String itemId, Response.Listener<String> listener) {
        super(Request.Method.GET, String.format(GET_MESSAGES_REQUEST_URL, email1, email2, itemId), listener, null);
    }
}

