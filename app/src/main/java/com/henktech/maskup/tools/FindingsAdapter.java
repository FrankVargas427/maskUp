package com.henktech.maskup.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.henktech.maskup.R;
import com.henktech.maskup.pojos.Finding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FindingsAdapter extends ArrayAdapter<Finding> {
    /*
    Un adapter es una herramienta para crear una lista en base los pojos.
    Este es el adaptador de los lugares.
     */
    public FindingsAdapter(Context context, ArrayList<Finding> findings) {
        super(context, 0, findings);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // De la lista se consigue el finding actual.
        Finding find = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_finding, parent, false);
        }

        // Se buscan los TextViews.
        TextView findingPlace = convertView.findViewById(R.id.findingPlace);
        TextView findingDay = convertView.findViewById(R.id.findingDay);

        // Se saca la fecha del Finding y se le da formato.
        Calendar cal = find.getTimeFound();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd hh:mm aa");
        String formatted = format1.format(cal.getTime());


        // Se le insertan los datos del Finding.
        findingPlace.setText(find.getPlaceFound());
        findingDay.setText(formatted);

        return convertView;
    }
}
