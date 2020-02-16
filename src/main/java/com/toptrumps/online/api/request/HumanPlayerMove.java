package com.toptrumps.online.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toptrumps.core.card.Attribute;

import java.util.List;

public class HumanPlayerMove extends PlayerMove {

    private Attribute selectedAttribute;

    public HumanPlayerMove() {
        // Jackson deserialization
    }

    public HumanPlayerMove(Attribute selectedAttribute, List<PlayerState> playerStates) {
        super(0, playerStates);
        this.selectedAttribute = selectedAttribute;
    }

    @JsonProperty
    public Attribute getSelectedAttribute() {
        return selectedAttribute;
    }

}
