package com.henktech.maskup.tools;

import com.henktech.maskup.pojos.Place;

import java.util.ArrayList;

public class ProbCalc {

    //test

    public static ArrayList<Place> calculatePlaces(ArrayList<Place> inPlaces) {
        ArrayList<Place> outPlaces = new ArrayList<>();
        float maxVal = 0;

        for (Place checkPlace : inPlaces) {
            maxVal = maxVal + checkPlace.getProbability();
            if (checkPlace.getProbability() != 0.0) {
                outPlaces.add(checkPlace);
            }
        }

        for (Place checkPlace : outPlaces) {
            float currentProb = (checkPlace.getProbability() * 100) / maxVal;
            checkPlace.setProbability(currentProb);
        }

        return outPlaces;
    }

}
