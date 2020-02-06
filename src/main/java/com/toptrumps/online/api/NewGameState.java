package com.toptrumps.online.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toptrumps.core.player.AIPlayer;
import com.toptrumps.core.player.Player;

import java.util.ArrayList;

public class NewGameState {

    int numberOfPlayers;
    int roundNumber;
    int activePlayerId;
    Player humanPlayer;
    ArrayList<AIPlayer> aiPlayers;

    public NewGameState() {
        // Jackson deserialization
    }

    public NewGameState(int numberOfPlayers, int roundNumber, int activePlayerId, Player humanPlayer, ArrayList<AIPlayer> aiPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.roundNumber = roundNumber;
        this.activePlayerId = activePlayerId;
        this.humanPlayer = humanPlayer;
        this.aiPlayers = aiPlayers;
    }

    @JsonProperty
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    @JsonProperty
    public int getRoundNumber() {
        return roundNumber;
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
    public ArrayList<AIPlayer> getAiPlayers() {
        return aiPlayers;
    }

}
