package com.groupProject.borrowMe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;

public class Unavaliable_Date extends AppCompatActivity{

    private CalendarView UCalendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.calendar_u );

        UCalendarView = (CalendarView) findViewById( R.id.calendarView2 );
        Intent incomingIntent = getIntent();
        final String email = incomingIntent.getStringExtra( "email" );
        final String ADate = incomingIntent.getStringExtra( "ADate" );
        final String name = incomingIntent.getStringExtra( "Name" );
        final String price = incomingIntent.getStringExtra( "Price" );
        final String des = incomingIntent.getStringExtra( "Des" );

        UCalendarView.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = i + "-" + (i1+1) + "-" + i2 ;
                Intent BackToAddItem = new Intent( Unavaliable_Date.this, Add_itemActivity.class );
                BackToAddItem.putExtra( "email", email );
                BackToAddItem.putExtra( "UDate", date);
                BackToAddItem.putExtra( "ADate", ADate );
                BackToAddItem.putExtra( "Name", name );
                BackToAddItem.putExtra( "Price", price );
                BackToAddItem.putExtra( "Des", des );
                startActivity( BackToAddItem );
            }
        } );

    }
}