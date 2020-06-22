package com.example.studentHub.Login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.studentHub.DashboardHome;
import com.example.studentHub.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registration extends AppCompatActivity {

    private static final int RC_SIGN_IN = 101;
    private EditText emailRegistration;
    private EditText passwordRegistration;

    TextInputLayout fullnameRegistration , username, phoneNumber;

    private Button buttonRegistration;
    private Button buttonGoogleLogin;
    GoogleSignInClient mGoogleSignInClient;
    Toolbar toolbar;

    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    DatabaseReference rootDatabaseref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);   //show back button

        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);



//        Log.d("fullnametest: ", fullnameRegistration.getEditText().toString());

        Registration(); //email and password registration of new user
       // SaveDatabaseUser();

    }

//    private void SaveDatabaseUser() {
//
//        rootDatabaseref = FirebaseDatabase.getInstance().getReference().child("userdata");
//
//        final String regName = fullnameRegistration.getEditText().getText().toString();
//        final String regUsername = username.getEditText().getText().toString();
//        final String regPhone = phoneNumber.getEditText().getText().toString().trim();
//
//
//        HashMap hashMap = new HashMap();
//        hashMap.put("Name", regName);
//        hashMap.put("UserName", regUsername);
//        hashMap.put("Phone", regPhone);
//
//        rootDatabaseref.child("users").setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Log.d("savedatauser", "savedatauser success");
//            }
//
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("savedatauser", "savedatauser failed");
//            }
//        });
//
//    }

    @SuppressLint("WrongViewCast")
    private void Registration() {

        emailRegistration = findViewById(R.id.registration_email);
        passwordRegistration = findViewById(R.id.registration_Password);
        buttonRegistration = findViewById(R.id.button_Registration);
        buttonGoogleLogin = findViewById(R.id.button_RegistrationGoogle);

        fullnameRegistration = findViewById(R.id.full_name_registration);
        username = findViewById(R.id.registration_username);
        phoneNumber = findViewById(R.id.registration_Phone);

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailRegistration.getText().toString().trim();
                String password = passwordRegistration.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailRegistration.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordRegistration.setError("Required Field");
                    return;
                }

                mDialog.setMessage("Processing");
                mDialog.show();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Successful",
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), DashboardHome.class));
                                    mDialog.dismiss();

                                } else {
                                    Toast.makeText(getApplicationContext(), "Registration failed",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                rootDatabaseref = FirebaseDatabase.getInstance().getReference().child("userdata");

                final String regName = fullnameRegistration.getEditText().getText().toString();
                final String regUsername = username.getEditText().getText().toString();
                final String regPhone = phoneNumber.getEditText().getText().toString().trim();


                HashMap hashMap = new HashMap();
                hashMap.put("Name", regName);
                hashMap.put("UserName", regUsername);
                hashMap.put("Phone", regPhone);

                rootDatabaseref.push().setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("savedatauser", "savedatauser success");
                        //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        mDialog.dismiss();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("savedatauser", "savedatauser failed");
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                onBackPressed();
                return true;

        }


        return super.onOptionsItemSelected(item);
    }
}
