package com.groupProject.borrowMe.Item;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.groupProject.borrowMe.JSONRequests.RequestBorrow;
import com.groupProject.borrowMe.JSONRequests.RequestItemName;
import com.groupProject.borrowMe.JSONRequests.RequestUserContact;
import com.groupProject.borrowMe.JSONRequests.acceptBorrowRequest;
import com.groupProject.borrowMe.JSONRequests.deleteBorrowRequest;
import com.groupProject.borrowMe.MainActivity;
import com.groupProject.borrowMe.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Enache on 25/04/2018.
 */


public class BorrowRequestDetails extends AppCompatActivity {

    public AppCompatTextView item, email, start,end;
    public String ITEM,EMAIL,START,END,ITEM_ID,B_EMAIL,name,phone,address,city,postcode,price,tmp,tmp_dep;
    int balance_lender,deposit;
    private String Days;

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
                                builder.setMessage("Email contact: "+ B_EMAIL +"\nNumber contact: "+phone
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
                RequestUserContact user = new RequestUserContact(B_EMAIL, getUserDet);
                RequestQueue queue1 = Volley.newRequestQueue(BorrowRequestDetails.this);
                queue1.add(user);
            }
        });


        FloatingActionButton accept = (FloatingActionButton) findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptRequest(borrow_id,ITEM_ID);
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
    private void checkDate(String ADate, String UDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date firstDate = sdf.parse(ADate);
        Date secondDate = sdf.parse(UDate);

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        Log.d( "Time is ---", String.valueOf( diff ) );
        Days = String.valueOf( diff );
    }


    public void acceptRequest(final String borrow_id,final String item_id){
        Response.Listener<String> deleteItem = new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {


                try {
                    JSONObject jsonResponse = new JSONObject(response1);
                    boolean success1 = jsonResponse.getBoolean("success");

                    if (success1) {
                        Intent Backintent = new Intent( BorrowRequestDetails.this, MainActivity.class );
                        startActivity( Backintent );


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

        acceptBorrowRequest Request = new acceptBorrowRequest(borrow_id,item_id,Days, deleteItem);
        RequestQueue queue = Volley.newRequestQueue(BorrowRequestDetails.this);
        queue.add(Request);
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



                        email.setText(B_EMAIL);
                        start.setText(START);
                        end.setText(END);
                        try {
                            checkDate(START,END);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


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
                        deposit = jsonResponse.getInt( "Deposit" );

                        tmp_dep = String.valueOf( deposit );


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
                        builder.setMessage("The request has been denied.")
                                .setNegativeButton("Ok", null)
                                .create()
                                .show();

                       // returnDeposit(EMAIL,tmp,tmp_dep);
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
