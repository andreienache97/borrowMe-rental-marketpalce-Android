package com.groupProject.borrowMe.JSONRequests;



import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class GetMessagesRequest extends StringRequest {

    private static final String GET_MESSAGES_REQUEST_URL = "https://myxstyle120.000webhostapp.com/get_messages.php?email=%1$s";

    public GetMessagesRequest(String email, Response.Listener<String> listener) {
        super(Request.Method.GET, String.format(GET_MESSAGES_REQUEST_URL, email), listener, null);
    }
}
