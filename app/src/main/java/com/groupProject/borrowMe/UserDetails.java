package com.groupProject.borrowMe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;

public class UserDetails extends AppCompatActivity {

    public AppCompatTextView textViewEmail;
    public AppCompatTextView textViewName;
    public AppCompatTextView textViewPhone;
    public AppCompatTextView textViewAddress;
    public AppCompatTextView textViewCity;
    public AppCompatTextView textViewPostcode;
    public  Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        textViewEmail = (AppCompatTextView) findViewById(R.id.textViewEmail);
        textViewPhone = (AppCompatTextView) findViewById(R.id.textViewPhone);
        textViewCity = (AppCompatTextView) findViewById(R.id.textViewCity);
        textViewAddress = (AppCompatTextView) findViewById(R.id.textViewAddress);
        textViewPostcode = (AppCompatTextView) findViewById(R.id.textViewPostcode);
        button = (Button) findViewById(R.id.bChange);



        Intent intent = getIntent();
      final  String password = intent.getStringExtra("password");
      final  String name = intent.getStringExtra("name");
      final  String email = intent.getStringExtra("email");
      final  String phone = intent.getStringExtra("phone");
      final  String address = intent.getStringExtra("address");
      final  String city = intent.getStringExtra("city");
      final  String postcode = intent.getStringExtra("postcode");

        textViewName.setText(name);
        textViewEmail.setText(email);
        textViewPhone.setText(phone);
        textViewCity.setText(city);
        textViewAddress.setText(address);
        textViewPostcode.setText(postcode);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChange = new Intent(UserDetails.this, ChangeUserDetails.class);
                intentChange.putExtra("email", email);
                intentChange.putExtra( "password",password );
                intentChange.putExtra( "phone",phone );
                intentChange.putExtra( "name",name );
                intentChange.putExtra( "address",address );
                intentChange.putExtra( "city",city );
                intentChange.putExtra( "postcode",postcode );
                UserDetails.this.startActivity(intentChange);
            }
        });

    }
}
