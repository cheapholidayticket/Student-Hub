package com.example.studentHub;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentHub.Login.StartUp;
import com.example.studentHub.Model_helper_classes.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PostNoticeActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton_postjob;
    private Toolbar toolbar;
    //recycler view
    private RecyclerView recyclerView;

    //firebase
    private FirebaseRecyclerOptions<Data> options; //add
    private FirebaseRecyclerAdapter<Data, MyViewHolder> adapter; //add
    private FirebaseAuth mAuth;
    private DatabaseReference JobPostDatabase; //ref

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);   //show back button

        floatingActionButton_postjob = findViewById(R.id.floatingActionButton);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        String uId = mUser.getUid();
        JobPostDatabase = FirebaseDatabase.getInstance().getReference()
                .child("Job Post").child(uId);

        recyclerView = findViewById(R.id.recycler_job_post_id);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        floatingActionButton_postjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), InsertPostActivity.class));

            }
        });


        options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(JobPostDatabase, Data.class).build();
        adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position
                    , @NonNull Data model) {
                holder.mTitle.setText("" + model.getTitle());
                holder.mDate.setText("" + model.getDate());
                holder.mDescription.setText("" + model.getDescription());
                Picasso.get().load(model.getImageUrl()).into(holder.imageView);

                holder.myViewHolderV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), EditNotice.class);
                        intent.putExtra("postjobkey", getRef(position).getKey());
                        startActivity(intent);

                    }
                });


                String DateTime = model.getDateTime();

                //  SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd SS HH:mm:ss");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
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
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.alljobpost, parent, false);
                return new MyViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.postnotice_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.postnotice_home:
                startActivity(new Intent (getApplicationContext(), HomeActivity.class));
                break;

            case R.id.postnotice_dashboard:
                startActivity(new Intent(getApplicationContext(), DashboardHome.class));
                break;

            case R.id.postnotice_logout:
                mAuth.signOut();
                startActivity(new Intent (getApplicationContext(), StartUp.class));
                break;

            case R.id.postnotice_viewAll:
                startActivity(new Intent(getApplicationContext(), AllPostActivity.class));

            case android.R.id.home:
                onBackPressed();
                return true;

        }



        return super.onOptionsItemSelected(item);
    }
}


//todo logout button
/*
* btnLogout.setonClickListener
* mAuth.signout();
* Intent intent = new Intent(this, HomeActivity.class);
* intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |INTENT.FLAG_ACTIVITY_NEW_TASK);
* startActivity(intent);
* */
