package com.toptrumps.online.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toptrumps.core.player.Player;

import java.util.List;

public class InitialGameState {

    private int numberOfOpponents;
    private int activePlayerId;
    private Player humanPlayer;
    private List<Player> aiPlayers;

    public InitialGameState() {
        // Jackson deserialization
    }

    public InitialGameState(int numberOfOpponents, int activePlayerId, Player humanPlayer, List<Player> aiPlayers) {
        this.numberOfOpponents = numberOfOpponents;
        this.activePlayerId = activePlayerId;
        this.humanPlayer = humanPlayer;
        this.aiPlayers = aiPlayers;
    }

    @JsonProperty
    public int getNumberOfOpponents() {
        return numberOfOpponents;
    }

    @JsonProperty
    public int getActivePlayerId() {
        return activePlayerId;
    }

    @JsonProperty
    public Player getHumanPlayer() {
        return humanPlayer;
    }

    @JsonProperty
    public List<Player> getAiPlayers() {
        return aiPlayers;
    }

}
