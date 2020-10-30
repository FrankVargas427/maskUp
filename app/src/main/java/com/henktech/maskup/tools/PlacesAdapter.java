package com.henktech.maskup.tools;

import android.content.Context;
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
    public PlacesAdapter(Context context, @LayoutRes int resource, ArrayList<Place> places) {
        super(context, resource, places);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Place place = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_place, parent, false);
        }
        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.tvName);
        RatingBar rbProbability = convertView.findViewById(R.id.rbProbability);
        // Populate the data into the template view using the data object
        tvName.setText(place.getName());
        rbProbability.setRating(place.getProbability());
        // Return the completed view to render on screen
        return convertView;
    }
}
