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
    int madeOrFound = 0;

    /*
    Un adapter es una herramienta para crear una lista en base los pojos.
    Este es el adaptador de los lugares.
     */

    public PlacesAdapter(Context context, @LayoutRes int resource, ArrayList<Place> places, int madeOrFound) {
        super(context, resource, places);
        this.madeOrFound = madeOrFound;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // De la lista se consigue el lugar actual.
        Place place = getItem(position);

        /*
        El boolean madeOrFound indica si el adaptador se ejecuto en el menu de Editar Lugares (made)
        o en el menu de Encontrar cubrebocas (found).

        En ambos casos se insertan los datos del lugar indicado. Pero si el adaptador se ejecuto en
        found, en lugar de un TextView y una barra de estrellas, es dos TextViews.
         */
        if (madeOrFound == 0) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_place, parent, false);
            }
            // Se buscan el TextView y la barra de estrellas.
            TextView tvName = convertView.findViewById(R.id.tvName);
            RatingBar rbProbability = convertView.findViewById(R.id.rbFrequency);

            // Se le insertan los datos del lugar.
            tvName.setText(place.getName());
            rbProbability.setRating(place.getProbability());
        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_prob, parent, false);
            }

            TextView tvRoomName = convertView.findViewById(R.id.tvRoomName);
            TextView tvProbability = convertView.findViewById(R.id.tvProbability);

            // Se redondea la probabilidad para sacar un numero con 2 decimales.
            double probRound = Math.round(place.getProbability() * 100.00) / 100.00;

            tvRoomName.setText(place.getName());
            tvProbability.setText(probRound + "%");

            /*
            En caso de que el valor en la lista sea el primero, se le pone un fondo verde.
            Si es el segundo, se le pone un fondo naranja.
            Si no es ninguno, se le pone un fondo rojo.
             */
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

        return convertView;
    }
}
