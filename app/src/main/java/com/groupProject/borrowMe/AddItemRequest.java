package com.groupProject.borrowMe;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddItemRequest extends StringRequest{

    private static final String REGISTER_REQUEST_URL = "https://myxstyle120.000webhostapp.com/Additem.php";
    private Map<String, String> params;

    public AddItemRequest(String email,String name, String price, String des, String ADate, String UDate, String department, Response.Listener<String> listener) {
            super( Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
//        params.put("email", email);
//        params.put("Name", name);
//        params.put("Des", des );
//        params.put("Price",price);
//        params.put("ADate", ADate);
//        params.put("UDate", UDate);
//        params.put("department", department);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
