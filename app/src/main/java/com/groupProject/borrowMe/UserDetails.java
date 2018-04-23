/* Author: Andrei Enache */
package com.groupProject.borrowMe;

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
import com.groupProject.borrowMe.JSONRequests.RequestUser;

import org.json.JSONException;
import org.json.JSONObject;

public class UserDetails extends AppCompatActivity {

//Fields
    public AppCompatTextView textViewEmail;
    public AppCompatTextView textViewName;
    public AppCompatTextView textViewPhone;
    public AppCompatTextView textViewAddress;
    public AppCompatTextView textViewCity;
    public AppCompatTextView textViewPostcode;
    public  Button button;
    String name, postcode, password, city, address, phone,email;
    String Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        textViewEmail = (AppCompatTextView) findViewById(R.id.textViewEmail);
        textViewPhone = (AppCompatTextView) findViewById(R.id.textViewPhone);
        textViewCity = (AppCompatTextView) findViewById(R.id.textViewCity);
        textViewAddress = (AppCompatTextView) findViewById(R.id.textViewAddress);
        textViewPostcode = (AppCompatTextView) findViewById(R.id.textViewPostcode);
        button = (Button) findViewById(R.id.bChange);

//get values from intent
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        Email = email;

//show the user details
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        email = jsonResponse.getString( "email" );
                        name = jsonResponse.getString("name");
                        phone = jsonResponse.getString("phone");
                        address = jsonResponse.getString("address");
                        city = jsonResponse.getString("city");
                        postcode = jsonResponse.getString("postcode");
                        password = jsonResponse.getString("password");


                        textViewName.setText(name);
                        textViewEmail.setText(email);
                        textViewPhone.setText(phone);
                        textViewCity.setText(city);
                        textViewAddress.setText(address);
                        textViewPostcode.setText(postcode);

                    } else {
 //Error
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserDetails.this);
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

//Connec to database
        RequestUser loginRequest = new RequestUser(Email, responseListener);
        RequestQueue queue = Volley.newRequestQueue(UserDetails.this);
        queue.add(loginRequest);

//update changes
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChange = new Intent(UserDetails.this, ChangeUserDetails.class);
                intentChange.putExtra("email", Email);
                intentChange.putExtra( "password",password );
                intentChange.putExtra( "phone",phone );
                intentChange.putExtra( "name",name );
                intentChange.putExtra( "address",address );
                intentChange.putExtra( "city",city );
                intentChange.putExtra( "postcode",postcode );
                UserDetails.this.startActivity(intentChange);
            }
        });
    }
}





