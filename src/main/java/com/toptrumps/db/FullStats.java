package com.toptrumps.db;
import java.sql.*;
import java.util.*;
import com.toptrumps.core.player.Player;

public class FullStats{

    public FullStats(ResultSet rs,PlayersList players) throws SQLException{

        this.players = players;
        this.gameID = rs.getInt(1);
        this.winnerID = rs.getInt(2);
        this.roundsPlayed = rs.getInt(3);
        this.draws = rs.getInt(4);
        this.winner = players.getPlayer(this.winnerID-1);
        roundsMap = new HashMap<Player,Integer>();
    }

    private int gameID;
    private int winnerID;
    private Player winner;
    private PlayersList players;
    private int roundsPlayed;
    private int draws;
    private Map<Player,Integer> roundsMap; 

    public void printStats(){
        System.out.println("Game ID: " + this.gameID);
        System.out.println("Winner ID: " + this.winnerID);
        System.out.println("Rounds Played: " + this.roundsPlayed);
        System.out.println("Draws: " + this.draws);
        for (Map.Entry<Player,Integer> entry : roundsMap.entrySet()){
            System.out.println("Player " + entry.getKey().getName() + " won " + entry.getValue());
        }
    }

    public void buildRoundStats(ResultSet rs) throws SQLException{

        this.roundsMap.put(players.getPlayer(rs.getInt(1)),rs.getInt(3));
    }

    public int getGameID(){
        return this.gameID;
    }

    public int getWinnerID(){
        return this.winnerID;
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