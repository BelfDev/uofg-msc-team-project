package com.toptrumps.online.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GamePreferences {

    private int numberOfPlayers;

    public GamePreferences() {
        // Jackson deserialization
    }

    public GamePreferences(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    @JsonProperty
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

}
