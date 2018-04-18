package com.groupProject.borrowMe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Comparator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    private static String[] LANG = new String[] {"All Departments","Books, Comic & Magazines", "Business, Office & Industrial", "Cameras & Photography", "Cars,Motorcycles & Vehicles", "Clothes, Shoes & Accessories", "Computers/Tablets & Networking", "DVDs, Films & TV", "Event Tickets", "Garden & Patio", "Health & Beauty", "Holiday & Travel", "Home,Furniture", "Mobile Phones & Communication", "Music, Sound & Vision", "Sporting Goods", "Video Games & Consoles", "Everything else"};


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
        listView = (ListView)findViewById(R.id.lv_display);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.department_list,LANG);
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
                String display = (String)listView.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, display, Toast.LENGTH_SHORT).show();
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

        if (id == R.id.add_item) {
            // Handle the add item action
        } else if (id == R.id.fav_items) {

        } else if (id == R.id.lent_items) {

        } else if (id == R.id.borrowed_items) {

        } else if (id == R.id.history) {

        } else if (id == R.id.message_user) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
