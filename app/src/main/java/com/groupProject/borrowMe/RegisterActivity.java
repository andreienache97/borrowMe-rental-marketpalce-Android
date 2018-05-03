/* Author: Lau Tsz Chung,Andrei Enache
 * This is the register page which allows new users to register,
  * they will need to enter some personal details, email, password, address etc
  * email,password will be used for login */
package com.groupProject.borrowMe;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.groupProject.borrowMe.JSONRequests.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //return button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//set EditText and Buttons
        final EditText email = (EditText) findViewById(R.id.etEmail);
        final EditText pass = (EditText) findViewById(R.id.etPassword);
        final EditText name = (EditText) findViewById(R.id.etName);
        final EditText phone = (EditText) findViewById(R.id.etPhone);
        final EditText address = (EditText) findViewById(R.id.etAddress);
        final EditText city = (EditText) findViewById(R.id.etCity);
        final EditText postcode = (EditText) findViewById(R.id.etPostcode);
        final Button bRegister = (Button) findViewById(R.id.bRegister);

//When user clicks the register button
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Validation for email
                boolean input = isInputEditTextEmail( email );
                String user_email;
                if (!input) {
//when the email is not in correct format
                } else {
//email is on the correct format

//get the inputs from user
                             user_email = email.getText().toString();
                final String user_password = pass.getText().toString();
                final String user_name = name.getText().toString();
                final String user_phone = phone.getText().toString();
                final String user_address = address.getText().toString();
                final String user_city = city.getText().toString();
                final String user_postcode = postcode.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//response from php code
                            JSONObject jsonResponse = new JSONObject( response );
                            boolean success = jsonResponse.getBoolean( "success" );
                            if (success) {
//if registerd, direct to login page and allow user to login
                                Intent intent = new Intent( RegisterActivity.this, LoginActivity.class );
                                RegisterActivity.this.startActivity( intent );
                            } else {
//cant register, email has been used in our database, password in wrong format
                                AlertDialog.Builder builder = new AlertDialog.Builder( RegisterActivity.this );
                                builder.setMessage( "Register Failed" )
                                        .setNegativeButton( "Retry", null )
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
//Register request to connect the database by php code
                RegisterRequest registerRequest = new RegisterRequest( user_email, user_password, user_name, user_phone, user_address, user_city, user_postcode, responseListener );
                RequestQueue queue = Volley.newRequestQueue( RegisterActivity.this );
                queue.add( registerRequest );
            }
        }
        });
    }

//email validation
    public boolean isInputEditTextEmail(EditText EMAIL) {
        String value = EMAIL.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("Insert valid email")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return false;
        } else {

        }
        return true;
    }
}