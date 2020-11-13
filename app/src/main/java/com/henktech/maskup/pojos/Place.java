package com.henktech.maskup.pojos;

import java.io.Serializable;

public class Place implements Serializable {
    String name;
    float probability;

    public Place(String name, float probability) {
        this.name = name;
        this.probability = probability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }
}
