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
import com.groupProject.borrowMe.JSONRequests.AcceptItemRequest;

import com.groupProject.borrowMe.JSONRequests.denyItemRequest;
import com.groupProject.borrowMe.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Arocha 25/04/2018
 */
public class InspectReportedItem extends AppCompatActivity {

    String item_id, email, title, price, details, department,reportEmail,reportReason;
    public AppCompatTextView ID, EMAIL, TITLE, PRICE, DETAILS, DEPARTMENT,REPORTEMAIL,REPORTREASON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_reported_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ID = (AppCompatTextView) findViewById(R.id.textId);
        EMAIL = (AppCompatTextView) findViewById(R.id.textEmail);
        TITLE = (AppCompatTextView) findViewById(R.id.textTitle);
        PRICE = (AppCompatTextView) findViewById(R.id.textPrice);
        DETAILS = (AppCompatTextView) findViewById(R.id.textDetails);
        DEPARTMENT = (AppCompatTextView) findViewById(R.id.textDepartment);
        REPORTEMAIL = (AppCompatTextView) findViewById(R.id.textReportEmail);
        REPORTREASON = (AppCompatTextView) findViewById(R.id.textReportReason);

        Intent intent = getIntent();
        item_id = intent.getStringExtra("item_id");
        email = intent.getStringExtra("email");
        title = intent.getStringExtra("title");
        price = intent.getStringExtra("price");
        details = intent.getStringExtra("description");
        department = intent.getStringExtra("department");
        reportEmail = intent.getStringExtra("reportEmail");
        reportReason = intent.getStringExtra("reportReason");

        ID.setText(item_id);
        EMAIL.setText(email);
        TITLE.setText(title);
        PRICE.setText(price);
        DETAILS.setText(details);
        DEPARTMENT.setText(department);
        REPORTEMAIL.setText(reportEmail);
        REPORTREASON.setText(reportReason);


        FloatingActionButton report = (FloatingActionButton) findViewById(R.id.check);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {

                                Snackbar.make(view, "Reported Item has been accepted", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(InspectReportedItem.this);
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

                AcceptItemRequest registerRequest = new AcceptItemRequest(item_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(InspectReportedItem.this);
                queue.add(registerRequest);

            }
        });

        FloatingActionButton delete = (FloatingActionButton) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                Response.Listener<String> deleteItem = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {


                        try {
                            JSONObject jsonResponse = new JSONObject(response1);
                            boolean success1 = jsonResponse.getBoolean("success");

                            if (success1) {

                                Snackbar.make(view, "Reported Item has been denied and removed", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(InspectReportedItem.this);
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
                RequestQueue queue = Volley.newRequestQueue(InspectReportedItem.this);
                queue.add(Request);
            }
        });

    }
}