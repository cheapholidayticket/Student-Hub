package com.example.studentHub.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentHub.Admin.MainActivity;
import com.example.studentHub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class ForgetPasswordActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button resetPasswordButton;
    EditText resetEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mAuth = FirebaseAuth.getInstance();
        resetEmail = findViewById(R.id.resetEmailID);
        resetPasswordButton = findViewById(R.id.sendMessage);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.fetchSignInMethodsForEmail(resetEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.getResult().getSignInMethods().isEmpty()) {
                            Toast.makeText(ForgetPasswordActivity.this, "Kindly input email to reset password", Toast.LENGTH_SHORT).show();
                        } else {
                            mAuth.sendPasswordResetEmail(resetEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){

                                    Toast.makeText(ForgetPasswordActivity.this, "Kindly check email to reset password", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ForgetPasswordActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    } else  {
                                        Log.d("error resetPassword", task.getException().getMessage());
                                            }
                               }
                            });
                        }
                    }
                });
            }
        });
    }
}