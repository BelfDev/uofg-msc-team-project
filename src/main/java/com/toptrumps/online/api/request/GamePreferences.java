package com.toptrumps.online.api.request;

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
