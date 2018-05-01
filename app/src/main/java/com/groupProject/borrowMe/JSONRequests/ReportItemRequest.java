package com.groupProject.borrowMe.JSONRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//Report Item
//Author: Sebastian Arocha

//Create Class
public class ReportItemRequest extends StringRequest{
    //Declare and Initialize URL
    private static final String REPORT_REQUEST_URL = "https://myxstyle120.000webhostapp.com/ReportItem.php";
    //Mapping of the parameters
    private Map<String, String> params;

    public ReportItemRequest(int item_id,String reporterEmail,String reason, Response.Listener<String> listener) {
        super( Method.POST, REPORT_REQUEST_URL, listener, null );
        params = new HashMap<>();
        params.put( "item_id",String.valueOf(item_id) );
        params.put( "reporterEmail", reporterEmail );
        params.put( "reason", reason );
    }

    @Override
    public Map<String, String> getParams() {return params;}
}
