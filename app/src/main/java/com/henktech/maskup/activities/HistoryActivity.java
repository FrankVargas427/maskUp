package com.henktech.maskup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.henktech.maskup.R;
import com.henktech.maskup.controllers.SaveLoadController;
import com.henktech.maskup.pojos.Finding;
import com.henktech.maskup.tools.FindingsAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    ListView repetitionList;
    ListView historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().hide();

        repetitionList = findViewById(R.id.repetitionList);
        historyList = findViewById(R.id.historyList);

        ArrayList<Finding> findingsArray = (ArrayList<Finding>)
                SaveLoadController.loadFile(this, getString(R.string.findingsSavefile));

        ArrayList<String> statistics = new ArrayList<>();

        if (findingsArray.isEmpty()) {
            String emptyStuff = getString(R.string.emptyData);
            statistics.add(emptyStuff);
        } else {
            HashMap<String, Integer> statisticsMap = calculateFindings(findingsArray);

            for (Map.Entry<String, Integer> e : statisticsMap.entrySet()) {
                if (e.getValue() > 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(e.getKey());
                    sb.append(": ");
                    sb.append(e.getValue());
                    sb.append(" veces");
                    statistics.add(sb.toString());
                }
            }
        }

        ArrayAdapter<String> repetitionAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statistics);
        repetitionList.setAdapter(repetitionAdapter);

        FindingsAdapter findingsAdapter =
                new FindingsAdapter(this, findingsArray);
        historyList.setAdapter(findingsAdapter);
    }

    public HashMap<String, Integer> calculateFindings(ArrayList<Finding> findingsArray) {
        HashMap<String, Integer> repetitions = new HashMap<>();

        for (int i = 0; i < findingsArray.size(); ++i) {
            Finding placeX = findingsArray.get(i);

            if (repetitions.containsKey(placeX.getPlaceFound())) {
                repetitions.put(placeX.getPlaceFound(), repetitions.get(placeX.getPlaceFound()) + 1);
            } else {
                repetitions.put(placeX.getPlaceFound(), 1);
            }
        }

        return repetitions;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(HistoryActivity.this, HomeActivity.class);
                HistoryActivity.this.startActivity(mainIntent);
                HistoryActivity.this.finish();
            }
        }, 100);
    }
}