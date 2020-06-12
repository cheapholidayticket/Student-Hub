package com.example.jobportalapp.methodtesting;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobportalapp.R;

public class Recycler2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Recycler2Adapter recycler2Adapter;

    int []arr = {R.drawable.card2, R.drawable.card1, R.drawable.card3, R.drawable.card4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler2);
        recyclerView = findViewById(R.id.attractions_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager
                (this, LinearLayoutManager.HORIZONTAL, false));
        recycler2Adapter = new Recycler2Adapter(arr);
        recyclerView.setAdapter(recycler2Adapter);
        recyclerView.setHasFixedSize(true);

    }
}
