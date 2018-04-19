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
import com.groupProject.borrowMe.JSONRequests.AcceptItemRequest;

import com.groupProject.borrowMe.JSONRequests.UpdateRequest;
import com.groupProject.borrowMe.JSONRequests.denyItemRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class InspectItem extends AppCompatActivity {

    String item_id,email,title,price,details,department;
    public AppCompatTextView ID,EMAIL,TITLE,PRICE,DETAILS,DEPARTMENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ID = (AppCompatTextView) findViewById(R.id.textId);
        EMAIL = (AppCompatTextView) findViewById(R.id.textEmail);
        TITLE = (AppCompatTextView) findViewById(R.id.textTitle);
        PRICE = (AppCompatTextView) findViewById(R.id.textPrice);
        DETAILS = (AppCompatTextView) findViewById(R.id.textDetails);
        DEPARTMENT = (AppCompatTextView) findViewById(R.id.textDepartment);

        Intent intent = getIntent();
        item_id = intent.getStringExtra("item_id");
        email = intent.getStringExtra("email");
        title = intent.getStringExtra("title");
        price = intent.getStringExtra("price");
        details = intent.getStringExtra("description");
        department = intent.getStringExtra("department");

        ID.setText(item_id);
        EMAIL.setText(email);
        TITLE.setText(title);
        PRICE.setText(price);
        DETAILS.setText(details);
        DEPARTMENT.setText(department);



        FloatingActionButton check = (FloatingActionButton) findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject( response );
                            boolean success = jsonResponse.getBoolean( "success" );
                            if (success) {

                                Snackbar.make(view, "Item has been accepted", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder( InspectItem.this );
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

                AcceptItemRequest registerRequest = new AcceptItemRequest(item_id, responseListener );
                RequestQueue queue = Volley.newRequestQueue( InspectItem.this );
                queue.add( registerRequest );

            }
        });

        FloatingActionButton delete = (FloatingActionButton) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                Response.Listener<String> deny = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponseDeny = new JSONObject( response );
                            boolean success = jsonResponseDeny.getBoolean( "success" );
                            if (success) {

                                Snackbar.make(view, "Item has been denied and removed", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder( InspectItem.this );
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

                denyItemRequest reject = new denyItemRequest(item_id, deny );
                RequestQueue queue1 = Volley.newRequestQueue( InspectItem.this );
                queue1.add( reject );

            }
        });
    }

}
