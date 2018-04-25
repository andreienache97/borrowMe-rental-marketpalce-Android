package com.groupProject.borrowMe;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.groupProject.borrowMe.Item.InspectItem;
import com.groupProject.borrowMe.Item.ItemDetails;
import com.groupProject.borrowMe.Item.PostedItem;
import com.groupProject.borrowMe.JSONRequests.RequestBorrow;
import com.groupProject.borrowMe.JSONRequests.RequestItemName;
import com.groupProject.borrowMe.JSONRequests.RequestUser;
import com.groupProject.borrowMe.JSONRequests.RequestUserContact;
import com.groupProject.borrowMe.JSONRequests.RequestUserItem;
import com.groupProject.borrowMe.JSONRequests.deleteBorrowRequest;
import com.groupProject.borrowMe.JSONRequests.denyItemRequest;
import com.groupProject.borrowMe.User.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Enache on 25/04/2018.
 */


public class BorrowRequestDetails extends AppCompatActivity {

    public AppCompatTextView item, email, start,end;
    String ITEM,EMAIL,START,END,ITEM_ID,B_EMAIL,name,phone,address,city,postcode,deposit,price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_request_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        item = (AppCompatTextView) findViewById(R.id.textItem);
        email = (AppCompatTextView) findViewById(R.id.textEmail);
        start = (AppCompatTextView) findViewById(R.id.textStartDate);
        end = (AppCompatTextView) findViewById(R.id.textEndDate);


        Intent intent = getIntent();
        final String borrow_id = intent.getStringExtra("borrow_id");

        getDetails(borrow_id);

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BorrowRequestDetails.this, PostedItem.class);
                intent.putExtra("item_id", ITEM_ID);
                BorrowRequestDetails.this.startActivity(intent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


                                AlertDialog.Builder builder = new AlertDialog.Builder(BorrowRequestDetails.this);
                                builder.setMessage("Email contact: "+ EMAIL +"\nNumber contact: "+phone
                                        +"\nName contact: "+name + "\nCity: " +city + "\nAddress: "+ address
                                +"\nPostcode: "+ postcode)
                                        .setNegativeButton("OK", null)
                                        .create()
                                        .show();



                            } else {
//Error
                                AlertDialog.Builder builder = new AlertDialog.Builder(BorrowRequestDetails.this);
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
//Connect to database
                RequestUserContact user = new RequestUserContact(EMAIL, getUserDet);
                RequestQueue queue1 = Volley.newRequestQueue(BorrowRequestDetails.this);
                queue1.add(user);
            }
        });


        FloatingActionButton accept = (FloatingActionButton) findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton decline = (FloatingActionButton) findViewById(R.id.decline);
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteRequest(borrow_id);




            }
        });


    }


    public void getDetails(final String borrow_id) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        EMAIL = jsonResponse.getString( "Lender_email" );
                        B_EMAIL = jsonResponse.getString( "Borrower_email" );
                        ITEM_ID = jsonResponse.getString( "item_id" );
                        START = jsonResponse.getString( "Start_date" );
                        END = jsonResponse.getString( "End_date" );



                        email.setText(EMAIL);
                        start.setText(START);
                        end.setText(END);

                       getItemDetails(ITEM_ID);

                    } else {
                        //Error
                        AlertDialog.Builder builder = new AlertDialog.Builder(BorrowRequestDetails.this);
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
        RequestBorrow Request = new RequestBorrow(borrow_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(BorrowRequestDetails.this);
        queue.add(Request);

    }

    public void getItemDetails(final String item_id){

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        ITEM = jsonResponse.getString( "name" );
                        price = jsonResponse.getString( "price" );
                        deposit = jsonResponse.getString( "Deposit" );


                        item.setText(ITEM);




                    } else {
                        //Error
                        AlertDialog.Builder builder = new AlertDialog.Builder(BorrowRequestDetails.this);
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

//Connec to database
        RequestItemName Request = new RequestItemName(item_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(BorrowRequestDetails.this);
        queue.add(Request);

    }


    public void deleteRequest(final String request_id){


        Response.Listener<String> deleteItem = new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {


                try {
                    JSONObject jsonResponse = new JSONObject(response1);
                    boolean success1 = jsonResponse.getBoolean("success");

                    if (success1) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(BorrowRequestDetails.this);
                        builder.setMessage("The request has been denied and the deposit returned")
                                .setNegativeButton("Ok", null)
                                .create()
                                .show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BorrowRequestDetails.this);
                        builder.setMessage("Error")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        deleteBorrowRequest Request = new deleteBorrowRequest(request_id, deleteItem);
        RequestQueue queue = Volley.newRequestQueue(BorrowRequestDetails.this);
        queue.add(Request);

    }

}
