/* Author: Lau Tsz Chung */
package com.groupProject.borrowMe.Helpers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;

import com.groupProject.borrowMe.Item.Add_itemActivity;
import com.groupProject.borrowMe.R;
import com.groupProject.borrowMe.Submit_Activity;

public class Unavaliable_Date extends AppCompatActivity{

    private CalendarView UCalendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.calendar_u );

        UCalendarView = (CalendarView) findViewById( R.id.calendarView2 );
        Intent incomingIntent = getIntent();
//Got from intent
        final String email = incomingIntent.getStringExtra( "email" );
        final String ADate = incomingIntent.getStringExtra( "ADate" );
        final String name = incomingIntent.getStringExtra( "Name" );
        final String price = incomingIntent.getStringExtra( "Price" );
        final String des = incomingIntent.getStringExtra( "Des" );

        final String LenderEmail = incomingIntent.getStringExtra( "Lenderemail" );
        final String BorrowEmal = incomingIntent.getStringExtra( "Borrowemail" );
        final String item_id = incomingIntent.getStringExtra( "item_id" );
        final int check = incomingIntent.getIntExtra( "FromItemDetail",1 );

//When the user chose a date
        UCalendarView.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = i + "-" + (i1+1) + "-" + i2 ;
                if(check == 0){
//"check" is to check which page come from, if check = 0, its from item details, which means user is trying to borrow an item
                    Intent GotoNext = new Intent( Unavaliable_Date.this, Submit_Activity.class );
                    GotoNext.putExtra( "Lenderemail", LenderEmail );
                    GotoNext.putExtra( "Borrowemail", BorrowEmal );
                    GotoNext.putExtra( "item_id", item_id );
                    GotoNext.putExtra( "FromItemDetail", check );
                    GotoNext.putExtra( "ADate", ADate );
                    GotoNext.putExtra( "UDate",date );
                    startActivity( GotoNext );

                }else {
//if its not from item details, must be from add item page, which means the user is adding an item
                    Intent BackToAddItem = new Intent( Unavaliable_Date.this, Add_itemActivity.class );
                    BackToAddItem.putExtra( "email", email );
                    BackToAddItem.putExtra( "UDate", date );
                    BackToAddItem.putExtra( "ADate", ADate );
                    BackToAddItem.putExtra( "Name", name );
                    BackToAddItem.putExtra( "Price", price );
                    BackToAddItem.putExtra( "Des", des );
                    startActivity( BackToAddItem );
                }
            }
        } );

    }
}
