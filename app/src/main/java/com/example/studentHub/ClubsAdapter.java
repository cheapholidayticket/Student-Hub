package com.example.studentHub;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentHub.Model_helper_classes.ClubHelper;
import com.example.studentHub.R;

import java.util.ArrayList;

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ClubsViewHolder> {


    //adapter bridge design to data
    //data for clubs is in ArrayList
    ArrayList<ClubHelper> clubs;
    Context context;


    //pass list data into constructor
    public ClubsAdapter(ArrayList<ClubHelper> clubs, Context context) {
        this.clubs = clubs;
        this.context = context;
    }

    @NonNull
    @Override
    public ClubsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clubs_card_design, parent, false);
        ClubsViewHolder clubsViewHolder = new ClubsViewHolder(view);
        return clubsViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ClubsViewHolder holder, int position) {
        ClubHelper clubHelper = clubs.get(position);
        holder.image.setImageResource(clubHelper.getImage());
        holder.clubName.setText(clubHelper.getClubName());
        holder.clubDescription.setText(clubHelper.getClubDescription());

    }
    @Override
    public int getItemCount() {
        return clubs.size();
    }



    //holds the view & design of recycler
    public class ClubsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView clubName, clubDescription;

        public ClubsViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.club_image);
            clubName = itemView.findViewById(R.id.club_name);
            clubDescription = itemView.findViewById(R.id.club_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            //Toast.makeText(context, String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            Log.d("clubsposition", String.valueOf(getAdapterPosition()));

            switch (getAdapterPosition()) {
                case 0:
                    Uri uri = Uri.parse("https://www.instagram.com/psbmusicclub/?hl=en");
                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                    likeIng.setPackage("com.instagram.android");

                    try {
                        view.getContext().startActivity(likeIng);
                    } catch (ActivityNotFoundException e) {
                        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.instagram.com/psbmusicclub/?hl=en")));
                    }

                    break;

                case 1:
                    Uri uri1 = Uri.parse("https://www.instagram.com/psbacademycsc/?hl=en");
                    Intent likeIng1 = new Intent(Intent.ACTION_VIEW, uri1);

                    likeIng1.setPackage("com.instagram.android");

                    try {
                        view.getContext().startActivity(likeIng1);
                    } catch (ActivityNotFoundException e) {
                        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.instagram.com/psbacademycsc/?hl=en")));
                    }

                    break;

                case 2:
                    Uri uri2 = Uri.parse("https://www.instagram.com/psbasportsnadv/?hl=en");
                    Intent likeIng2 = new Intent(Intent.ACTION_VIEW, uri2);

                    likeIng2.setPackage("com.instagram.android");

                    try {
                        view.getContext().startActivity(likeIng2);
                    } catch (ActivityNotFoundException e) {
                        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.instagram.com/psbasportsnadv/?hl=en")));
                    }

                    break;

                case 3:
                Uri uri3 = Uri.parse("https://www.instagram.com/psbphotoclub/?hl=en");
                Intent likeIng3 = new Intent(Intent.ACTION_VIEW, uri3);

                likeIng3.setPackage("com.instagram.android");

                try {
                    view.getContext().startActivity(likeIng3);
                } catch (ActivityNotFoundException e) {
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/psbphotoclub/?hl=en")));
                }

                case 4:
                    Uri uri4 = Uri.parse("https://www.instagram.com/psbdanceclub/?hl=en");
                    Intent likeIng4 = new Intent(Intent.ACTION_VIEW, uri4);

                    likeIng4.setPackage("com.instagram.android");

                    try {
                        view.getContext().startActivity(likeIng4);
                    } catch (ActivityNotFoundException e) {
                        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.instagram.com/psbdanceclub/?hl=en")));
                    }

            }
        }

        }
    }

