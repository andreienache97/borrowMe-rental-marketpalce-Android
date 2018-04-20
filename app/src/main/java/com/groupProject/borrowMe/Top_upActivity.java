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
import com.groupProject.borrowMe.JSONRequests.AddItemRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Top_upActivity extends AppCompatActivity{

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_topup);
            final TextView Balance = (TextView) findViewById( R.id.Balance );
            final EditText etMoney = (EditText) findViewById( R.id.etMoney );
            final Button bTopUp = (Button) findViewById( R.id.bTopUp );
            Intent incomingIntent = getIntent();
            final String email = incomingIntent.getStringExtra( "email" );
             final int balance = incomingIntent.getIntExtra("balance",0);
            Balance.setText( "Balance : £ " + balance );

            bTopUp.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String money = etMoney.getText().toString();
                    String tmp = String.valueOf( balance );

                    if(money.isEmpty()){
                        AlertDialog.Builder builder = new AlertDialog.Builder( Top_upActivity.this );
                        builder.setMessage( "Please enter the amount of money to top up " )
                                .setNegativeButton( "Retry", null )
                                .create()
                                .show();

                    }else{
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject( response );
                                    boolean success = jsonResponse.getBoolean( "success" );
                                    if (success) {
                                        int newBalance = jsonResponse.getInt( "balance" );
                                        Intent BacktoMainintent = new Intent( Top_upActivity.this, MainActivity.class );
                                        BacktoMainintent.putExtra( "email", email );
                                        BacktoMainintent.putExtra( "balance", newBalance);
                                        Top_upActivity.this.startActivity( BacktoMainintent );
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder( Top_upActivity.this );
                                        builder.setMessage( "Please enter a valid amount( > 0 )" )
                                                .setNegativeButton( "Retry", null )
                                                .create()
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        Top_upRequest TopUpRequest = new Top_upRequest( email, tmp, money, responseListener );
                        RequestQueue queue = Volley.newRequestQueue( Top_upActivity.this );
                        queue.add( TopUpRequest );


                    }

                }
            } );
        }


}
