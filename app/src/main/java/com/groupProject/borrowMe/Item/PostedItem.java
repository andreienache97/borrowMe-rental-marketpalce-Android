package com.groupProject.borrowMe.Item;

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
import com.groupProject.borrowMe.JSONRequests.RequestUserItem;
import com.groupProject.borrowMe.JSONRequests.denyItemRequest;
import com.groupProject.borrowMe.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Enache on 23/04/2018.
 */

public class PostedItem extends AppCompatActivity {


    public AppCompatTextView ID, EMAIL, TITLE, PRICE, DETAILS, DEPARTMENT,STATUS,DEPOSIT,FINE,AVAILABLE,UNAVAILABLE;
    public String id,email,name,price,description,available,unavailable,department,deposit,fine,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ID = (AppCompatTextView) findViewById(R.id.textId);
        EMAIL = (AppCompatTextView) findViewById(R.id.textEmail);
        TITLE = (AppCompatTextView) findViewById(R.id.textTitle);
        PRICE = (AppCompatTextView) findViewById(R.id.textPrice);
        DETAILS = (AppCompatTextView) findViewById(R.id.textDetails);
        DEPARTMENT = (AppCompatTextView) findViewById(R.id.textDepartment);
        AVAILABLE = (AppCompatTextView) findViewById(R.id.textAvailable);
        UNAVAILABLE = (AppCompatTextView) findViewById(R.id.textUnavailable);
        DEPOSIT = (AppCompatTextView) findViewById(R.id.textDeposit);
        FINE = (AppCompatTextView) findViewById(R.id.textFine);
        STATUS =(AppCompatTextView) findViewById(R.id.textStatus);

        FloatingActionButton upload = (FloatingActionButton) findViewById(R.id.upload);
        FloatingActionButton delete = (FloatingActionButton) findViewById(R.id.delete);


        Intent intent = getIntent();
        final String item_id = intent.getStringExtra("item_id");


        getDetails(item_id);


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if(status == "0"){
                Intent listItems = new Intent(PostedItem.this, ChangeItemDetails.class);
                listItems.putExtra("title", name);
                listItems.putExtra( "price", price );
                listItems.putExtra( "description", description );
                listItems.putExtra( "item_id", id );
                PostedItem.this.startActivity(listItems);}
                else {

                    Snackbar.make(view, "Your item is borrowed, changes are not allowed", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                if(status == "0") {
                    Response.Listener<String> deleteItem = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response1) {


                            try {
                                JSONObject jsonResponse = new JSONObject(response1);
                                boolean success1 = jsonResponse.getBoolean("success");

                                if (success1) {

                                    Snackbar.make(view, "Your item has been removed", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(PostedItem.this);
                                    builder.setMessage("Update Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    denyItemRequest Request = new denyItemRequest(item_id, deleteItem);
                    RequestQueue queue = Volley.newRequestQueue(PostedItem.this);
                    queue.add(Request);

                }
                else
                {
                    Snackbar.make(view, "Your item is borrowed, can not delete it", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

    }


    public void getDetails(final String item_id) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        id = jsonResponse.getString( "item_id" );
                        email = jsonResponse.getString( "email" );
                        name = jsonResponse.getString( "name" );
                        price = jsonResponse.getString( "price" );
                        description = jsonResponse.getString( "Description" );
                        available = jsonResponse.getString( "AvailableDate" );
                        unavailable = jsonResponse.getString( "UnavailableDate" );
                        department = jsonResponse.getString( "Department" );
                        deposit = jsonResponse.getString( "Deposit" );
                        fine = jsonResponse.getString( "Fine" );
                        status = jsonResponse.getString( "Status" );



                        ID.setText(id);
                        EMAIL.setText(email);
                        TITLE.setText(name);
                        PRICE.setText( String.format( "%s £/day", price ) );
                        DETAILS.setText(description);
                        AVAILABLE.setText(available);
                        UNAVAILABLE.setText(unavailable);
                        DEPARTMENT.setText(department);
                        DEPOSIT.setText( String.format( "£ %s", deposit ) );
                        FINE.setText( String.format( "£ %s", fine ) );
                        if(status == "0")
                            STATUS.setText( "AVAILABLE" );
                        else
                            STATUS.setText(  "BORROWED" );


                    } else {
                        //Error
                        AlertDialog.Builder builder = new AlertDialog.Builder(PostedItem.this);
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
        RequestUserItem Request = new RequestUserItem(item_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(PostedItem.this);
        queue.add(Request);

    }


}
