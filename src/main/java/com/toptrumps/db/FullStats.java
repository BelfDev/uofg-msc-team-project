package com.toptrumps.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.toptrumps.core.player.Player;
import com.toptrumps.online.api.request.PlayerState;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

    public FullStats(ResultSet rs,Map<Integer,String> players) throws SQLException{

        this.gameID = rs.getInt("game_id");
        this.winner = new Player(rs.getInt("winner_id"),players.get(rs.getInt("winner_id")));
        this.roundsPlayed = rs.getInt("rounds_played");
        this.draws = rs.getInt("draws");
        roundsMap = new HashMap<Player,Integer>();

    }

    public void printStats() {
        System.out.println("Game ID: " + this.gameID);
        System.out.println("Rounds Played: " + this.roundsPlayed);
        System.out.println("Draws: " + this.draws);

        for (Map.Entry<Player, Integer> entry : roundsMap.entrySet()) {
            System.out.println("Player " + entry.getKey().getName() + " won " + entry.getValue());
        }

        System.out.println("---------------------------------------");
    }

    private int gameID;
    private Player winner;
    private int roundsPlayed;
    private int draws;
    private Map<Player,Integer> roundsMap; 

    public void buildRoundStats(ResultSet rs,Map<Integer,String> players) throws SQLException{
        
        int playerID = rs.getInt("player_id");
        String playerName = players.get(playerID);
        Player player = new Player(playerID,playerName);
        this.roundsMap.put(player,rs.getInt("rounds_won"));
    }

    @JsonProperty
    public int getGameID() {
        return this.gameID;
    }

    public int getRoundsPlayed(){
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
    public Map<String, Integer> getPlayerNameRoundsMap() {
        Map<String, Integer> playerStateRoundsMap = new HashMap<>();
        this.roundsMap.forEach((player, roundWins) -> {
                playerStateRoundsMap.put(player.getName(), roundWins);
            }
        );
        return playerStateRoundsMap;
    }
}