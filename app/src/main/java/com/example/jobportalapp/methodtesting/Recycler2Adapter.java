package com.example.jobportalapp.methodtesting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobportalapp.R;

public class Recycler2Adapter extends RecyclerView.Adapter<Recycler2Adapter.MyViewHolder> {

    public Recycler2Adapter(int[] arr) {
        this.arr = arr;
    }

    int []arr;


    @NonNull
    @Override
    public  MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clubs_card_design, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setImageResource(arr[position]);
        holder.textView1.setText("Image No." + position);
        holder.textView2.setText("Image No." + position);


    }

    @Override
    public int getItemCount() {
        return arr.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView;
        TextView textView1, textView2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.club_image);
            textView1 = itemView.findViewById(R.id.club_name);
            textView2 = itemView.findViewById(R.id.club_description);

        }
    }

}
