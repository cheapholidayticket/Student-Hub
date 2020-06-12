package com.example.jobportalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobportalapp.Admin.MainActivity;
import com.example.jobportalapp.Model_helper_classes.AttractionsHelper;
import com.example.jobportalapp.Model_helper_classes.ClubHelper;
import com.example.jobportalapp.Model_helper_classes.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;


public class DashboardHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //drawerlayout variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    //link image for schools
    ImageView businessSchool;
    ImageView engineerSchool;
    ImageView foundation;
    ImageView science;

    //notice recycler activities
    private DatabaseReference mAllJobPost; //ref
    private FirebaseRecyclerOptions<Data> options;
    private FirebaseRecyclerAdapter<Data, AllViewHolder> adapter;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    EditText inputSearch;
    //notice recycler activities//

    //clubs recyclerview activities
    private ClubsAdapter clubAdapter;
    private RecyclerView clubRecyclerView;
    RecyclerView.LayoutManager clubLayoutManager;
    //clubs recyclerview activities//

    //attractions recyclerview activities
    private AttractionsAdapter attractionsAdapterAdapter;
    private RecyclerView attractionsRecyclerView;
    RecyclerView.LayoutManager attractionsLayoutManager;
    //attractions recyclerview activities//

    //open drawer
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
        {
            drawerLayout.openDrawer(GravityCompat.START);
            //neccessory
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //drawer to avoid closing application on backpressed when drawer open
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }

    //clubs recyclerview activities
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard_home);


    //drawer activities
    drawerLayout = findViewById(R.id.drawer_layout);
    navigationView = findViewById(R.id.nav_view);
    navigationView.bringToFront();
    navigationView.setNavigationItemSelectedListener(this);
    navigationView.setCheckedItem(R.id.nav_home);

    //toolbar
    toolbar = findViewById(R.id.toolbar_menu);
    setSupportActionBar(toolbar);

    ActionBar actionbar = getSupportActionBar();
    actionbar.setHomeAsUpIndicator(R.drawable.menu);
    actionbar.setDisplayHomeAsUpEnabled(true);

    //clubs recyclerview activities/
        clubRecyclerView = findViewById(R.id.clubs_recycler);
        clubLayoutManager = new LinearLayoutManager(this);
        clubRecyclerView.setLayoutManager(clubLayoutManager);
        cRecyclerView();
    //clubs recyclerview activities//

    //attractions recyclerview activities
        attractionsRecyclerView = findViewById(R.id.attractions_recycler);
        attractionsLayoutManager = new LinearLayoutManager(this);
        attractionsRecyclerView.setLayoutManager(attractionsLayoutManager);
        aRecyclerView();
    //attractions recyclerview activities//

        //views for school
        businessSchool = findViewById(R.id.businessSchool);
        businessSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.psb-academy.edu.sg/school/school-of-business-and-management";
                Intent intent = new Intent (Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        engineerSchool = findViewById(R.id.engineerSchool);
        engineerSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.psb-academy.edu.sg/school/school-of-engineering-and-technology";
                Intent intent = new Intent (Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        foundation = findViewById(R.id.foundation);
        foundation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.psb-academy.edu.sg/school/school-of-postgraduate-studies";
                Intent intent = new Intent (Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        science = findViewById(R.id.scienceSchool);
        science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.psb-academy.edu.sg/school/school-of-postgraduate-studies";
                Intent intent = new Intent (Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        //notice recycler activities
        //database
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        //String uId = mUser.getUid();
        mAllJobPost = FirebaseDatabase.getInstance().getReference()
                .child("Public Database");
        mAllJobPost.keepSynced(true);

        inputSearch = findViewById(R.id.input_Search);
        recyclerView = findViewById(R.id.featured_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, true));

        LoadData("");

    }

    public void view_All (View view){
        Intent intent = new Intent (DashboardHome.this, AllJobActivity.class);
        startActivity(intent);
    }


    private void LoadData(String data) {

        Query query = mAllJobPost.orderByChild("title")
                .startAt(data.toLowerCase()).endAt(data.toLowerCase() + "\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(query, Data.class).build();

        adapter = new FirebaseRecyclerAdapter<Data, AllViewHolder>(options) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected void onBindViewHolder(@NonNull AllViewHolder holder, final int position, @NonNull final Data model) {
                holder.mTitle.setText(model.getTitle());
                holder.mDate.setText(model.getDate());
                holder.mDescription.setText(model.getDescription());

                Picasso.get().load(model.getImageUrl()).into(holder.imageView);

                //click listener to view notice
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ViewNotice.class);
                        intent.putExtra("key", getRef(position).getKey());
                        startActivity(intent);

                    }
                });


                //getting the time-ago- to work//

                String DateTime = model.getDateTime();

              //  SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd SS HH:mm:ss");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                try {
                    long time = sdf.parse(DateTime).getTime(); //time of uploading the post
                    long now = System.currentTimeMillis();  //time of viewing the post
                    CharSequence ago =
                            DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
                    Log.d("myTime", "onBindViewHolder: "+ago);
                    holder.timeAgo.setText(""+ago);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @NonNull
            @Override
            public AllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.featured_card_design, parent, false);
                return new AllViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    //clubs recyclerview activities
    private void cRecyclerView() {
        clubRecyclerView.setHasFixedSize(true);
        clubRecyclerView.setLayoutManager(new LinearLayoutManager
                (this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<ClubHelper> clubs = new ArrayList<>();
        clubs.add(new ClubHelper(R.drawable.musicclub, "Music Club", "Description"));
        clubs.add(new ClubHelper(R.drawable.communityclub, "Community Club", "Description"));
        clubs.add(new ClubHelper(R.drawable.sportsclub, "Sports Club", "Description"));
        clubs.add(new ClubHelper(R.drawable.photoclub, "Photo Club", "Description"));
        clubs.add(new ClubHelper(R.drawable.danceclub, "Dance Club", "Description"));

        clubAdapter = new ClubsAdapter(clubs, this);
        clubRecyclerView.setAdapter(clubAdapter);
    }
    //clubs recyclerview activities//

    private void aRecyclerView() {

        attractionsRecyclerView.setHasFixedSize(true);
        attractionsRecyclerView.setLayoutManager(new LinearLayoutManager
                (this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<AttractionsHelper> attractions = new ArrayList<>();
        attractions.add(new AttractionsHelper(R.drawable.uss, "Universal Studio", "Description"));
        attractions.add(new AttractionsHelper(R.drawable.sea, "SEA Aquarium", "Description"));
        attractions.add(new AttractionsHelper(R.drawable.zoo, "Singapore Zoo", "Description"));
        attractions.add(new AttractionsHelper(R.drawable.gardens, "Gardens by the Bay", "Description"));
        attractions.add(new AttractionsHelper(R.drawable.sentosa, "Sentosa Island", "Description"));

        attractionsAdapterAdapter = new AttractionsAdapter(attractions);
        attractionsRecyclerView.setAdapter(attractionsAdapterAdapter);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {

        switch (menuitem.getItemId()){
            case R.id.nav_home:
                break;

                case R.id.nav_all_notice:
                Intent intent = new Intent(DashboardHome.this, AllJobActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_login:
                Intent intentLogin = new Intent(DashboardHome.this, MainActivity.class);
                startActivity(intentLogin);
                break;

            case R.id.nav_profile:
                String url = "https://www.psb-academy.edu.sg/connect-with-us/campuses/visit-us#2";
                Intent intentWeb = new Intent (Intent.ACTION_VIEW);
                intentWeb.setData(Uri.parse(url));
                startActivity(intentWeb);
                break;

            case R.id.nav_logout:
                mAuth.signOut();
                break;

            case R.id.nav_share:
                Toast.makeText(this, "Student Hub is still Work in Progress!!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_email:
                Intent intentEmail = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","studenthub_beta@gmail.com", null));
                intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                intentEmail.putExtra(Intent.EXTRA_TEXT, "Message");
                startActivity(Intent.createChooser(intentEmail, "Choose an Email client :"));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
