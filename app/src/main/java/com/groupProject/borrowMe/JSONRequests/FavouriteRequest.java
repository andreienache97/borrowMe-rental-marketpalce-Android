package com.groupProject.borrowMe.JSONRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Diana on 5/2/2018.
 * add to Favorite table
 */

    public class FavouriteRequest extends StringRequest {

        private static final String FAVOURITE_REQUEST_URL = "https://myxstyle120.000webhostapp.com/AddFavorite.php";
        private Map<String, String> params;

        public FavouriteRequest(String item_id,String email, Response.Listener<String> listener) {
            super( Method.POST, FAVOURITE_REQUEST_URL, listener, null );
            params = new HashMap<>();
            params.put( "item_id", item_id );
            params.put( "email", email );
        }


    //test
        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }
