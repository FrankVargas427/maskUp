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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.henktech.maskup.R;
import com.henktech.maskup.pojos.Place;

public class PlacesDialog extends AppCompatDialogFragment {
    Place place;
    int position;
    boolean madeOrFound;
    boolean buttonOrText;
    private EditText placeName;
    private RatingBar ratingBar;
    private DialogListener listener;

    public PlacesDialog(Place place, int position, boolean madeOrFound, boolean buttonOrText) {
        this.place = place;
        this.position = position;
        this.madeOrFound = madeOrFound;
        this.buttonOrText = buttonOrText;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Context context = getContext();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.places_dialog, null);

        // Se consiguen los datos del lugar y se insertan en el EditText

        placeName = view.findViewById(R.id.placeName);
        placeName.setText(place.getName());
        placeName.setEnabled(true);
        placeName.setActivated(true);

        /*
        El boolean madeOrFound indica si el dialogo se ejecuto en el menu de Editar Lugares (made)
        o en el menu de Encontrar cubrebocas (found).

        Si se ejecuto en made, la barra de estrellas se activa. De lo contrario, se desactiva.
         */

        ratingBar = view.findViewById(R.id.placeRatingBar);
        if (madeOrFound) {
            ratingBar.setRating(place.getProbability());
        } else {
            ratingBar.setVisibility(View.GONE);
            ratingBar.setEnabled(false);
            ratingBar.setActivated(false);
        }

        builder.setView(view).setTitle(getString(R.string.place))
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                /*
                 En el caso de que se presione el boton de OK y el nombre no este vacio,
                 al lugar se le inserta el nombre en el EditText y dos cosas pueden pasar:
                    -Si el dialogo se ejecuto en made, al lugar se le inserta la probabilidad
                    especificada en la barra de estrellas.
                    -Si el dialogo se ejecuto en found, al lugar se le inserta un 1 como su
                    probabilidad.
                 Despues de esto, se aplican los cambios.

                 Si se presiona el boton OK y el nombre esta vacio, te avisa sobre este error
                 y no hace nada mas.
                 */

                if (!placeName.getText().toString().equals("")) {
                    place.setName(placeName.getText().toString());
                    if (madeOrFound) {
                        place.setProbability(ratingBar.getRating());
                    } else {
                        place.setProbability(1);
                    }
                    listener.applyChanges(place, position, buttonOrText);
                } else {
                    Toast toast = Toast.makeText(context, getString(R.string.emptyName), Toast.LENGTH_SHORT);
                    toast.show();
                }
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
        void applyChanges(Place place, int position, boolean buttonOrText);
    }
}
