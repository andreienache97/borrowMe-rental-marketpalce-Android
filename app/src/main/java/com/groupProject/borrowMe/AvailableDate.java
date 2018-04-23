/* Author: Lau Tsz Chung, */
package com.groupProject.borrowMe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;

public class AvailableDate extends AppCompatActivity{

    private CalendarView ACalendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.calendar_a );

        ACalendarView = (CalendarView) findViewById( R.id.calendarView );
        Intent incomingIntent = getIntent();
//Getting variables from intent
        final String email = incomingIntent.getStringExtra( "email" );
        final String UDate = incomingIntent.getStringExtra( "UDate" );
        final String name = incomingIntent.getStringExtra( "Name" );
        final String price = incomingIntent.getStringExtra( "Price" );
        final String des = incomingIntent.getStringExtra( "Des" );

        final String LenderEmail = incomingIntent.getStringExtra( "Lenderemail" );
        final String BorrowEmal = incomingIntent.getStringExtra( "Borrowemail" );
        final String item_id = incomingIntent.getStringExtra( "item_id" );
        final int check = incomingIntent.getIntExtra( "FromItemDetail",1 );

        ACalendarView.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = i + "-" + (i1+1) + "-" + i2 ;
//"check" is to check which page come from, if check = 0, its from item details, which means user is trying to borrow an item
                if(check == 0){
                    Intent GoToUDate = new Intent( AvailableDate.this, Unavaliable_Date.class );
                    GoToUDate.putExtra( "Lenderemail", LenderEmail );
                    GoToUDate.putExtra( "Borrowemail", BorrowEmal );
                    GoToUDate.putExtra( "item_id", item_id );
                    GoToUDate.putExtra( "FromItemDetail", check );
                    GoToUDate.putExtra( "ADate", date );
                    startActivity( GoToUDate );

                }else {
//if its not from item details, must be from add item page, which means the user is adding an item
                    Intent BackToAddItem = new Intent( AvailableDate.this, Add_itemActivity.class );
                    BackToAddItem.putExtra( "email", email );
                    BackToAddItem.putExtra( "ADate", date );
                    BackToAddItem.putExtra( "UDate", UDate );
                    BackToAddItem.putExtra( "Name", name );
                    BackToAddItem.putExtra( "Price", price );
                    BackToAddItem.putExtra( "Des", des );
                    startActivity( BackToAddItem );
                }
            }
        } );

    }
}
