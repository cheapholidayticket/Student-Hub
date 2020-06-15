package com.example.studentHub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentHub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class EditNotice extends AppCompatActivity {

    TextView textViewDate;
    TextView textViewTitle;
    TextView textViewDetails;
    Button buttonDelete;
    Button buttonUpload;
    DatabaseReference mPublicDatabase,publicDatabase;
    DatabaseReference mJobPost, ref,jobPost;
    StorageReference StorageRef;
    FirebaseAuth mAuth;
    EditText editTitle;
    EditText editDetails;
    String imageUrl;

    //image
    final int REQUEST_CODE_IMAGE = 101;
    ImageView imageViewEdit;
    Uri selectedImageUri;
    boolean isImageAdded = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notice);


        //"postjobkey"
        String key = getIntent().getStringExtra("postjobkey");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uId = mUser.getUid();




        mPublicDatabase = FirebaseDatabase.getInstance().getReference().child("Public Database").child(key);
        mJobPost = FirebaseDatabase.getInstance().getReference().child("Job Post").child(uId).child(key);
        StorageRef = FirebaseStorage.getInstance().getReference().child("Image").child(key + ".jpg");
        imageViewEdit = findViewById(R.id.activity_edit_image);
        textViewDate = findViewById(R.id.activity_edit_date);
        textViewTitle = findViewById(R.id.editNotice_title);
        textViewDetails = findViewById(R.id.editNotice_description);
        buttonDelete = findViewById(R.id.activity_edit_btnDelete);
        buttonUpload = findViewById(R.id.activity_edit_btnUpload);
        editTitle = findViewById(R.id.editText_notice_Title);
        editDetails = findViewById(R.id.editText_notice_Description);
        ref = FirebaseDatabase.getInstance().getReference().child("Job Post");


        mJobPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String title = dataSnapshot.child("title").getValue().toString();
                    String date = dataSnapshot.child("date").getValue().toString();
                    String description = dataSnapshot.child("description").getValue().toString();
                    imageUrl = dataSnapshot.child("imageUrl").getValue().toString();


                    editTitle.setText(title);
                    textViewDate.setText(date);
                    editDetails.setText(description);
                    Picasso.get().load(imageUrl).into(imageViewEdit);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//
//        String id = mJobPost.push().getKey();
//        StorageRef.child(id + ".jpg").putFile(imageUri);
//        String title = editTitle.getText().toString().trim().toLowerCase();
//        String description = editDetails.getText().toString().trim().toLowerCase();
//        HashMap hashmap = new HashMap();
//        hashmap.put("title", title);
//        hashmap.put("description", description);
//        hashmap.put("id", id);

//        mPublicDatabase.child(id).updateChildren(hashmap);
//        mJobPost.child(id).updateChildren(hashmap).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(EditNotice.this, "Success", Toast.LENGTH_SHORT).show();
//            }
//        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJobPost.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        StorageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {

                            @Override
                            public void onSuccess(Void aVoid) {
                                mPublicDatabase.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) { //todo toast etc to inform
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

        imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //1. delete the image

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);

            }
        });


        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String title = editTitle.getText().toString().trim().toLowerCase();
                final String description = editDetails.getText().toString().trim().toLowerCase();

                StorageReference storageReference = FirebaseStorage
                        .getInstance().getReference().child(imageUrl);  //storagereference
                StorageReference mReff=FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);

                mReff.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        uploadImage(title,description);
                        Toast.makeText(EditNotice.this, "Image Deleted now you can update", Toast.LENGTH_SHORT).show();
                    }
                });


                if (isImageAdded && title != null && description != null) {
                    uploadImage(title, description);
                }
            }

            private void uploadImage(final String title, final String description) {

                final String id = mJobPost.push().getKey();
                StorageRef = FirebaseStorage.getInstance().getReference().child("Image");
                StorageRef.child(id + ".jpg").putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        StorageRef.child(id + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final HashMap hashMap = new HashMap();
                                hashMap.put("title", title);
                                hashMap.put("description", description);
                                hashMap.put("imageUrl", uri.toString());
                                mPublicDatabase.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        mJobPost.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                            @Override
                                            public void onComplete(@NonNull Task task) {
                                                if (task.isSuccessful())
                                                {
                                                    Toast.makeText(EditNotice.this, "Data Updated", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    Toast.makeText(EditNotice.this, "Not Updated!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    //notifydatasetchanged???

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        {
            selectedImageUri = data.getData();
            isImageAdded = true;
            imageViewEdit.setImageURI(selectedImageUri);
        }
    }
}




//
//                HashMap hashMap = new HashMap();
//                hashMap.put("title", title);
//
//                mJobPost.child("Job Post").updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
//                    @Override
//                    public void onSuccess(Object o) {
//                        Toast.makeText(EditNotice.this, "update", Toast.LENGTH_SHORT).show();
//
//                    }
//                });

