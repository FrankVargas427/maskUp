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
import com.henktech.maskup.pojos.Finding;
import com.henktech.maskup.pojos.Place;
import com.henktech.maskup.tools.PlacesAdapter;
import com.henktech.maskup.tools.PlacesDialog;
import com.henktech.maskup.tools.ProbCalc;

import java.util.ArrayList;
import java.util.Calendar;

public class FindMaskActivity extends AppCompatActivity implements PlacesDialog.DialogListener {

    ListView placesList;
    ArrayList<Place> placesProbabilityNumbers;
    ArrayList<Place> placesProbabilityNormal;
    Context thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        thisContext = this.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_mask);
        getSupportActionBar().hide();

        placesList = findViewById(R.id.placesList);

        /*
        Se crean dos listas identicas de todos los lugares y sus probabilidades.
        Una de estas listas tendra todas las probabilidades como son (numbers) y la otra
        tendra todas las probabilidades normalizadas (normal).
         */
        placesProbabilityNumbers = (ArrayList<Place>)
                SaveLoadController.loadFile(this.getApplicationContext(), getString(R.string.placesSavefile));
        placesProbabilityNormal = (ArrayList<Place>)
                SaveLoadController.loadFile(this.getApplicationContext(), getString(R.string.placesSavefile));

        // La lista normalizada se le calculan y aplican las normalizaciones.
        placesProbabilityNormal = ProbCalc.calculatePlaces(placesProbabilityNormal);

        // Se le aplica el adaptador a la lista de lugares en el presentador.
        placesList.setAdapter(new PlacesAdapter(this,
                android.R.layout.simple_list_item_multiple_choice, placesProbabilityNormal, 1));
        placesList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        placesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                CharSequence text = getString(R.string.foundFacemask);
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                /*
                Cuando se seleccione el lugar donde el cubrebocas fue encontrado, se consigue
                el lugar de la lista de lugares.
                 */
                Place foundPlace = placesProbabilityNormal.get(position);

                /*
                A la lista de lugares con los valores no normalizados se le busca
                el lugar seleccionado y se le suma un 1.
                 */
                for (Place checkPlace : placesProbabilityNumbers) {
                    if (checkPlace.getName().equals(foundPlace.getName())) {
                        float oldProb = checkPlace.getProbability();
                        checkPlace.setProbability(oldProb + 1);
                    }
                }
                saveAndFinish(foundPlace);
            }
        });
    }

    public void newPlace(View v) {
        /*
        Si el cubrebocas se encontro en un lugar nuevo, se manda a llamar el dialogo de
        creacion de nuevo lugar.
         */
        PlacesDialog placesDialog = new PlacesDialog(new Place(), placesProbabilityNormal.size(),
                false, true);
        placesDialog.show(getSupportFragmentManager(), getString(R.string.newPlace));
    }

    @Override
    public void applyChanges(Place place, int position, boolean buttonOrText) {
        /*
        Cuando se apliquen los datos del dialogo, se agrega el lugar nuevo en la lista
        no normalizada de lugares.
         */
        placesProbabilityNumbers.add(place);
        saveAndFinish(place);
    }

    public void saveAndFinish(Place place) {
        /*
         Se carga la lista de encuentros y se le crea un nuevo encuentro con
         el nombre del lugar y la fecha actual.
        */
        ArrayList<Finding> findingsArray = (ArrayList<Finding>)
                SaveLoadController.loadFile(thisContext, getString(R.string.findingsSavefile));
        findingsArray.add(new Finding(place.getName(), Calendar.getInstance()));

        /*
        Se guarda la lista de encuentros y la lista con los numeros no normalizados
        (la cual le sumamos 1 a el lugar donde se encontro el lugar).
         */
        SaveLoadController.saveFile(findingsArray, thisContext, getString(R.string.findingsSavefile));
        SaveLoadController.saveFile(placesProbabilityNumbers, thisContext, getString(R.string.placesSavefile));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(FindMaskActivity.this, HomeActivity.class);
                FindMaskActivity.this.startActivity(mainIntent);
                FindMaskActivity.this.finish();
            }
        }, 100);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(FindMaskActivity.this, HomeActivity.class);
                FindMaskActivity.this.startActivity(mainIntent);
                FindMaskActivity.this.finish();
            }
        }, 100);
    }
}