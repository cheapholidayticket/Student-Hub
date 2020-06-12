package com.example.jobportalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class InsertJobPostActivity extends AppCompatActivity {

    EditText job_title;
    private EditText job_description;
    private Button btn_job_post;
    //private EditText job_skills;
    private EditText job_salary;

    //progressbar
    private TextView textViewProgress;
    private ProgressBar progressBar;

    //DatabaseReference DataRef;
    DatabaseReference mJobPost;
    DatabaseReference mPublicDatabase;
    StorageReference StorageRef;
    FirebaseAuth mAuth;

    //image
    private static final int REQUEST_CODE_IMAGE = 101;
    private ImageView imageViewAdd;
    Uri imageUri;
    boolean isImageAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_job_post);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        String uId = mUser.getUid();

        imageViewAdd = findViewById(R.id.imageViewAdd);
        job_title = findViewById(R.id.title);
        job_description = findViewById(R.id.description);
        btn_job_post = findViewById(R.id.job_post_btn);
        textViewProgress = findViewById(R.id.textViewProgress);
        progressBar = findViewById(R.id.progressBar);


        //progressbar
        textViewProgress.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        mJobPost = FirebaseDatabase.getInstance().getReference().child("Job Post").child(uId);
        mPublicDatabase = FirebaseDatabase.getInstance().getReference().child("Public Database");
        StorageRef = FirebaseStorage.getInstance().getReference().child("Image");

        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });

        btn_job_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = job_title.getText().toString().trim().toLowerCase();
                String description = job_description.getText().toString().trim().toLowerCase();
                //String skills = job_skills.getText().toString().trim().toLowerCase(); //not used
                //String salary = job_salary.getText().toString().trim().toLowerCase();

                if (isImageAdded && title != null && description != null) {
                    uploadImage(title, description);
                }
            }

            private void uploadImage(final String title, final String description) {

                textViewProgress.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                final String id = mJobPost.push().getKey();
                final String date = DateFormat.getDateInstance().format(new Date());  //to get date
                StorageRef.child(id + ".jpg").putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                StorageRef.child(id + ".jpg").getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                HashMap hashMap = new HashMap();
                                                hashMap.put("date", date); //date posted notice
                                                hashMap.put("description", description);
                                                hashMap.put("id", id);
                                                hashMap.put("imageUrl", uri.toString());
                                                //hashMap.put("salary", salary ); //campus
                                                hashMap.put("title", title);

                                                //time in miliseconds

//
//                                                Date date = new Date();
//                                                SimpleDateFormat formatter =  new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss");
//                                                String DateTime = formatter.format(date);
//

                                                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                                                hashMap.put("DateTime",f.format(new Date()));

                                                mPublicDatabase.child(id).setValue(hashMap);
                                                mJobPost.child(id).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(InsertJobPostActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        });

                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (taskSnapshot.getBytesTransferred() * 100) / taskSnapshot.getTotalByteCount();
                        progressBar.setProgress((int) progress);
                        textViewProgress.setText(progress + "%");

                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        {
            imageUri = data.getData();
            isImageAdded = true;
            imageViewAdd.setImageURI(imageUri);
        }
    }
}

