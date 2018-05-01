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
import com.groupProject.borrowMe.JSONRequests.RequestBorrow;
import com.groupProject.borrowMe.JSONRequests.RequestItemName;
import com.groupProject.borrowMe.JSONRequests.RequestUserContact;
import com.groupProject.borrowMe.JSONRequests.returnItemRequest;
import com.groupProject.borrowMe.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LentItemsDetails extends AppCompatActivity {

    public AppCompatTextView item, email, start,end,ItemPrice,item_deposit;
    String ITEM,EMAIL,START,END,ITEM_ID,B_EMAIL,name,phone,address,city,postcode,price,tmp,tmp_dep,deposit,balance_lender;
    Button returnItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lent_items_details);

        item = (AppCompatTextView) findViewById(R.id.textItem);
        email = (AppCompatTextView) findViewById(R.id.textEmail);
        start = (AppCompatTextView) findViewById(R.id.textStartDate);
        end = (AppCompatTextView) findViewById(R.id.textEndDate);
        returnItem = (Button) findViewById(R.id.returnItem);


        Intent intent = getIntent();
        final String borrow_id = intent.getStringExtra("borrow_id");

        getDetails(borrow_id);

        returnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                returnItem(borrow_id,ITEM_ID);
            }
        });


        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LentItemsDetails.this, PostedItem.class);
                intent.putExtra("item_id", ITEM_ID);
                LentItemsDetails.this.startActivity(intent);
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
                                balance_lender = jsonResponse.getString("balance");
                                address = jsonResponse.getString("address");
                                city = jsonResponse.getString("city");
                                postcode = jsonResponse.getString("postcode");




                                AlertDialog.Builder builder = new AlertDialog.Builder(LentItemsDetails.this);
                                builder.setMessage("Email contact: "+ B_EMAIL +"\nNumber contact: "+phone
                                        +"\nName contact: "+name + "\nCity: " +city + "\nAddress: "+ address
                                        +"\nPostcode: "+ postcode)
                                        .setNegativeButton("OK", null)
                                        .create()
                                        .show();



                            } else {
//Error
                                AlertDialog.Builder builder = new AlertDialog.Builder(LentItemsDetails.this);
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
                RequestQueue queue1 = Volley.newRequestQueue(LentItemsDetails.this);
                queue1.add(user);
            }
        });


    }

    public void returnItem(final String borrow_id,final String item_id){
        Response.Listener<String> Item = new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {


                try {
                    JSONObject jsonResponse = new JSONObject(response1);
                    boolean success1 = jsonResponse.getBoolean("success");

                    if (success1) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(LentItemsDetails.this);
                        builder.setMessage("The item was returned to it's owner.")
                                .setNegativeButton("Ok", null)
                                .create()
                                .show();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LentItemsDetails.this);
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

        returnItemRequest Request = new returnItemRequest(borrow_id,item_id, Item);
        RequestQueue queue = Volley.newRequestQueue(LentItemsDetails.this);
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

                        getItemDetails(ITEM_ID);

                    } else {
                        //Error
                        AlertDialog.Builder builder = new AlertDialog.Builder(LentItemsDetails.this);
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
        RequestQueue queue = Volley.newRequestQueue(LentItemsDetails.this);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(LentItemsDetails.this);
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
        RequestQueue queue = Volley.newRequestQueue(LentItemsDetails.this);
        queue.add(Request);

    }
}
