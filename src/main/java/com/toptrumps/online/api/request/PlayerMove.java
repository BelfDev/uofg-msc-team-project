package com.toptrumps.online.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerMove {

    private PlayerState activePlayerState;
    private List<PlayerState> playerStates;

    public PlayerMove() {
        // Jackson deserialization
    }

    public PlayerMove(List<PlayerState> playerStates, PlayerState activePlayerState) {
        this.playerStates = playerStates;
        this.activePlayerState = activePlayerState;
    }

    public PlayerMove(List<PlayerState> playerStates) {
        this(playerStates, null);
    }

    @JsonProperty("players")
    public List<PlayerState> getPlayerStates() {
        return playerStates;
    }

    @JsonProperty("activePlayer")
    public PlayerState getActivePlayerState() {
        return activePlayerState;
    }
}
