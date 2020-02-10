package com.toptrumps.online.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FinalGameState {

    private int numberOfDraws;
    private PlayerState finalWinner;
    private int numberOfRounds;
    private List<PlayerState> playersWinTrack;

    public FinalGameState() {
        // Jackson deserialization
    }

    public FinalGameState(int numberOfDraws, PlayerState finalWinner, int numberOfRounds, List<PlayerState> playersWinTrack) {
        this.numberOfDraws = numberOfDraws;
        this.finalWinner = finalWinner;
        this.numberOfRounds = numberOfRounds;
        this.playersWinTrack = playersWinTrack;
    }

    @JsonProperty
    public int getNumberOfDraws() {
        return numberOfDraws;
    }

    @JsonProperty
    public PlayerState getFinalWinner() {
        return finalWinner;
    }

    @JsonProperty
    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    @JsonProperty("roundWins")
    public List<PlayerState> getPlayersWinTrack() {
        return playersWinTrack;
    }

}
