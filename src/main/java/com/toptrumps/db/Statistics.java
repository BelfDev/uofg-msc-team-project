package com.toptrumps.db;
import java.sql.*; 
import java.sql.Connection; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 

public class Statistics{

    private int gamesPlayed;
    private int aiWins;
    private int humanWins;
    private int drawsAverage;
    private int maxRounds; 

    public Statistics(ResultSet resultSet)throws SQLException{
        this.gamesPlayed = resultSet.getInt(1);
        this.aiWins = resultSet.getInt(2);
        this.humanWins = resultSet.getInt(3);
        this.drawsAverage = resultSet.getInt(4);
        this.maxRounds = resultSet.getInt(5);
    }

    public int getGamesPlayed(){
        return gamesPlayed;
    }

    public int getAiWins(){
        return aiWins;
    }

    public int getHumanWins(){
        return humanWins;
    }

    public int getAverageDraws(){
        return drawsAverage;
    }

    public int getMaxRounds(){
        return maxRounds;
    }
}

