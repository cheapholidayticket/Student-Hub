package com.example.studentHub.Login;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentHub.Admin.MainActivity;
import com.example.studentHub.DashboardHome;
import com.example.studentHub.R;

public class StartUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void mainScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        Pair[] pairs = new Pair[1]; // 1 element animation
        pairs[0] = new Pair<View, String>
                (findViewById(R.id.login_button_startup), "transition_login");  //at first position index, call button view
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartUp.this, pairs);
        startActivity(intent, options.toBundle());
    }

    public void viewNotice(View view) {
        Intent intent = new Intent(getApplicationContext(), DashboardHome.class);
        startActivity(intent);
    }

    public void signUp(View view) {
        Intent intent = new Intent(getApplicationContext(), Registration.class);
        startActivity(intent);
    }
}
