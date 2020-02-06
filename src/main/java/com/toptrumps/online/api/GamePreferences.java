package com.toptrumps.online.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GamePreferences {

    private int numberOfOpponents;

    public GamePreferences() {
        // Jackson deserialization
    }

    public GamePreferences(int numberOfOpponents) {
        this.numberOfOpponents = numberOfOpponents;
    }

    @JsonProperty
    public int getNumberOfOpponents() {
        return numberOfOpponents;
    }

}
