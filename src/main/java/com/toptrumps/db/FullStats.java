package com.toptrumps.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.toptrumps.core.player.Player;
import com.toptrumps.online.api.request.PlayerState;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

// TODO: Refactor this class!!!
public class FullStats {

    public FullStats(ResultSet rs, PlayersList players) throws SQLException {

        this.players = players;
        this.gameID = rs.getInt("game_id");
        this.winnerID = rs.getInt("winner_id");
        this.roundsPlayed = rs.getInt("rounds_played");
        this.draws = rs.getInt("draws");
        this.winner = players.getPlayer(this.winnerID - 1);
        roundsMap = new HashMap<Player, Integer>();
    }

    private int gameID;
    private int winnerID;
    private Player winner;
    private PlayersList players;
    private int roundsPlayed;
    private int draws;
    private Map<Player, Integer> roundsMap;

    public void printStats() {
        System.out.println("Game ID: " + this.gameID);
        System.out.println("Winner ID: " + this.winnerID);
        System.out.println("Rounds Played: " + this.roundsPlayed);
        System.out.println("Draws: " + this.draws);

        for (Map.Entry<Player, Integer> entry : roundsMap.entrySet()) {
            System.out.println("Player " + entry.getKey().getName() + " won " + entry.getValue());
        }
    }

    public void buildRoundStats(ResultSet rs) throws SQLException {

        this.roundsMap.put(players.getPlayer(rs.getInt("player_id")), rs.getInt("rounds_won"));
    }

    @JsonProperty
    public int getGameID() {
        return this.gameID;
    }

    @JsonProperty
    public int getWinnerID() {
        return this.winnerID;
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
    public Map<Player, Integer> getRoundsMap() {
        return this.roundsMap;
    }

    // TODO: REFACTOR THIS!
    @JsonProperty("roundWins")
    public Map<PlayerState, Integer> getPlayerStateRoundsMap() {
        Map<PlayerState, Integer> playerStateRoundsMap = new HashMap<>();
        this.roundsMap.forEach((player, roundWins) -> {
                    playerStateRoundsMap.put(new PlayerState(player), roundWins);
                }
        );
        return playerStateRoundsMap;
    }
}