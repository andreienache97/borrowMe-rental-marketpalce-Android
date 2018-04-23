/* Author: Lau Tsz Chung,Andrei Enache, Sebastián Arocha */
package com.groupProject.borrowMe.Item;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.groupProject.borrowMe.Helpers.AvailableDate;
import com.groupProject.borrowMe.JSONRequests.RequestItem;
import com.groupProject.borrowMe.JSONRequests.RequestUserContact;
import com.groupProject.borrowMe.R;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class ItemDetails extends AppCompatActivity {

//Fields
    public AppCompatTextView title,price,details,available,unavailable,department,deposit,fine;
    public Button userDetails,borrow,report;
    String id,TITLE,PRICE,DETAILS,AVAILABLE,UNAVAILABLE,DEPARTMENT,LendarEMAIL,name,phone,address,city,postcode,DEPOSIT,FINE;
    String BorrowerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

//setting text views and buttons
        title = (AppCompatTextView) findViewById(R.id.textViewTitle);
        price = (AppCompatTextView) findViewById(R.id.textViewPrice);
        details = (AppCompatTextView) findViewById(R.id.textViewDescription);
        available = (AppCompatTextView) findViewById(R.id.textViewAvailable);
        unavailable = (AppCompatTextView) findViewById(R.id.textViewUnavailable);
        department = (AppCompatTextView) findViewById(R.id.textViewDepartment);
        deposit = (AppCompatTextView) findViewById(R.id.textViewDeposit);
        fine = (AppCompatTextView) findViewById(R.id.textViewFine);

        userDetails = (Button) findViewById(R.id.bUserDetails);
        borrow = (Button) findViewById(R.id.bBorrow);
        report = (Button) findViewById(R.id.bReport);

//get variables from intent
        Intent intent = getIntent();
        id = intent.getStringExtra("item_id");
        BorrowerEmail = intent.getStringExtra( "email" );


//get the item details from database
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        LendarEMAIL = jsonResponse.getString( "email" );
                        TITLE = jsonResponse.getString("name");
                        PRICE = jsonResponse.getString("price");
                        DETAILS = jsonResponse.getString("Description");
                        AVAILABLE = jsonResponse.getString("AvailableDate");
                        UNAVAILABLE = jsonResponse.getString("UnavailableDate");
                        DEPARTMENT = jsonResponse.getString("Department");
                        DEPOSIT = jsonResponse.getString("Deposit");
                        FINE = jsonResponse.getString("Fine");

//show details of the item to user
                        title.setText(TITLE);
                        price.setText( String.format( "%s £/day", PRICE ) );
                        details.setText(DETAILS);
                        available.setText(AVAILABLE);
                        unavailable.setText(UNAVAILABLE);
                        department.setText(DEPARTMENT);
                        deposit.setText( String.format( "£ %s", DEPOSIT ) );
                        fine.setText( String.format( "£ %s", FINE ) );

//get the lender\s details
                        secondRequest(LendarEMAIL);

                    } else {
//Error
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
//Conenct to database
        RequestItem Request = new RequestItem(id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ItemDetails.this);
        queue.add(Request);


//if user wants to know the lender's details
        userDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetails.this);
                builder.setMessage("Email contact: "+ LendarEMAIL +"\nNumber contact: "+phone
                                    +"\nName contact: "+name + "\nCity: " +city)
                        .setNegativeButton("OK", null)
                        .create()
                        .show();
            }
        });

//user clicks borrow button
        borrow.setOnClickListener( new View.OnClickListener() {
            int test = 0;
            @Override
            public void onClick(View v) {
//direct user to select a date
                Intent SelectDate = new Intent( ItemDetails.this, AvailableDate.class );
                SelectDate.putExtra( "Lenderemail", LendarEMAIL );
                SelectDate.putExtra( "Borrowemail", BorrowerEmail );
                SelectDate.putExtra( "item_id", id );
                SelectDate.putExtra( "FromItemDetail", test );
                startActivity( SelectDate );

            }
        } );

        report.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("email",LendarEMAIL + "+" + id);

            }
        } );

    }

//Get the lender's details
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
//Error
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
//Connect to database
        RequestUserContact user = new RequestUserContact(email, getUserDet);
        RequestQueue queue1 = Volley.newRequestQueue(ItemDetails.this);
        queue1.add(user);
    }

}
