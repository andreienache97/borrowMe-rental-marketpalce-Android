/* Author: Andrei Enache */
/* Updated by Sebastian Arocha */
package com.groupProject.borrowMe.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.groupProject.borrowMe.Item.CheckItems;
import com.groupProject.borrowMe.Item.ReportItems;
import com.groupProject.borrowMe.R;

public class AdminAreaActivity extends AppCompatActivity {

//Admin main page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_area);

        //return button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final AppCompatButton cCheck = (AppCompatButton) findViewById(R.id.check);
        final AppCompatButton rCheck = (AppCompatButton) findViewById(R.id.reportedItemsAdmin);

//Check item button is clicked
        cCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listItems = new Intent(AdminAreaActivity.this, CheckItems.class);
                AdminAreaActivity.this.startActivity(listItems);
            }

            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //      .setAction("Action", null).show();

        });

        rCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listItems = new Intent(AdminAreaActivity.this, ReportItems.class);
                AdminAreaActivity.this.startActivity(listItems);
            }
        });

    }
}
