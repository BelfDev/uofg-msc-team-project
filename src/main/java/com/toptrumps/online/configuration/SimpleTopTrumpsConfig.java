package com.toptrumps.online.configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is a simplified version of the TopTrumpsJSONConfiguration.
 * It is used to deserialize the deckFile name from the TopTrumps.json file.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleTopTrumpsConfig {

    private String deckFileName;

    public SimpleTopTrumpsConfig() {
    }

    public SimpleTopTrumpsConfig(String deckFileName) {
        this.deckFileName = deckFileName;
    }

    @JsonProperty("deckFile")
    public String getDeckFileName() {
        return deckFileName;
    }
}
