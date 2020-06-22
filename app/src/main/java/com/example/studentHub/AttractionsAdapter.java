package com.example.studentHub;

import android.content.ActivityNotFoundException;
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

import com.example.studentHub.Model_helper_classes.AttractionsHelper;

import java.util.ArrayList;

class AttractionsAdapter extends RecyclerView.Adapter <AttractionsAdapter.AttractionsViewHolder> {

    ArrayList<AttractionsHelper> attractions;

    public AttractionsAdapter(ArrayList<AttractionsHelper> attractions) {
        this.attractions = attractions;
    }

    @NonNull
    @Override
    public AttractionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attractions_card_design, parent, false);
        AttractionsViewHolder attractionsViewHolder = new AttractionsViewHolder(view);
        return attractionsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AttractionsViewHolder holder, int position) {
        AttractionsHelper attractionsHelper = attractions.get(position);
        holder.attractionsImage.setImageResource(attractionsHelper.getAttractionsImage());
        holder.attractionName.setText(attractionsHelper.getAttractionsName());
        holder.attractionDescription.setText(attractionsHelper.getAttractionsDescription());

    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    //Viewholder holds view
    public static class AttractionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView attractionsImage;
        TextView attractionName, attractionDescription;

        public AttractionsViewHolder(@NonNull View itemView) {
            super(itemView);

            attractionsImage = itemView.findViewById(R.id.attractions_image);
            attractionName = itemView.findViewById(R.id.attractions_title);
            attractionDescription = itemView.findViewById(R.id.attractions_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("attractionsposition", String.valueOf(getAdapterPosition()));

            switch (getAdapterPosition()) {
                case 0:

                    Uri uri = Uri.parse("https://www.facebook.com/ResortsWorldatSentosa");
                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                    likeIng.setPackage("com.facebook.katana");

                    try {
                        view.getContext().startActivity(likeIng);
                    } catch (ActivityNotFoundException e) {
                        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.facebook.com/ResortsWorldatSentosa")));
                    }
                    break;


                case 1:
                    Uri uri1 = Uri.parse("https://www.facebook.com/ResortsWorldatSentosa/posts/sea-aquarium-marine-life-park-has-the-worlds-largest-acrylic-panel-at-the-open-o/453860704692429/");
                    Intent likeIng1 = new Intent(Intent.ACTION_VIEW, uri1);

                    likeIng1.setPackage("com.facebook.katana");

                    try {
                        view.getContext().startActivity(likeIng1);
                    } catch (ActivityNotFoundException e) {
                        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.facebook.com/ResortsWorldatSentosa/posts/sea-aquarium-marine-life-park-has-the-worlds-largest-acrylic-panel-at-the-open-o/453860704692429/")));
                    }
                    break;

                case 2:
                    Uri uri2 = Uri.parse("https://www.facebook.com/wrs.sg/");
                    Intent likeIng2 = new Intent(Intent.ACTION_VIEW, uri2);

                    likeIng2.setPackage("com.facebook.katana");

                    try {
                        view.getContext().startActivity(likeIng2);
                    } catch (ActivityNotFoundException e) {
                        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.facebook.com/wrs.sg/")));
                    }
                    break;

                case 3:
                    Uri uri3 = Uri.parse("https://www.facebook.com/gardensbythebay/");
                    Intent likeIng3 = new Intent(Intent.ACTION_VIEW, uri3);

                    likeIng3.setPackage("com.facebook.katana");

                    try {
                        view.getContext().startActivity(likeIng3);
                    } catch (ActivityNotFoundException e) {
                        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.facebook.com/gardensbythebay/")));
                    }
                    break;

                case 4:
                    Uri uri4 = Uri.parse("https://www.facebook.com/sentosaofficial/");
                    Intent likeIng4 = new Intent(Intent.ACTION_VIEW, uri4);

                    likeIng4.setPackage("com.facebook.katana");

                    try {
                        view.getContext().startActivity(likeIng4);
                    } catch (ActivityNotFoundException e) {
                        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.facebook.com/sentosaofficial/")));
                    }
            }
        }
    }
}
