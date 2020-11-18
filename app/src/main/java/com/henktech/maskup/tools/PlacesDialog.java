package com.henktech.maskup.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.henktech.maskup.R;
import com.henktech.maskup.pojos.Place;

public class PlacesDialog extends AppCompatDialogFragment {
    Place place;
    int position;
    boolean findOrMake;
    private EditText placeName;
    private RatingBar ratingBar;
    private DialogListener listener;

    public PlacesDialog(Place place, int position, boolean findOrMake) {
        this.place = place;
        this.position = position;
        this.findOrMake = findOrMake;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.places_dialog, null);

        placeName = view.findViewById(R.id.placeName);
        placeName.setText(place.getName());
        placeName.setEnabled(true);
        placeName.setActivated(true);
        if (findOrMake == false) {
            ratingBar = view.findViewById(R.id.placeRatingBar);
        }


        builder.setView(view).setTitle("Frequency")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                place.setName(placeName.getText().toString());
                if (findOrMake == false) {
                    place.setProbability(ratingBar.getRating());
                }
                listener.applyChanges(place, position);
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DialogListener");
        }
    }

    public interface DialogListener {
        void applyChanges(Place place, int position);
    }
}
