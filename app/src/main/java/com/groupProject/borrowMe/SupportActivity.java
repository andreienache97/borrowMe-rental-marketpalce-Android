package com.groupProject.borrowMe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        final Spinner spinner1 = findViewById(R.id.spinner1);
        final EditText editText4 = findViewById(R.id.editText4);
        final Button button = findViewById(R.id.button);

        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        String email = jsonResponse.getString("email");
                        String name = jsonResponse.getString("name");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}