package com.example.studentHub;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.studentHub.Login.StartUp;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private Button buttonViewAllJob;
    private Button buttonPostJob;
    private Button buttonPushNotification;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);   //show back button

        mAuth = FirebaseAuth.getInstance();

        buttonViewAllJob = findViewById(R.id.button_allJob);
        buttonPostJob = findViewById(R.id.button_postJob);
        buttonPushNotification = findViewById(R.id.button_allpushNotificationWebView);

        buttonViewAllJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DashboardHome.class));
            }
        });

        buttonPostJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), PostNoticeActivity.class));

            }
        });

        buttonPushNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (getApplicationContext(), MessageWebView.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent (getApplicationContext(), StartUp.class));
                finish();
                break;

            case android.R.id.home:
                onBackPressed();
                return true;


            case R.id.home_viewAll:
                startActivity(new Intent (getApplicationContext(), AllPostActivity.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
