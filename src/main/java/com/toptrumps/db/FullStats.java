package com.toptrumps.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.toptrumps.core.player.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class FullStats {

    private int gameID;
    private Player winner;
    private int roundsPlayed;
    private int draws;
    private Map<String, Integer> roundsMap;

    public FullStats(ResultSet rs, Map<Integer, String> players) throws SQLException {
        this.gameID = rs.getInt("game_id");
        this.winner = new Player(rs.getInt("winner_id"), players.get(rs.getInt("winner_id")));
        this.roundsPlayed = rs.getInt("rounds_played");
        this.draws = rs.getInt("draws");
        roundsMap = new HashMap<>();
    }

    public void buildRoundStats(ResultSet rs, Map<Integer, String> players) throws SQLException {
        int playerID = rs.getInt("player_id");
        String playerName = players.get(playerID);
        this.roundsMap.put(playerName, rs.getInt("rounds_won"));
    }

    @JsonProperty
    public int getGameID() {
        return this.gameID;
    }

    @JsonProperty
    public Integer getWinnerID() {
        return winner.getId();
    }

    @JsonProperty
    public int getRoundsPlayed() {
        return this.roundsPlayed;
    }

    @JsonProperty
    public int getDraws() {
        return this.draws;
    }

    @JsonIgnore
    public Player getWinner() {
        return winner;
    }

    @JsonProperty("roundWins")
    public Map<String, Integer> getRoundsMap() {
        return this.roundsMap;
    }

}