package com.groupProject.borrowMe.Departments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.groupProject.borrowMe.R;
import com.groupProject.borrowMe.adaptors.AdaptorItemDepartments;
import com.groupProject.borrowMe.models.ItemDepartments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.groupProject.borrowMe.R.layout.activity_item_department;

public class Clothing extends AppCompatActivity {

    RecyclerView recyclerView;



    List<ItemDepartments> items;

    private static final String request_url = "https://myxstyle120.000webhostapp.com/departments/clothing.php";

    String Department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_item_department);


        recyclerView = (RecyclerView) findViewById(R.id.ItemsAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        items = new ArrayList<>();

        Intent intent = getIntent();
        Department = intent.getStringExtra("department");



        StringRequest stringRequest = new StringRequest(Request.Method.GET, request_url,
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
                                items.add(new ItemDepartments(
                                        item.getString("item_id"),
                                        item.getString("name"),
                                        item.getString("price")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            AdaptorItemDepartments adapter = new AdaptorItemDepartments(Clothing.this, items);
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
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);



    }}