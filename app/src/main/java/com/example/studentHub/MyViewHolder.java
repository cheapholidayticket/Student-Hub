package com.example.studentHub;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentHub.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView mTitle, mDate, mDescription, timeAgo;
    View myViewHolderV;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageViewAdd_alljobpost);
        mTitle = itemView.findViewById(R.id.notice_title_alljobpost);
        mDate = itemView.findViewById(R.id.notice_date_alljobpost);
        mDescription = itemView.findViewById(R.id.notice_description_alljobpost);
        timeAgo = itemView.findViewById(R.id.timeAgo_alljobpost);
        myViewHolderV = itemView;

        //mCampus = itemView.findViewById(R.id.notice_campus);
    }
}
