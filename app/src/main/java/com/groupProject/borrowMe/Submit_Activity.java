package com.groupProject.borrowMe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Submit_Activity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        final TextView StartDate = (TextView) findViewById( R.id.StartDate );
        final TextView EndDate = (TextView) findViewById( R.id.EndDate );
        final TextView Item_id = (TextView) findViewById( R.id.item_id );
        final TextView LenderEmail = (TextView) findViewById( R.id.Lenderemail );
        final TextView BorrowEmail = (TextView) findViewById( R.id.Borrowemail );

        Intent incomingIntent = getIntent();
        final String LEmail = incomingIntent.getStringExtra( "Lenderemail" );
        final String BEmal = incomingIntent.getStringExtra( "Borrowemail" );
        final String id = incomingIntent.getStringExtra( "item_id" );
        final String ADate = incomingIntent.getStringExtra( "ADate" );
        final String UDate = incomingIntent.getStringExtra( "UDate" );

        StartDate.setText( ADate );
        EndDate.setText( UDate );
        Item_id.setText( id );
        LenderEmail.setText( LEmail );
        BorrowEmail.setText( BEmal );



    }

}

