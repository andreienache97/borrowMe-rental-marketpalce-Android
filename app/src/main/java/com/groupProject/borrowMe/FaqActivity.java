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
import com.groupProject.borrowMe.JSONRequests.FaqRequest;
import com.groupProject.borrowMe.JSONRequests.SupportRequest;
import com.groupProject.borrowMe.MainActivity;
import com.groupProject.borrowMe.R;

import org.json.JSONException;
import org.json.JSONObject;

public class FaqActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        final TextView textView1 = findViewById(R.id.textView1);
        final TextView textView2 = findViewById(R.id.textView2);
        final TextView textView3 = findViewById(R.id.textView3);
        final TextView textView4 = findViewById(R.id.textView4);
        final TextView textView5 = findViewById(R.id.textView5);
        final TextView textView6 = findViewById(R.id.textView6);
        final TextView textView7 = findViewById(R.id.textView7);
        final TextView textView8 = findViewById(R.id.textView8);
        final TextView textView9 = findViewById(R.id.textView9);
        final TextView textView10 = findViewById(R.id.textView10);
        final EditText editText4 = findViewById(R.id.editText4);
        final Button submitQuestion = findViewById(R.id.submitQuestion);

        submitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String question = editText4.getText().toString();

                Intent incomingIntent = getIntent();
                final String email = incomingIntent.getStringExtra( "email" );

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            //Getting responses from php file
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String question = jsonResponse.getString("question");

                                Intent intent = new Intent(com.groupProject.borrowMe.FaqActivity.this, MainActivity.class);
                                intent.putExtra("question", question);
                                com.groupProject.borrowMe.FaqActivity.this.startActivity(intent);

                                AlertDialog.Builder builder = new AlertDialog.Builder(FaqActivity.this);
                                builder.setMessage("Your question was successfully submitted.")
                                        .setPositiveButton("Okay", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                FaqRequest FaqRequest = new FaqRequest(email, question, responseListener);
                RequestQueue queue = Volley.newRequestQueue(com.groupProject.borrowMe.FaqActivity.this);
                queue.add(FaqRequest);

            }
        });
    }
}
