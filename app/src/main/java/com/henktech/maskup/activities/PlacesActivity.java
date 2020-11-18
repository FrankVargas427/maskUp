package com.henktech.maskup.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.henktech.maskup.R;
import com.henktech.maskup.controllers.SaveLoadController;
import com.henktech.maskup.pojos.Place;
import com.henktech.maskup.tools.PlacesAdapter;
import com.henktech.maskup.tools.PlacesDialog;

import java.util.ArrayList;

public class PlacesActivity extends AppCompatActivity implements PlacesDialog.DialogListener {
    final ArrayList<Place> housePlaces = new ArrayList<>();
    PlacesAdapter placeAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        getSupportActionBar().hide();

        initializePlaces(this);

        listView = findViewById(R.id.placesListView);
        placeAdapter = new PlacesAdapter(this,
                android.R.layout.simple_list_item_multiple_choice, housePlaces, 0);
        listView.setAdapter(placeAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlacesDialog placesDialog =
                        new PlacesDialog(housePlaces.get(position), position, false);
                placesDialog.show(getSupportFragmentManager(), "Frequency");
            }
        });
    }

    public void initializePlaces(Context context) {
        ArrayList<Place> loadHousePlaces = (ArrayList<Place>)
                SaveLoadController.loadFile(context, getString(R.string.placesSavefile));
        if (loadHousePlaces == null) {
            housePlaces.add(new Place("Living Room", 0));
            housePlaces.add(new Place("Bedroom", 0));
            housePlaces.add(new Place("Kitchen", 0));
            housePlaces.add(new Place("Dining Room", 0));
            housePlaces.add(new Place("Bathroom", 0));
            housePlaces.add(new Place("Studio", 0));
        } else {
            housePlaces.addAll(loadHousePlaces);
        }
    }

    public void newPlace(View v) {
        Place newPlace = new Place();
        housePlaces.add(newPlace);
        PlacesDialog placesDialog = new PlacesDialog(newPlace, housePlaces.indexOf(newPlace), false);
        placesDialog.show(getSupportFragmentManager(), "Frequency");
    }

    public void savePlaces(View v) {
        SaveLoadController.saveFile(housePlaces, this.getApplicationContext(), getString(R.string.placesSavefile));

        Toast toast = Toast.makeText(getApplicationContext(), "Places saved!", Toast.LENGTH_SHORT);
        toast.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(PlacesActivity.this, HomeActivity.class);
                PlacesActivity.this.startActivity(mainIntent);
                PlacesActivity.this.finish();
            }
        }, 100);
    }

    @Override
    public void applyChanges(Place place, int position) {
        housePlaces.set(position, place);
        placeAdapter.notifyDataSetChanged();
    }
}