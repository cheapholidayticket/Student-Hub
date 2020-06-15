package com.example.studentHub.Admin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentHub.HomeActivity;
import com.example.studentHub.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {

    private static final int RC_SIGN_IN = 101;
    private EditText emailRegistration;
    private EditText passwordRegistration;

    private Button buttonRegistration;
    private Button buttonGoogleLogin;

    GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        Registration();

    }

    @SuppressLint("WrongViewCast")
    private void Registration() {

        emailRegistration = findViewById(R.id.registration_email);
        passwordRegistration = findViewById(R.id.registration_Password);
        buttonRegistration = findViewById(R.id.button_Registration);
        buttonGoogleLogin = findViewById(R.id.button_RegistrationGoogle);


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
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                    mDialog.dismiss();

                                } else {
                                    Toast.makeText(getApplicationContext(), "Registration failed",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
