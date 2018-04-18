package com.groupProject.borrowMe;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Add_itemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String[] DepartmentNames={"Select a department","Mobile Devices & Tablets" ,"Camera & Accessories",
    "Computer & Accessories", "Tools & Equipments", "Furnitures", "Home Appliances", "Bicycles & E-Scooter",
    "Car Accessories", "Sports Equipments", "Party", "Wedding Essentials", "Clothing", "Costumes", "Luxury", "Travel Essentials",
    "Outdoor Essentials", "Board Games", "Toys", "Video Games", "Books", "Healthcare Items", "Music Related", "Ohters"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

//The spinner
        Spinner spin = (Spinner) findViewById(R.id.simpleSpinner);
        assert spin != null;
        spin.setOnItemSelectedListener( this );

//Add array to the spinner
        ArrayAdapter<String> aa;
        aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,DepartmentNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

//Field
        final EditText etItemName = (EditText) findViewById( R.id.etItemName );
        final EditText etPrice = (EditText) findViewById( R.id.etPrice );
        final EditText etDes = (EditText) findViewById( R.id.etDes );
        final TextView tvADate = (TextView) findViewById( R.id.tvADate );
        final TextView tvUDate = (TextView) findViewById( R.id.tvUDate );
        final TextView DADate = (TextView) findViewById( R.id.DADate );
        final TextView DUDate = (TextView) findViewById( R.id.DUDate );
        final Button bSubmit = (Button) findViewById( R.id.bSubmit );
        Intent incomingIntent = getIntent();
        final String email = incomingIntent.getStringExtra( "email" );
        final String ADate = incomingIntent.getStringExtra( "ADate" );
        final String UDate = incomingIntent.getStringExtra( "UDate" );
            DADate.setText( ADate );
            DUDate.setText( UDate );


//When user click select avaliable date
        tvADate.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent SelectADate = new Intent( Add_itemActivity.this, AvailableDate.class );
                SelectADate.putExtra( "email",email );
                SelectADate.putExtra( "UDate",UDate );
                startActivity( SelectADate );
            }
        } );

//When user click select unavaliable date
        tvUDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SelectUDate = new Intent( Add_itemActivity.this, Unavaliable_Date.class );
                SelectUDate.putExtra( "email",email );
                SelectUDate.putExtra( "ADate", ADate );
                startActivity( SelectUDate );
            }
        } );

        bSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String item_name = etItemName.getText().toString();
                final String item_price = etPrice.getText().toString();
                final String item_Des = etDes.getText().toString();
                Spinner mySpinner=(Spinner) findViewById(R.id.simpleSpinner);
                final String Department = mySpinner.getSelectedItem().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject( response );
                            boolean success = jsonResponse.getBoolean( "success" );
                           if (success) {
                                String email = jsonResponse.getString( "email" );

                                Intent BacktoMainintent = new Intent( Add_itemActivity.this, MainActivity.class );
                               BacktoMainintent.putExtra( "email",email );
                                Add_itemActivity.this.startActivity( BacktoMainintent );
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder( Add_itemActivity.this );
                                builder.setMessage( "Failed" )
                                        .setNegativeButton( "Retry", null )
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                AddItemRequest addItemRequest = new AddItemRequest( email, item_name, item_price, item_Des, ADate, UDate, Department, responseListener );
                RequestQueue queue = Volley.newRequestQueue( Add_itemActivity.this );
                queue.add( addItemRequest);


            }
        } );



    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
