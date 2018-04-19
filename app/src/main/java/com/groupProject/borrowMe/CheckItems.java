package com.groupProject.borrowMe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.groupProject.borrowMe.adaptors.AdaptorAdminCheckItem;
import com.groupProject.borrowMe.models.ItemCheck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class CheckItems extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<ItemCheck> personUtilsList;

    RequestQueue rq;

    String request_url = "http://localhost/testapi.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_items);

        rq = Volley.newRequestQueue(this);

        recyclerView = (RecyclerView) findViewById(R.id.CheckItemsAdapter);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        personUtilsList = new ArrayList<>();

        sendRequest();

    }


    public void sendRequest(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i = 0; i < response.length(); i++){

                    ItemCheck items = new ItemCheck();

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        items.setItemTitle(jsonObject.getString("name"));
                        items.setItemPrice(jsonObject.getString("price"));
                        items.setItemDetails(jsonObject.getString("des"));
                        items.setItemDepartment(jsonObject.getString("department"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    personUtilsList.add(items);

                }

                mAdapter = new AdaptorAdminCheckItem(CheckItems.this, personUtilsList);

                recyclerView.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Volley Error: ", String.valueOf(error));
            }
        });
        rq.add(jsonArrayRequest);

    }
}
