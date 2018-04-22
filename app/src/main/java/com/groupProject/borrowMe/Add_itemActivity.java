package com.groupProject.borrowMe;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.groupProject.borrowMe.JSONRequests.AddItemRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Add_itemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String[] DepartmentNames={"Select a department","Mobile Devices & Tablets" ,"Camera & Accessories",
    "Computer & Accessories", "Tools & Equipments", "Bicycles & E-Scooter",
    "Car Accessories", "Sports Equipments", "Party", "Clothing", "Costumes", "Travel Essentials",
    "Outdoor Essentials", "Board Games", "Toys", "Video Games", "Books", "Music Related", "Other"};

    private static final String [] Deposit = {"Refundable deposit","none - £0","low - £25", "normal - £75", "high - £125"};
    private static final String [] Fine = {"Late return fine","low - £15/day", "normal - £30/day", "high - £60/day"};

    private int finalD;
    private int finalF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

//The spinner
        Spinner spin = (Spinner) findViewById(R.id.simpleSpinner);
        assert spin != null;
        spin.setOnItemSelectedListener( this );

        Spinner deposit = (Spinner) findViewById(R.id.simpleSpinner2);
        assert deposit != null;
        deposit.setOnItemSelectedListener( this );

        Spinner fine = (Spinner) findViewById(R.id.simpleSpinner3);
        assert fine != null;
        fine.setOnItemSelectedListener( this );



//Add array to the spinner
        ArrayAdapter<String> aa;
        aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,DepartmentNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        ArrayAdapter<String> aadeposit;
        aadeposit = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,Deposit);
        aadeposit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deposit.setAdapter(aadeposit);

        ArrayAdapter<String> aafine;
        aafine = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,Fine);
        aafine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fine.setAdapter(aafine);

//Field
        final EditText etItemName = (EditText) findViewById( R.id.etItemName );
        final EditText etPrice = (EditText) findViewById( R.id.etPrice );
        final EditText etDes = (EditText) findViewById( R.id.etDes );
        final TextView tvADate = (TextView) findViewById( R.id.tvADate );
        final TextView tvUDate = (TextView) findViewById( R.id.tvUDate );
        final TextView DADate = (TextView) findViewById( R.id.DADate );
        final TextView DUDate = (TextView) findViewById( R.id.DUDate );
        final AppCompatButton bSubmit = (AppCompatButton) findViewById( R.id.bSubmit );

        Intent incomingIntent = getIntent();

        final String email = incomingIntent.getStringExtra( "email" );
        final String ADate = incomingIntent.getStringExtra( "ADate" );
        final String UDate = incomingIntent.getStringExtra( "UDate" );
        final String N1 = incomingIntent.getStringExtra( "Name" );
        final String P1 = incomingIntent.getStringExtra( "Price" );
        final String D1 = incomingIntent.getStringExtra( "Des" );

            DADate.setText( ADate );
            DUDate.setText( UDate );
            etItemName.setText( N1 );
            etPrice.setText( P1 );
            etDes.setText( D1 );


//When user click select available date
        tvADate.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String item_name = etItemName.getText().toString();
                final String item_price = etPrice.getText().toString();
                final String item_Des = etDes.getText().toString();
                Intent SelectADate = new Intent( Add_itemActivity.this, AvailableDate.class );
                SelectADate.putExtra( "email",email );
                SelectADate.putExtra( "UDate",UDate );
                SelectADate.putExtra( "Name",item_name );
                SelectADate.putExtra( "Price",item_price );
                SelectADate.putExtra( "Des",item_Des );
                startActivity( SelectADate );
            }
        } );

//When user click select unavailable date
        tvUDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String item_name = etItemName.getText().toString();
                final String item_price = etPrice.getText().toString();
                final String item_Des = etDes.getText().toString();
                Intent SelectUDate = new Intent( Add_itemActivity.this, Unavaliable_Date.class );
                SelectUDate.putExtra( "email",email );
                SelectUDate.putExtra( "ADate", ADate );
                SelectUDate.putExtra( "Name",item_name );
                SelectUDate.putExtra( "Price",item_price );
                SelectUDate.putExtra( "Des",item_Des );
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
                Spinner myDeposit=(Spinner) findViewById(R.id.simpleSpinner2);
                final String deposit = myDeposit.getSelectedItem().toString();
                Spinner myfine=(Spinner) findViewById(R.id.simpleSpinner3);
                final String fine = myfine.getSelectedItem().toString();
                boolean check = TextIsNull( item_name,  item_price,  item_Des,  ADate,  UDate);
                boolean test = ValidDates(ADate,UDate);
                if(!ValidDepartmentAndFine( Department, deposit, fine ) || !check || !test){


                } else {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject( response );
                                boolean success = jsonResponse.getBoolean( "success" );
                                if (success) {
                                    String email = jsonResponse.getString( "email" );

                                    Intent BacktoMainintent = new Intent( Add_itemActivity.this, MainActivity.class );
                                    BacktoMainintent.putExtra( "email", email );
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

                    AddItemRequest addItemRequest = new AddItemRequest( email, item_name, item_price, item_Des, ADate, UDate, Department, finalD, finalF, responseListener );
                    RequestQueue queue = Volley.newRequestQueue( Add_itemActivity.this );
                    queue.add( addItemRequest );

                }
            }
        } );



    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean ValidDepartmentAndFine(String Department, String deposit, String fine) {
        if (Department.equals( DepartmentNames[0] )) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Add_itemActivity.this);
            builder.setMessage("Please select a department")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return false;
        } else { if(deposit.equals( Deposit[0] )){
                AlertDialog.Builder builder = new AlertDialog.Builder(Add_itemActivity.this);
                builder.setMessage("Please select a deposit plan ")
                        .setNegativeButton("Retry", null)
                        .create()
                        .show();
                return false;
                 }else{ if(fine.equals( Fine[0] )){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Add_itemActivity.this);
                        builder.setMessage("Please select a late return fine plan ")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                        return false;
                        }else{


                             }
                }
        }
        if(deposit.equals( Deposit[1] ))
            finalD = 0;
        if(deposit.equals( Deposit[2] ))
            finalD = 25;
        if(deposit.equals( Deposit[3] ))
            finalD = 75;
        if(deposit.equals( Deposit[4] ))
            finalD = 125;

        if(fine.equals( Fine[1] ))
            finalF = 15;
        if(fine.equals( Fine[2] ))
            finalF = 30;
        if(fine.equals( Fine[3] ))
            finalF = 60;

        return true;
    }

    private boolean TextIsNull(String Name, String Price, String Des, String ADate, String UDate) {
        if (Name.isEmpty() ) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Add_itemActivity.this);
            builder.setMessage("Please enter the item name")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return false;
        } else { if (Price.isEmpty() ) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Add_itemActivity.this);
                builder.setMessage("Please enter the price")
                        .setNegativeButton("Retry", null)
                        .create()
                        .show();
                return false;
                } else{  if (Des.isEmpty() ) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Add_itemActivity.this);
                        builder.setMessage("Please enter the desription")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                        return false;
                        } else{ if (ADate.isEmpty() || UDate.isEmpty()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Add_itemActivity.this);
                                builder.setMessage("Please select the date")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                                return false;
                                } else {}
                        }
                 }

        }
        return true;
    }

    private boolean ValidDates(String ADate, String UDate) {
        Calendar calendar;
        SimpleDateFormat simpleDateFormat;
        String Date;

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd" );
        Date = simpleDateFormat.format( calendar.getTime() );
        String splitCDate[] = Date.split( "-" );
        String splitADate[] = ADate.split( "-" );
        String splitUDate[] = UDate.split( "-" );

        int CDateArray[] = new int[3];
        int ADateArray[] = new int[3];
        int UDateArray[] = new int[3];

        for (int i = 0 ; i < 3; i++ ){
            CDateArray[i] = Integer.parseInt( splitCDate[i] );
            ADateArray[i] = Integer.parseInt( splitADate[i] );
            UDateArray[i] = Integer.parseInt( splitUDate[i] );

        }


        if (ADateArray[0] >= CDateArray[0] && UDateArray[0] >= ADateArray[0]) {
            if(UDateArray[0] > ADateArray[0]){return true;} else{
                if(ADateArray[1] >= CDateArray[1] && UDateArray[1] >= ADateArray[1]){
                    if(UDateArray[1] > ADateArray[1]){return true;} else{
                        if (ADateArray[2] >= CDateArray[2] && UDateArray[2] > ADateArray[2]){
                            return true;
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Add_itemActivity.this);
                            builder.setMessage("Please select a valid day")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                            return false;
                            }
                        }
                }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(Add_itemActivity.this);
                        builder.setMessage("Please select a valid month")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                        return false;
                  }
            }
        } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Add_itemActivity.this);
                builder.setMessage("Please select a valid year")
                        .setNegativeButton("Retry", null)
                        .create()
                        .show();
                return false;
            }



    }
}
