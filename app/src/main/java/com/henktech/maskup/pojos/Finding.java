package com.henktech.maskup.pojos;

import java.io.Serializable;
import java.util.Calendar;

public class Finding implements Serializable {
    String placeFound;
    Calendar timeFound;

    public Finding(String placeFound, Calendar timeFound) {
        this.placeFound = placeFound;
        this.timeFound = timeFound;
    }

    public String getPlaceFound() {
        return placeFound;
    }

    public void setPlaceFound(String placeFound) {
        this.placeFound = placeFound;
    }

    public Calendar getTimeFound() {
        return timeFound;
    }

    public void setTimeFound(Calendar timeFound) {
        this.timeFound = timeFound;
    }
}
