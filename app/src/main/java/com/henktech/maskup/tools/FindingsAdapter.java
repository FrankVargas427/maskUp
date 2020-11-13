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
    public FindingsAdapter(Context context, ArrayList<Finding> findings) {
        super(context, 0, findings);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Finding find = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_finding, parent, false);
        }

        TextView findingPlace = convertView.findViewById(R.id.findingPlace);
        TextView findingDay = convertView.findViewById(R.id.findingDay);

        Calendar cal = find.getTimeFound();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd hh:mm aa");
        String formatted = format1.format(cal.getTime());
        findingPlace.setText(find.getPlaceFound());
        findingDay.setText(formatted);

        return convertView;
    }
}
