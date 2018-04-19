package com.groupProject.borrowMe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;

import com.groupProject.borrowMe.R;

public class AdminAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_area);

        final AppCompatButton cCheck = (AppCompatButton) findViewById(R.id.check);


        cCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listItems = new Intent(AdminAreaActivity.this, CheckItems.class);
                AdminAreaActivity.this.startActivity(listItems);
            }

            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //      .setAction("Action", null).show();

        });
    }
}
