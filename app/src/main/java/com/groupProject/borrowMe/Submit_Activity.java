/* Author: Lau Tsz Chung
  * Submit page is final page before user make a borrow request,
   * this page will show the details such as, Lender email, user email, item id, start and end dates
   * once the user clicks the submit button, lender will get a message
   * This page will also check the dates as well*/
package com.groupProject.borrowMe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.groupProject.borrowMe.JSONRequests.GetDate;
import com.groupProject.borrowMe.JSONRequests.SubmitItem;

import org.json.JSONException;
import org.json.JSONObject;

public class Submit_Activity extends AppCompatActivity{
    private String Start_Date;
    private String End_Date;
    private boolean checkedA;
    private boolean checkedU = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        //return button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        Start_Date = ADate;
        final String UDate = incomingIntent.getStringExtra( "UDate" );
        End_Date = UDate;

        //get the dates from database
        Response.Listener <String> getDate = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject( response );
                    boolean success = jsonResponse.getBoolean( "success" );
                    if(success){
                        String ATheDate = jsonResponse.getString( "ADate" );
                        String UTheDate = jsonResponse.getString( "UDate" );
                        checkedA = CheckADate(ATheDate);
                        checkedU = CheckUDate(UTheDate);
                        StartDate.setText( ADate );
                        EndDate.setText( UDate );
                        LenderEmail.setText( LEmail );
                        BorrowEmail.setText( BEmail );

                    }else{
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder( Submit_Activity.this );
                        builder.setMessage( "Cant find the dates " )
                                .setNegativeButton( "Retry", null )
                                .create()
                                .show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };
        GetDate getTheDate = new GetDate(id,getDate);
        RequestQueue queue = Volley.newRequestQueue( Submit_Activity.this );
        queue.add( getTheDate );


        RadioGroup radioGroup;

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

            //when Submit
            Submit.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                if(checkedA && checkedU){
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


                }else{
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Submit_Activity.this);
                    builder.setMessage("period selected is not available.")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }


                }
            } );








    }

    //Check if the End date is before the unavailable date
    private boolean CheckUDate(String uTheDate) {
        String splitUDate[] = uTheDate.split( "-" );
        String splitEndDate[] = End_Date.split( "-" );
        int EndDate[] = new int[3];
        int UDate[] = new int[3];
        for (int i = 0 ; i < 3; i++ ){
            UDate[i] = Integer.parseInt( splitUDate[i] );
            EndDate[i] = Integer.parseInt( splitEndDate[i] );

        }
        if(EndDate[0] < UDate[0]){
            checkedU = true;
            return true;

        }else{
            if(EndDate[0] == UDate[0]){
                if(EndDate[1] < UDate[1]){
                    checkedU = true;
                    return true;
                }else{
                    if(EndDate[1] == UDate[1]){
                        if(EndDate[2] < UDate[2]){
                            checkedU = true;
                            return true;
                        }else{}

                    }else{}

                }
            }else{}
        }
        return false;


    }

    //check if the Start date is after the available date
    private boolean CheckADate(String aTheDate) {
            String splitADate[] = aTheDate.split( "-" );
            String splitStartDate[] = Start_Date.split( "-" );
            int StartDate[] = new int[3];
            int ADate[] = new int[3];
            for (int i = 0 ; i < 3; i++ ){
                ADate[i] = Integer.parseInt( splitADate[i] );
                StartDate[i] = Integer.parseInt( splitStartDate[i] );

            }

            if(StartDate[0] > ADate[0]){
                checkedA = true;
                return true;

            }else{
                if(StartDate[0] == ADate[0]){
                    if(StartDate[1] > ADate[1]){
                        checkedA = true;
                        return true;
                    }else{
                        if(StartDate[1] == ADate[1]){
                            if(StartDate[2] > ADate[2]){
                                checkedA = true;
                                return true;
                            }else{}

                        }else{}

                    }
                }else{}
            }

    return false;

    }


}


