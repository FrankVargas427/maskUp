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
import com.henktech.maskup.tools.ProbCalc;

import java.util.ArrayList;

public class FindMaskActivity extends AppCompatActivity {

    ListView placesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_mask);
        placesList = findViewById(R.id.placesList);
        ArrayList<Place> placesProbability = (ArrayList<Place>)
                SaveLoadController.loadFile(this.getApplicationContext(), getString(R.string.placesSavefile));

        placesProbability = ProbCalc.calculatePlaces(placesProbability);

        placesList.setAdapter(new PlacesAdapter(this,
                android.R.layout.simple_list_item_multiple_choice, placesProbability, 1));
        placesList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        placesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Metodo que saque el objeto seleccionado, le sume valor a las veces encontrado
                //y lo guarde.

                Context context = getApplicationContext();
                CharSequence text = "Cubrebocas encontrado!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent mainIntent = new Intent(FindMaskActivity.this, HomeActivity.class);
                        FindMaskActivity.this.startActivity(mainIntent);
                        FindMaskActivity.this.finish();
                    }
                }, 100);
            }
        });
    }
}