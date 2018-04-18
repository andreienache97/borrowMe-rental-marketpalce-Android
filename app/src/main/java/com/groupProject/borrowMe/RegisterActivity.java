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

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText email = (EditText) findViewById(R.id.etEmail);
        final EditText pass = (EditText) findViewById(R.id.etPassword);
        final EditText name = (EditText) findViewById(R.id.etName);
        final EditText phone = (EditText) findViewById(R.id.etPhone);
        final EditText address = (EditText) findViewById(R.id.etAddress);
        final EditText city = (EditText) findViewById(R.id.etCity);
        final EditText postcode = (EditText) findViewById(R.id.etPostcode);
        final Button bRegister = (Button) findViewById(R.id.bRegister);


        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean input = isInputEditTextEmail( email );
                String user_email = "";
                if (input == false) {

                } else {


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
                            JSONObject jsonResponse = new JSONObject( response );
                            boolean success = jsonResponse.getBoolean( "success" );
                            if (success) {

                                Intent intent = new Intent( RegisterActivity.this, LoginActivity.class );
                                RegisterActivity.this.startActivity( intent );
                            } else {
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

                RegisterRequest registerRequest = new RegisterRequest( user_email, user_password, user_name, user_phone, user_address, user_city, user_postcode, responseListener );
                RequestQueue queue = Volley.newRequestQueue( RegisterActivity.this );
                queue.add( registerRequest );
            }
        }
        });
    }

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