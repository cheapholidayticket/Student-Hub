package com.example.jobportalapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class AllViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView mTitle, mDate, mDescription, timeAgo;
    View v;

    public AllViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageViewAdd_alljobpost);
        mTitle = itemView.findViewById(R.id.notice_title_alljobpost);
        mDate = itemView.findViewById(R.id.notice_date_alljobpost);
        mDescription = itemView.findViewById(R.id.notice_description_alljobpost);
        timeAgo = itemView.findViewById(R.id.timeAgo_alljobpost);
        v = itemView;
        //mCampus = itemView.findViewById(R.id.notice_campus);

    }
}
