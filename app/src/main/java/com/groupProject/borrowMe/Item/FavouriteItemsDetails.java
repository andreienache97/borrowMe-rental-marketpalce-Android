package com.groupProject.borrowMe.Item;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.groupProject.borrowMe.JSONRequests.RequestFavourite;
import com.groupProject.borrowMe.JSONRequests.RequestItemName;
import com.groupProject.borrowMe.JSONRequests.RequestUserContact;
import com.groupProject.borrowMe.JSONRequests.returnItemRequest;
import com.groupProject.borrowMe.R;

import org.json.JSONException;
import org.json.JSONObject;

public class FavouriteItemsDetails extends AppCompatActivity {

    public AppCompatTextView item, email1, start,end,ItemPrice;

    String ITEM,START,END,ITEM_ID,price,LENDER_EMAIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_items_details);

        item = (AppCompatTextView) findViewById(R.id.textItem);
        email1 = (AppCompatTextView) findViewById(R.id.textEmail);
        start = (AppCompatTextView) findViewById(R.id.availableDate);
        end = (AppCompatTextView) findViewById(R.id.unAvailableDate);
        ItemPrice = (AppCompatTextView) findViewById(R.id.textItemPrice);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        final String id = intent.getStringExtra("item_id");

        getDetails(email,id);

    }

    public void getDetails(final String email,String id) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.d("---------",response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        LENDER_EMAIL = jsonResponse.getString( "email" );
                        START = jsonResponse.getString( "AvailableDate" );
                        END = jsonResponse.getString( "UnavailableDate" );
                        ITEM = jsonResponse.getString( "name" );
                        price = jsonResponse.getString( "price" );

                        email1.setText(LENDER_EMAIL);
                        start.setText(START);
                        end.setText(END);

                        item.setText(ITEM);
                        ItemPrice.setText(price+ " £/day");


                    } else {
                        //Error
                        AlertDialog.Builder builder = new AlertDialog.Builder(FavouriteItemsDetails.this);
                        builder.setMessage("Can not fetch the request details at the moment")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

//Connec to database
        RequestFavourite Request = new RequestFavourite(email,id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(FavouriteItemsDetails.this);
        queue.add(Request);

    }

}
