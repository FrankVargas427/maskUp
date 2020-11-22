package com.henktech.maskup.tools;

import com.henktech.maskup.pojos.Place;

import java.util.ArrayList;
import java.util.Collections;

public class ProbCalc {

    public static ArrayList<Place> calculatePlaces(ArrayList<Place> inPlaces) {
        ArrayList<Place> outPlaces = new ArrayList<>();
        float maxVal = 0;

        /*
        Primero se suman todas las probabilidades y se introducen todos los lugares
        en un segundo ArrayList.
         */

        for (Place checkPlace : inPlaces) {
            maxVal = maxVal + checkPlace.getProbability();
            outPlaces.add(checkPlace);
        }

        //Aqui se ordenan los lugares en base a su probabilidad del mas alto al mas bajo.

        for (int i = 0; i < outPlaces.size(); i++) {
            for (int j = outPlaces.size() - 1; j > i; j--) {
                if (outPlaces.get(i).getProbability() > outPlaces.get(j).getProbability()) {
                    Place tmp = outPlaces.get(i);
                    outPlaces.set(i, outPlaces.get(j));
                    outPlaces.set(j, tmp);
                }
            }
        }
        Collections.reverse(outPlaces);

        /*
        Se normalizan todos los lugares con la siguiente formula para sacar cuanto es el
        valor de cada probabilidad. Asi, si se suman todos los porcentajes, da un 100.
         */

        for (Place checkPlace : outPlaces) {
            float currentProb = (checkPlace.getProbability() * 100) / maxVal;
            checkPlace.setProbability(currentProb);
        }

        return outPlaces;
    }

}
