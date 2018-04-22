package com.groupProject.borrowMe;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Insert_KeywordRequests extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://myxstyle120.000webhostapp.com/InsertKeywords.php";
    private Map<String, String> params;

    Insert_KeywordRequests(String email, String item_name, String item_price, String item_Des, String Keyword_one, String Keyword_two, String Keyword_three, String Keyword_four, Response.Listener<String> listener) {
        super( Method.POST, REGISTER_REQUEST_URL, listener, null );
        params = new HashMap<>();
        params.put( "email", email );
        params.put( "Name", item_name );
        params.put( "Price", item_price );
        params.put( "Des", item_Des );
        params.put( "Keyword_one", Keyword_one );
        params.put( "Keyword_two", Keyword_two );
        params.put( "Keyword_three", Keyword_three );
        params.put( "Keyword_four", Keyword_four );

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
