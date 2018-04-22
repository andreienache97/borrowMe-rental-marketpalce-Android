package com.groupProject.borrowMe.JSONRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Enache on 20/04/2018.
 */

public class ItemsFromDepartmentRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "https://myxstyle120.000webhostapp.com/itemsDepartment.php";
    private Map<String, String> params;

    public ItemsFromDepartmentRequest(String department, Response.Listener<String> listener) {
        super(Method.GET, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Department", department);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
