package com.henktech.maskup.pojos;

import java.io.Serializable;

public class Place implements Serializable {
    String nameOfRoom;
    float probability;
    //Next iteration: picture

    public Place() {
        nameOfRoom = null;
        probability = 0;
    }

    public Place(String nameOfRoom, float probability) {
        this.nameOfRoom = nameOfRoom;
        this.probability = probability;
    }

    public String getName() {
        return nameOfRoom;
    }

    public void setName(String nameOfRoom) {
        this.nameOfRoom = nameOfRoom;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }
}
