package com.groupProject.borrowMe;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.groupProject.borrowMe.JSONRequests.RequestItem;
import com.groupProject.borrowMe.JSONRequests.RequestUser;
import com.groupProject.borrowMe.JSONRequests.RequestUserContact;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemDetails extends AppCompatActivity {


    public AppCompatTextView title,price,details,available,unavailable,department;
    public Button userDetails,borrow,report;
    String id,TITLE,PRICE,DETAILS,AVAILABLE,UNAVAILABLE,DEPARTMENT,EMAIL,name,phone,address,city,postcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        title = (AppCompatTextView) findViewById(R.id.textViewTitle);
        price = (AppCompatTextView) findViewById(R.id.textViewPrice);
        details = (AppCompatTextView) findViewById(R.id.textViewDescription);
        available = (AppCompatTextView) findViewById(R.id.textViewAvailable);
        unavailable = (AppCompatTextView) findViewById(R.id.textViewUnavailable);
        department = (AppCompatTextView) findViewById(R.id.textViewDepartment);

        userDetails = (Button) findViewById(R.id.bUserDetails);
        borrow = (Button) findViewById(R.id.bBorrow);
        report = (Button) findViewById(R.id.bReport);


        Intent intent = getIntent();
        id = intent.getStringExtra("item_id");


        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        EMAIL = jsonResponse.getString( "email" );
                        TITLE = jsonResponse.getString("name");
                        PRICE = jsonResponse.getString("price");
                        DETAILS = jsonResponse.getString("Description");
                        AVAILABLE = jsonResponse.getString("AvailableDate");
                        UNAVAILABLE = jsonResponse.getString("UnavailableDate");
                        DEPARTMENT = jsonResponse.getString("Department");


                        title.setText(TITLE);
                        price.setText(PRICE);
                        details.setText(DETAILS);
                        available.setText(AVAILABLE);
                        unavailable.setText(UNAVAILABLE);
                        department.setText(DEPARTMENT);

                        secondRequest(EMAIL);

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetails.this);
                        builder.setMessage("Can not fetch the item details at the moment")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RequestItem Request = new RequestItem(id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ItemDetails.this);
        queue.add(Request);



        userDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetails.this);
                builder.setMessage("Email contact: "+ EMAIL +"\nNumber contact: "+phone
                                    +"\nName contact: "+name + "\nCity: " +city)
                        .setNegativeButton("OK", null)
                        .create()
                        .show();
            }
        });

    }

    public void secondRequest(String email)
    {
        Response.Listener<String> getUserDet = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        name = jsonResponse.getString("name");
                        phone = jsonResponse.getString("phone");
                        address = jsonResponse.getString("address");
                        city = jsonResponse.getString("city");
                        postcode = jsonResponse.getString("postcode");




                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetails.this);
                        builder.setMessage("Can not fetch the user details at the moment")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RequestUserContact user = new RequestUserContact(email, getUserDet);
        RequestQueue queue1 = Volley.newRequestQueue(ItemDetails.this);
        queue1.add(user);
    }

}
