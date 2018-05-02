/* Author: Lau Tsz Chung,Andrei Enache, Sebastián Arocha */
package com.groupProject.borrowMe.Item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.groupProject.borrowMe.Helpers.AvailableDate;
import com.groupProject.borrowMe.JSONRequests.FavouriteRequest;
import com.groupProject.borrowMe.JSONRequests.RequestItem;
import com.groupProject.borrowMe.JSONRequests.RequestUserContact;
import com.groupProject.borrowMe.JSONRequests.ReportItemRequest;
import com.groupProject.borrowMe.JSONRequests.denyItemRequest;
import com.groupProject.borrowMe.R;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.groupProject.borrowMe.Chat.ChatActivity;

public class ItemDetails extends AppCompatActivity {

//Fields
    public AppCompatTextView title,price,details,available,unavailable,department,deposit,fine;
    public Button userDetails,borrow,report,contact,favoriteItem;
    String id,TITLE,PRICE,DETAILS,AVAILABLE,UNAVAILABLE,DEPARTMENT,LendarEMAIL,name,phone,address,city,postcode,DEPOSIT,FINE;
    String BorrowerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

//return button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        contact = (Button) findViewById(R.id.bContact);
        favoriteItem = (Button) findViewById(R.id.bFavorite);
//get variables from intent
        Intent intent = getIntent();
        id = intent.getStringExtra("item_id");
        BorrowerEmail = intent.getStringExtra( "email" );

//chat button
        contact.setVisibility(View.GONE);

// favorite item button
        favoriteItem.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(final View view) {

                                                Response.Listener<String> responseListener = new Response.Listener<String>() {

                                                    @Override
                                                    public void onResponse(String response) {
                                                        try {
                                                            JSONObject jsonResponse = new JSONObject(response);
                                                            boolean success = jsonResponse.getBoolean("success");
                                                            if (success) {
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetails.this);
                                                                builder.setMessage("Added to Favourites!")
                                                                        .setPositiveButton("OK", null)
                                                                        .create()
                                                                        .show();
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                };

                                                FavouriteRequest FavouriteRequest = new FavouriteRequest(id, BorrowerEmail, responseListener);
                                                RequestQueue queue = Volley.newRequestQueue(ItemDetails.this);
                                                queue.add(FavouriteRequest);
                                            }
        });


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

                        setupContactButton();

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

        //Report Button
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final EditText reportInputText = new EditText(ItemDetails.this);
                LinearLayout.LayoutParams layParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                    LinearLayout.LayoutParams.MATCH_PARENT);

                reportInputText.setLayoutParams(layParameters);

                //Show User Details and Prompt to Enter the report reason
                AlertDialog.Builder reportInput= new AlertDialog.Builder(ItemDetails.this);
                reportInput.setTitle("Report Submission");
                reportInput.setMessage("Item: "+ TITLE +"\nDepartment: "+ DEPARTMENT +"Owner: "+ LendarEMAIL +
                             "\nEnter your reason for reporting this item (MAX 140 Characters): \n")
                            .setView(reportInputText)
                            .setPositiveButton("Submit", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    //Gets Item id, Reporter e-mail and Reason
                                    int item_id = Integer.parseInt(id);
                                    String email = BorrowerEmail;
                                    String reason = reportInputText.getText().toString();

                                    //Create a response listener
                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            //Try to connect to the PHP File And get response boolean
                                            try
                                            {
                                                JSONObject jsonResponse = new JSONObject(response);
                                                System.out.println(jsonResponse.toString());
                                                boolean success = jsonResponse.getBoolean("success");
                                                //If Connection Successful then Display Successful submission message
                                                //Else Display Submission Error
                                                if(success)
                                                {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetails.this);
                                                    builder.setMessage("Report has been submitted: ")
                                                            .setNegativeButton("OK", null)
                                                            .create()
                                                            .show();
                                                }else{
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetails.this);
                                                    builder.setMessage("Failed Report")
                                                            .setNegativeButton("OK", null)
                                                            .create()
                                                            .show();
                                                }//Catch Exception and Print Stack Trace
                                            }catch(JSONException e)
                                            {
                                                e.printStackTrace();
                                            }

                                        }
                                    };

                                    //Create the connection between the The Listener and the class that interacts with the Php
                                    ReportItemRequest reportRequest = new ReportItemRequest(item_id, email,reason,responseListener);
                                    //Create a queue and add the request to it
                                    RequestQueue queue = Volley.newRequestQueue(ItemDetails.this);
                                    queue.add(reportRequest);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create()
                            .show();



            }
        });

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
    private void setupContactButton() {
        contact.setVisibility(View.VISIBLE);

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemDetails.this, ChatActivity.class);

                intent.putExtra(ChatActivity.EXTRA_EMAIL_FROM, BorrowerEmail);
                intent.putExtra(ChatActivity.EXTRA_EMAIL_TO, LendarEMAIL);
                intent.putExtra(ChatActivity.EXTRA_EMAIL_ITEM_ID, id);

                startActivity(intent);
            }
        });
    }

}
