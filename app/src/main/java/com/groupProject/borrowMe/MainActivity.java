/* Author: Andrei Enache, Sebastián Arocha, Lau Tsz Chung
 * Main page for the app, include all major function on the left drawer, Add item, Top up, My item, FAQ
  * the centre of the page is search by departments, when user select one of the deparments on the list,
  * the app will direct user to the search results of that department
  * top right corner has a drop down menu, include settings, user details, logout*/
package com.groupProject.borrowMe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.groupProject.borrowMe.Chat.ChatroomsActivity;
import com.groupProject.borrowMe.Departments.AllDepartments;
import com.groupProject.borrowMe.Departments.DepartmentByName;
import com.groupProject.borrowMe.Departments.ItemSearch;
import com.groupProject.borrowMe.Item.Add_itemActivity;
import com.groupProject.borrowMe.Item.BorrowItemRequests;
import com.groupProject.borrowMe.Item.BorrowedItems;
import com.groupProject.borrowMe.Item.ItemDetails;
import com.groupProject.borrowMe.Item.LentItems;
import com.groupProject.borrowMe.Item.MyItems;
import com.groupProject.borrowMe.JSONRequests.FavouriteRequest;
import com.groupProject.borrowMe.User.UserDetails;
import com.groupProject.borrowMe.Chat.ChatActivity;

import java.util.Comparator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
//list of departments name
    private static String[] LANG = new String[] {"All Departments","Mobile Devices & Tablets" ,"Camera & Accessories",
            "Computer & Accessories", "Tools & Equipments", "Bicycles & E-Scooter",
            "Car Accessories", "Sports Equipments", "Party", "Clothing", "Costumes", "Travel Essentials",
            "Outdoor Essentials", "Board Games", "Toys", "Video Games", "Books", "Music Related", "Other"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




//The drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ListItem();
    }

//List the departments
    void ListItem(){
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

//When user select all departments, direct to AllDepartments class
                if(dep == "All Departments") {
                    Intent listItems = new Intent(MainActivity.this, AllDepartments.class);
                    listItems.putExtra("department", dep);
                    listItems.putExtra( "email", email );
                    MainActivity.this.startActivity(listItems);
                }else
                    {
//When user select a department, direct to Department by names
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
// Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        inflater.inflate(R.menu.main,menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent search = new Intent(MainActivity.this, ItemSearch.class);
                search.putExtra("searchWord",query);
                MainActivity.this.startActivity(search);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
//show user details
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

//choose to logout
        if (id == R.id.logout) {
            Intent registerIntent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(registerIntent);
        }

        return super.onOptionsItemSelected(item);
    }

//menu on the left, user can select options from there
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent incomingintent = getIntent();
        String email = incomingintent.getStringExtra("email");
//add item page
        if (id == R.id.add_item) {
            Intent intent = new Intent(MainActivity.this, Add_itemActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        }
        else if (id == R.id.top_up) {
//Top up

            Intent TopUpintent= new Intent( MainActivity.this, Top_upActivity.class );
            TopUpintent.putExtra( "email", email );
            startActivity( TopUpintent );

        }
        else if (id == R.id.fav_items) {
            Intent intent = new Intent(MainActivity.this, FavouriteActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        }
        else if (id == R.id.borrow_request) {

            Intent intent = new Intent(MainActivity.this, BorrowItemRequests.class);
            intent.putExtra("email", email);
            MainActivity.this.startActivity(intent);
            //

        }
        else if (id == R.id.my_items) {

            Intent intent = new Intent(MainActivity.this, MyItems.class);
            intent.putExtra("email", email);
            MainActivity.this.startActivity(intent);

        }

        else if (id == R.id.borrowed_items) {

            Intent intent = new Intent(MainActivity.this, BorrowedItems.class);
            intent.putExtra("email", email);
            MainActivity.this.startActivity(intent);

        } else if (id == R.id.lent_items) {

            Intent intent = new Intent(MainActivity.this, LentItems.class);
            intent.putExtra("email", email);
            MainActivity.this.startActivity(intent);


        }

        else if (id == R.id.message_user) {

            Intent intent = new Intent(MainActivity.this, ChatroomsActivity.class);
            intent.putExtra(ChatroomsActivity.EXTRA_EMAIL, email);
            startActivity(intent);

        } else if (id == R.id.faq){
//FAQ
            Intent FAQ = new Intent(MainActivity.this, FaqActivity.class);
            FAQ.putExtra( "email", email );
            MainActivity.this.startActivity(FAQ);
        } else if (id == R.id.support){
//Support ticket
            Intent SupportIntent = new Intent(MainActivity.this, SupportActivity.class);
            SupportIntent.putExtra( "email",email );
            MainActivity.this.startActivity(SupportIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
