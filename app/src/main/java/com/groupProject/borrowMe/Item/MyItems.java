package com.groupProject.borrowMe.Item;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.groupProject.borrowMe.Departments.DepartmentByName;
import com.groupProject.borrowMe.Helpers.SeparatorDecoration;
import com.groupProject.borrowMe.R;
import com.groupProject.borrowMe.adaptors.AdaptorItemDepartments;
import com.groupProject.borrowMe.adaptors.AdaptorUserItems;
import com.groupProject.borrowMe.models.ItemDepartments;
import com.groupProject.borrowMe.models.UserItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Enache on 23/04/2018.
 */

public class MyItems extends AppCompatActivity {


    RecyclerView recyclerView;


    List<UserItems> items;



    String request_url ="https://myxstyle120.000webhostapp.com/myItems.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_items);

        //return button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.MyItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SeparatorDecoration decoration = new SeparatorDecoration(this, Color.GRAY, 1.5f);
        recyclerView.addItemDecoration(decoration);


        items = new ArrayList<>();

        Intent intent = getIntent();
        final String email = intent.getStringExtra( "email" );

        sendRequest(email);

    }

    public void sendRequest(final String email) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, request_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject item = array.getJSONObject(i);

                                //adding the product to product list
                                items.add(new UserItems(
                                        item.getString("item_id"),
                                        item.getString("name")

                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            AdaptorUserItems adapter = new AdaptorUserItems(MyItems.this, items);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put( "email", email ); //Add the data you'd like to send to the server.
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }


        } ;

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

}
