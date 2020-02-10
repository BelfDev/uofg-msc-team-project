package com.toptrumps.online.api.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.toptrumps.core.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @JsonIgnore
    public Map<Player, Integer> getRoundWinsMap() {
        HashMap<Player, Integer> roundWinsMap = new HashMap<>();
        playersWinTrack.forEach(playerState -> {
            roundWinsMap.put(playerState.toPlayer(), playerState.getNumberOfRoundWins());
        });
        return roundWinsMap;
    }

}
