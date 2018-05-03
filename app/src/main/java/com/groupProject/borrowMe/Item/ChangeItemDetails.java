package com.groupProject.borrowMe.Item;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.groupProject.borrowMe.JSONRequests.UpdateItemRequest;
import com.groupProject.borrowMe.JSONRequests.UpdateRequest;
import com.groupProject.borrowMe.R;
import com.groupProject.borrowMe.User.ChangeUserDetails;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Enache on 25/04/2018.
 */


public class ChangeItemDetails extends AppCompatActivity {

    EditText title,price,description;


    String TITLE,PRICE,DESCRIPTION,ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_item_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //return button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        title = (EditText) findViewById(R.id.etTitle);
        price = (EditText) findViewById(R.id.etPrice);
        description = (EditText) findViewById(R.id.etDetails);


        Intent intent = getIntent();
        TITLE = intent.getStringExtra("title");
        PRICE = intent.getStringExtra("price");
        DESCRIPTION = intent.getStringExtra("description");
        ID = intent.getStringExtra("item_id");

        title.setText(TITLE);
        price.setText(PRICE);
        description.setText(DESCRIPTION);



        FloatingActionButton upload = (FloatingActionButton) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                GetDataFromEditText();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject( response );
                            boolean success = jsonResponse.getBoolean( "success" );
                            if (success) {

                                Snackbar.make(view, "Update made", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder( ChangeItemDetails.this );
                                builder.setMessage( "Update Failed" )
                                        .setNegativeButton( "Retry", null )
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                UpdateItemRequest Request = new UpdateItemRequest( TITLE, PRICE, DESCRIPTION,ID, responseListener );
                RequestQueue queue = Volley.newRequestQueue( ChangeItemDetails.this );
                queue.add( Request );


            }
        });
    }


    public void GetDataFromEditText(){

        TITLE = title.getText().toString();
        PRICE = price.getText().toString();
        DESCRIPTION = description.getText().toString();


    }

}
