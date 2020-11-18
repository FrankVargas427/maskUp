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
                        new PlacesDialog(housePlaces.get(position), position, true, false);
                placesDialog.show(getSupportFragmentManager(), getString(R.string.newPlace));
            }
        });
    }

    public void initializePlaces(Context context) {
        ArrayList<Place> loadHousePlaces = (ArrayList<Place>)
                SaveLoadController.loadFile(context, getString(R.string.placesSavefile));
        if (loadHousePlaces == null) {
            housePlaces.add(new Place("Sala de Estar", 0));
            housePlaces.add(new Place("Habitacion", 0));
            housePlaces.add(new Place("Cocina", 0));
            housePlaces.add(new Place("Comedor", 0));
            housePlaces.add(new Place("Baño", 0));
            housePlaces.add(new Place("Estudio", 0));
        } else {
            housePlaces.addAll(loadHousePlaces);
        }
    }

    public void newPlace(View v) {
        PlacesDialog placesDialog = new PlacesDialog(new Place(), housePlaces.size(), true, true);
        placesDialog.show(getSupportFragmentManager(), getString(R.string.newPlace));
    }

    public void savePlaces(View v) {
        SaveLoadController.saveFile(housePlaces, this.getApplicationContext(), getString(R.string.placesSavefile));

        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.placesSaved), Toast.LENGTH_SHORT);
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
    public void applyChanges(Place place, int position, boolean buttonOrText) {
        if (buttonOrText) {
            housePlaces.add(place);
        }
        placeAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(PlacesActivity.this, HomeActivity.class);
                PlacesActivity.this.startActivity(mainIntent);
                PlacesActivity.this.finish();
            }
        }, 100);
    }
}