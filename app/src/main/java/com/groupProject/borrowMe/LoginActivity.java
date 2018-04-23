/* Author: Lau Tsz Chung*/
package com.groupProject.borrowMe;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.groupProject.borrowMe.Admin.AdminLoginActivity;
import com.groupProject.borrowMe.JSONRequests.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//Set the Edit text, TextView, Button
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final TextView tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        final TextView admin = (TextView) findViewById(R.id.admin_login);
        final Button bLogin = (Button) findViewById(R.id.bSignIn);

//When Click on admin TextView
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Direct to Admin Login Page
                Intent registerIntent = new Intent(LoginActivity.this, AdminLoginActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

//When Click on register
        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Direct to register page
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

//When Click on login
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Get the inputs from text fields
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

// Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
//Getting responses from php file
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String email = jsonResponse.getString( "email" );
                                String name = jsonResponse.getString("name");
                                String phone = jsonResponse.getString("phone");
                                String address = jsonResponse.getString("address");
                                String city = jsonResponse.getString("city");
                                String postcode = jsonResponse.getString("postcode");
                                String pass = jsonResponse.getString("postcode");
                                int balance = jsonResponse.getInt( "balance" );

//Login and direct to main page
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("email", email);
                                intent.putExtra( "password",pass );
                                intent.putExtra( "phone",phone );
                                intent.putExtra( "name",name );
                                intent.putExtra( "address",address );
                                intent.putExtra( "city",city );
                                intent.putExtra( "postcode",postcode );
                                intent.putExtra( "balance", balance);
                                LoginActivity.this.startActivity(intent);
                            } else {
//Cant login, no record found in database
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
//Login request to connect to the database with php code
                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
