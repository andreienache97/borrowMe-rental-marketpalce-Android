package com.groupProject.borrowMe.JSONRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Diana on 5/2/2018.
 */

    public class FavouriteRequest extends StringRequest {

        private static final String FAVOURITE_REQUEST_URL = "https://myxstyle120.000webhostapp.com/.php";
        private Map<String, String> params;

        public FavouriteRequest(String email,String item_name, String price, String des, String ADate, String UDate, String department, int deposit, Response.Listener<String> listener) {
            super( Method.POST, FAVOURITE_REQUEST_URL, listener, null );
            params = new HashMap<>();
            params.put( "email", email );
            params.put( "Name", item_name );
            params.put( "Des", des );
            params.put( "Price", price );
            params.put( "ADate", ADate );
            params.put( "UDate", UDate );
            params.put( "Department", department );
            params.put( "Deposit", String.valueOf( deposit ) );
        }


    //test
        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }
