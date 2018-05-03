/* Author: Lau Tsz Chung
  * Submit page is final page before user make a borrow request,
   * this page will show the details such as, Lender email, user email, item id, start and end dates
   * once the user clicks the submit button, lender will get a message*/
package com.groupProject.borrowMe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Button;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.groupProject.borrowMe.Item.ItemDetails;
import com.groupProject.borrowMe.JSONRequests.SubmitItem;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;
import java.text.ParseException;
import java.util.concurrent.*;

public class Submit_Activity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

//Show the input details when user wants to borrow an item before submit
        final TextView StartDate = (TextView) findViewById( R.id.StartDate );
        final TextView EndDate = (TextView) findViewById( R.id.EndDate );
        final TextView Item_id = (TextView) findViewById( R.id.item_id );
        final TextView LenderEmail = (TextView) findViewById( R.id.Lenderemail );
        final TextView BorrowEmail = (TextView) findViewById( R.id.Borrowemail );
        final Button Submit = (Button) findViewById( R.id.bSubmit );

//Get from intent
        Intent incomingIntent = getIntent();
        final String LEmail = incomingIntent.getStringExtra( "Lenderemail" );
        final String BEmail = incomingIntent.getStringExtra( "Borrowemail" );
        final String id = incomingIntent.getStringExtra( "item_id" );
        final String ADate = incomingIntent.getStringExtra( "ADate" );
        final String UDate = incomingIntent.getStringExtra( "UDate" );
        StartDate.setText( ADate );
        EndDate.setText( UDate );
        LenderEmail.setText( LEmail );
        BorrowEmail.setText( BEmail );

        RadioGroup radioGroup;

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

//when Submit
        Submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responselistener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject( response );
                                    boolean success = jsonResponse.getBoolean( "success" );
                                    if (success) {
                                        Intent BackToMain = new Intent( Submit_Activity.this, MainActivity.class );
                                        BackToMain.putExtra( "email", BEmail );
                                        startActivity( BackToMain );

                                    } else {

                                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Submit_Activity.this);
                                        builder.setMessage("Failed!")
                                                .setNegativeButton("Retry", null)
                                                .create()
                                                .show();

                                }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        };
                        SubmitItem SubmitItem = new SubmitItem( LEmail, BEmail, id, ADate, UDate, responselistener );
                        RequestQueue queue = Volley.newRequestQueue( Submit_Activity.this );
                        queue.add( SubmitItem );

            }
        } );



    }



}


