package com.groupProject.borrowMe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.groupProject.borrowMe.Departments.AllDepartments;
import com.groupProject.borrowMe.Departments.DepartmentByName;

import java.util.Comparator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    private static String[] LANG = new String[] {"All Departments","Mobile Devices & Tablets" ,"Camera & Accessories",
            "Computer & Accessories", "Tools & Equipments", "Bicycles & E-Scooter",
            "Car Accessories", "Sports Equipments", "Party", "Clothing", "Costumes", "Travel Essentials",
            "Outdoor Essentials", "Board Games", "Toys", "Video Games", "Books", "Music Related", "Other"};

    //HI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(MainActivity.this, HelpActivity.class);
                MainActivity.this.startActivity(registerIntent);
            }

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();

        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ListItem();
    }



    void ListItem(){
        //
        listView = (ListView)findViewById(R.id.lv_display);
        ArrayAdapter<String> adapter = new ArrayAdapter<>( this, R.layout.department_list, LANG );
        adapter.sort(new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dep = (String)listView.getItemAtPosition(position);
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");

                if(dep == "All Departments") {
                    Intent listItems = new Intent(MainActivity.this, AllDepartments.class);
                    listItems.putExtra("department", dep);
                    listItems.putExtra( "email", email );
                    MainActivity.this.startActivity(listItems);
                }else
                    {
                    Intent listItems = new Intent(MainActivity.this, DepartmentByName.class);
                    listItems.putExtra("department", dep);
                    listItems.putExtra( "email", email );
                    MainActivity.this.startActivity(listItems);

                }

            }
        });
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //variablegoeshere.getFilter().filter(newText);
                // result goes here
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);


        // we don't need this anymore
        // Inflate the menu; this adds items to the action bar if it is present.
//        inflater.inflate(R.menu.main, menu);
//        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent registerIntent = new Intent(MainActivity.this, SettingsActivity.class);
            MainActivity.this.startActivity(registerIntent);
        }

        if (id == R.id.user_details) {
            Intent intent = getIntent();
            String name = intent.getStringExtra("name");
            String email = intent.getStringExtra("email");
            String phone = intent.getStringExtra("phone");
            String address = intent.getStringExtra("address");
            String city = intent.getStringExtra("city");
            String postcode = intent.getStringExtra("postcode");
            String password = intent.getStringExtra("password");


            Intent IntentDetails = new Intent(MainActivity.this, UserDetails.class);
            IntentDetails.putExtra("email", email);
            IntentDetails.putExtra( "password",password );
            IntentDetails.putExtra( "phone",phone );
            IntentDetails.putExtra( "name",name );
            IntentDetails.putExtra( "address",address );
            IntentDetails.putExtra( "city",city );
            IntentDetails.putExtra( "postcode",postcode );

            MainActivity.this.startActivity(IntentDetails);
        }

        if (id == R.id.logout) {
            Intent registerIntent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(registerIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent incomingintent = getIntent();
        String email = incomingintent.getStringExtra("email");
        int balance = incomingintent.getIntExtra( "balance",0 );

        if (id == R.id.add_item) {
            Intent intent = new Intent(MainActivity.this, Add_itemActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        }
        else if (id == R.id.top_up) {
            Intent TopUpintent= new Intent( MainActivity.this, Top_upActivity.class );
            TopUpintent.putExtra( "email", email );
            TopUpintent.putExtra( "balance", balance );
            startActivity( TopUpintent );


        }
        else if (id == R.id.fav_items) {

        }
        else if (id == R.id.borrow_request) {

        }
        else if (id == R.id.my_items) {

        }

        else if (id == R.id.lent_items) {

        } else if (id == R.id.borrowed_items) {

        }

        else if (id == R.id.message_user) {

        } else if (id == R.id.faq){
            Intent registerIntent = new Intent(MainActivity.this, FaqActivity.class);
            MainActivity.this.startActivity(registerIntent);
        } else if (id == R.id.support){
            Intent registerIntent = new Intent(MainActivity.this, SupportActivity.class);
            MainActivity.this.startActivity(registerIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
