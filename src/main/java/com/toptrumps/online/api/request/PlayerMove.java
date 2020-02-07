package com.toptrumps.online.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerMove {

    private int activePlayerId;
    private List<PlayerState> playerStates;

    public PlayerMove() {
        // Jackson deserialization
    }

    public PlayerMove(int activePlayerId, List<PlayerState> playerStates) {
        this.activePlayerId = activePlayerId;
        this.playerStates = playerStates;
    }

    @JsonProperty("players")
    public List<PlayerState> getPlayerStates() {
        return playerStates;
    }

    @JsonProperty
    public int getActivePlayerId() {
        return activePlayerId;
    }

    public PlayerState getPlayerState() {
        return playerStates.get(activePlayerId);
    }

}
