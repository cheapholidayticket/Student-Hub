package com.example.studentHub;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentHub.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ViewNotice extends AppCompatActivity {

    ImageView imageView;
    TextView textViewDate;
    TextView textViewTitle;
    TextView textViewDetails;
//    Button buttonDelete;
//    Button buttonEdit;
    DatabaseReference mAllJobPost;
    DatabaseReference mJobPost;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notice);

        imageView = findViewById(R.id.activity_view_image);
        textViewDate = findViewById(R.id.activity_view_date);
        textViewTitle = findViewById(R.id.activity_view_title);
        textViewDetails = findViewById(R.id.activity_view_details);
//        buttonDelete = findViewById(R.id.activity_view_delete);
//        buttonEdit = findViewById(R.id.activity_view_edit);

        String key = getIntent().getStringExtra("key");

        mAllJobPost = FirebaseDatabase.getInstance().getReference().child("Public Database").child(key);
        mJobPost = FirebaseDatabase.getInstance().getReference().child("Job Post").child(key);
        storageRef = FirebaseStorage.getInstance().getReference().child("Image").child(key + ".jpg");;

        mAllJobPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String title = dataSnapshot.child("title").getValue().toString();
                    String date = dataSnapshot.child("date").getValue().toString();
                    String description = dataSnapshot.child("description").getValue().toString();
                    String imageUrl  = dataSnapshot.child("imageUrl").getValue().toString();

                    textViewTitle.setText(title);
                    textViewDate.setText(date);
                    textViewDetails.setText(description);
                    Picasso.get().load(imageUrl).into(imageView);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //, DOES NOT go back dashboardhome? //mjobpost not remove value

//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mAllJobPost.removeValue().addOnSuccessListener( new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                            public void onSuccess(Void aVoid) {
//                                mJobPost.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                 public void onSuccess(Void aVoid) {
//                                        Toast.makeText(ViewNotice.this, "Call method", Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(getApplicationContext(), DashboardHome.class));                            }
//                        });
//
//                    }
//                });
//
//            }
//
//        });
//
//            }
//
//        });


    }
}
