/* Author: Lau Tsz Chung, Andrei Enache */
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
import com.groupProject.borrowMe.JSONRequests.Top_upRequest;
import com.groupProject.borrowMe.JSONRequests.WithdrawRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Top_upActivity extends AppCompatActivity{

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_topup);

//Fields
            final TextView Balance = (TextView) findViewById( R.id.Balance );
            final EditText etMoney = (EditText) findViewById( R.id.etMoney );
            final Button bTopUp = (Button) findViewById( R.id.bTopUp );

            final EditText etWithdraw = (EditText) findViewById( R.id.etWithdraw );
            final Button bWithdraw = (Button) findViewById( R.id.bWithdraw );

//Get vaules from intent
            Intent incomingIntent = getIntent();
            final String email = incomingIntent.getStringExtra( "email" );
            final int balance = incomingIntent.getIntExtra("balance",0);

//show the balance
            Balance.setText( "Balance : £ " + balance );

//Top up in order to borrow items
            bTopUp.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//Get inputs from user
                    String money = etMoney.getText().toString();
                    String tmp = String.valueOf( balance );

//check if the input is valid
                    if(money.isEmpty()){
                        AlertDialog.Builder builder = new AlertDialog.Builder( Top_upActivity.this );
                        builder.setMessage( "Please enter the amount of money to top up " )
                                .setNegativeButton( "Retry", null )
                                .create()
                                .show();

                    }else{
//input is valid
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
//Response from php code
                                    JSONObject jsonResponse = new JSONObject( response );
                                    boolean success = jsonResponse.getBoolean( "success" );
                                    if (success) {
//direct user back to the main page
                                        int newBalance = jsonResponse.getInt( "balance" );
                                        Intent BacktoMainintent = new Intent( Top_upActivity.this, MainActivity.class );
                                        BacktoMainintent.putExtra( "email", email );
                                        BacktoMainintent.putExtra( "balance", newBalance);
                                        Top_upActivity.this.startActivity( BacktoMainintent );
                                    } else {
//php code will check if the input is 0 or not
                                        AlertDialog.Builder builder = new AlertDialog.Builder( Top_upActivity.this );
                                        builder.setMessage( "Please enter a valid amount( not 0 )" )
                                                .setNegativeButton( "Retry", null )
                                                .create()
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
//Connect to database
                        Top_upRequest TopUpRequest = new Top_upRequest( email, tmp, money, responseListener );
                        RequestQueue queue = Volley.newRequestQueue( Top_upActivity.this );
                        queue.add( TopUpRequest );


                    }

                }
            } );

//Withdraw cash from account
            bWithdraw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String money = etWithdraw.getText().toString();
                    String tmp = String.valueOf( balance );
//check if there is enough balance
                    int difference = balance - Integer.parseInt(money);
//error
                    if(money.isEmpty() || difference < 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder( Top_upActivity.this );
                        builder.setMessage( "Please enter the the right amount to withdraw " )
                                .setNegativeButton( "Retry", null )
                                .create()
                                .show();

                    }else{
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
//Response from php code
                                    JSONObject jsonResponse = new JSONObject( response );
                                    boolean success = jsonResponse.getBoolean( "success" );
                                    if (success) {
 //direct user back to main page
                                        int newBalance = jsonResponse.getInt( "balance" );
                                        Intent BacktoMainintent = new Intent( Top_upActivity.this, MainActivity.class );
                                        BacktoMainintent.putExtra( "email", email );
                                        BacktoMainintent.putExtra( "balance", newBalance);
                                        Top_upActivity.this.startActivity( BacktoMainintent );
                                    } else {
//cant enter 0
                                        AlertDialog.Builder builder = new AlertDialog.Builder( Top_upActivity.this );
                                        builder.setMessage( "Please enter a valid amount( not 0 )" )
                                                .setNegativeButton( "Retry", null )
                                                .create()
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

//Connect to database
                        WithdrawRequest Request = new WithdrawRequest( email, tmp, money, responseListener );
                        RequestQueue queue = Volley.newRequestQueue( Top_upActivity.this );
                        queue.add( Request );


                    }


                }
            });


        }


}
