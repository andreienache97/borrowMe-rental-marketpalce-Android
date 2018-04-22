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
        final String email = incomingIntent.getStringExtra( "email" );
        final String UDate = incomingIntent.getStringExtra( "UDate" );
        final String name = incomingIntent.getStringExtra( "Name" );
        final String price = incomingIntent.getStringExtra( "Price" );
        final String des = incomingIntent.getStringExtra( "Des" );

        ACalendarView.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = i + "-" + (i1+1) + "-" + i2 ;
                Intent BackToAddItem = new Intent( AvailableDate.this, Add_itemActivity.class );
                BackToAddItem.putExtra( "email", email );
                BackToAddItem.putExtra( "ADate", date);
                BackToAddItem.putExtra( "UDate", UDate );
                BackToAddItem.putExtra( "Name", name );
                BackToAddItem.putExtra( "Price", price );
                BackToAddItem.putExtra( "Des", des );
                startActivity( BackToAddItem );
            }
        } );

    }
}
