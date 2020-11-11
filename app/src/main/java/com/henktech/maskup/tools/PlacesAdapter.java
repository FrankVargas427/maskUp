package com.henktech.maskup.tools;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import com.henktech.maskup.R;
import com.henktech.maskup.pojos.Place;

import java.util.ArrayList;

public class PlacesAdapter extends ArrayAdapter<Place> {
    int makeOrFind = 0;

    public PlacesAdapter(Context context, @LayoutRes int resource, ArrayList<Place> places, int makeOrFind) {
        super(context, resource, places);
        this.makeOrFind = makeOrFind;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Place place = getItem(position);
        if (makeOrFind == 0) {
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_place, parent, false);
            }
            // Lookup view for data population
            TextView tvName = convertView.findViewById(R.id.tvName);
            RatingBar rbProbability = convertView.findViewById(R.id.rbFrequency);
            // Populate the data into the template view using the data object
            tvName.setText(place.getName());
            rbProbability.setRating(place.getProbability());
        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_prob, parent, false);
            }

            TextView tvRoomName = convertView.findViewById(R.id.tvRoomName);
            TextView tvProbability = convertView.findViewById(R.id.tvProbability);
            double probRound = Math.round(place.getProbability() * 100.00) / 100.00;

            tvRoomName.setText(place.getName());
            tvProbability.setText(probRound + "%");

            switch (position) {
                case 0:
                    convertView.setBackgroundColor(Color.parseColor("#7FEE59"));
                    break;
                case 1:
                    convertView.setBackgroundColor(Color.parseColor("#EECA59"));
                    break;
                default:
                    convertView.setBackgroundColor(Color.parseColor("#EE5B59"));
            }
        }
        // Return the completed view to render on screen

        return convertView;
    }
}
