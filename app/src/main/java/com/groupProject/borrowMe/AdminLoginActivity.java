package com.groupProject.borrowMe;

/**
 * Created by Enache on 13/04/2018.
 */

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class AdminLoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        final EditText adminName = (EditText) findViewById(R.id.etAdminUsername);
        final EditText adminPassword = (EditText) findViewById(R.id.etAdminPassword);
        final Button bLogin = (Button) findViewById(R.id.bSignIn);


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = adminName.getText().toString();
                final String password = adminPassword.getText().toString();


                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String name = jsonResponse.getString("name");
                                Intent intent = new Intent(AdminLoginActivity.this, AdminAreaActivity.class);
                                AdminLoginActivity.this.startActivity(intent);

                               // intent.putExtra("", name);
                                //intent.putExtra("age", age);
                                //intent.putExtra("username", username);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminLoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                AdminLoginRequest loginRequest = new AdminLoginRequest(name, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AdminLoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }}


