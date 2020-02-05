package com.toptrumps.db;
import java.sql.*; 
import java.sql.Connection; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 

public class Statistics{

    private int games_played;
    private int ai_wins;
    private int human_wins;
    private int draws_average;
    private int max_rounds; 

    public Statistics(ResultSet resultSet)throws SQLException{
        this.games_played = resultSet.getInt(1);
        this.ai_wins = resultSet.getInt(2);
        this.human_wins = resultSet.getInt(3);
        this.draws_average = resultSet.getInt(4);
        this.max_rounds = resultSet.getInt(5);
    }

    public int getGamesPlayed(){
        return games_played;
    }

    public int getAiWins(){
        return ai_wins;
    }

    public int getHumanWins(){
        return human_wins;
    }

    public int getAverageDraws(){
        return draws_average;
    }

    public int getMaxRounds(){
        return max_rounds;
    }
}

