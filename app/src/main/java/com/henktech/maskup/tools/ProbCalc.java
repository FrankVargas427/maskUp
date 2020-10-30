package com.henktech.maskup.tools;

import com.henktech.maskup.pojos.Place;

import java.util.ArrayList;

public class ProbCalc {
    public static ArrayList<Place> calculatePlaces(ArrayList<Place> inPlaces) {
        ArrayList<Place> outPlaces = new ArrayList<>();
        float maxVal = 0;

        for (Place checkPlace : inPlaces) {
            maxVal = maxVal + checkPlace.getProbability();
        }

        for (Place checkPlace : outPlaces) {
            float currentProb = (checkPlace.getProbability() * 100) / maxVal;
            checkPlace.setProbability(currentProb);
        }

        return outPlaces;
    }

}
