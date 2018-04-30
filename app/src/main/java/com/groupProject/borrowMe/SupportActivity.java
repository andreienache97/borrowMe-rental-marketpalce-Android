package com.groupProject.borrowMe;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.groupProject.borrowMe.JSONRequests.SupportRequest;

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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String type = spinner1.getSelectedItem().toString();
                final String message = editText4.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            //Getting responses from php file
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String type = jsonResponse.getString("type");
                                String message = jsonResponse.getString("message");

                                Intent intent = new Intent(SupportActivity.this, MainActivity.class);
                                intent.putExtra("issue", type);
                                intent.putExtra( "comment",message );
                                SupportActivity.this.startActivity(intent);

                                AlertDialog.Builder builder = new AlertDialog.Builder(SupportActivity.this);
                                builder.setMessage("Your message was successfully submitted.")
                                        .setPositiveButton("Okay", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                SupportRequest SupportRequest = new SupportRequest(type, message, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SupportActivity.this);
                queue.add(SupportRequest);

            }
        });
    }
}