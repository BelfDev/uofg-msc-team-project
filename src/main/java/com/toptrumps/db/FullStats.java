package com.toptrumps.db;
import java.sql.*;
import java.util.*;
import com.toptrumps.core.player.Player;

public class FullStats{

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

    public int getGameID(){
        return this.gameID;
    }

    public int getRoundsPlayed(){
        return this.roundsPlayed;
    }

    public int getDraws(){
        return this.draws;
    }

    public Map getRoundsMap(){
        return this.roundsMap;
    }
}