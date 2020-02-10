package com.toptrumps.db;
import java.sql.*; 
import com.toptrumps.core.statistics.GameStateCollector;

public class Statistics{

    private int gamesPlayed;
    private int aiWins;
    private int humanWins;
    private int drawsAverage;
    private int maxRounds; 

    public Statistics(){
        try{
            buildStatistics(retrieveStats());
        }catch(SQLException e){e.printStackTrace();}
    }

    public void persistData(GameStateCollector gameState){
        
        IndividualGameDAOImpl igdi = new IndividualGameDAOImpl(gameState);
        igdi.create();
        PerformanceDAOImpl pdi = new PerformanceDAOImpl(gameState);
        pdi.create();
    }

    public ResultSet retrieveStats(){

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement playedStats = null;
        ResultSet rs = null;
        String playedStatsSQL = 

        "SELECT (SELECT COUNT (game_id)" +
        "            FROM  individual_game_data) AS gamesplayed," +
        "        (SELECT AVG(draws)" +
        "            FROM individual_game_data) AS avgdraws," +
        "        (SELECT MAX(rounds_played)" +
        "            FROM individual_game_data) AS mostrounds," +
        "        (SELECT COUNT (winner_id)" +
        "            FROM individual_game_data" +
        "            WHERE winner_id = 0) AS humanwins," +
        "        (SELECT COUNT (winner_id)" +
        "            FROM individual_game_data" +
        "            WHERE winner_id != 0) AS aiwins;";

        try{
            playedStats = conn.prepareStatement(playedStatsSQL);
            rs = playedStats.executeQuery();
            return rs;
        }catch(SQLException e){e.printStackTrace();}

        return rs;
    }

    public void buildStatistics(ResultSet resultSet)throws SQLException{

        while(resultSet.next()){
            this.gamesPlayed = resultSet.getInt(1);
            this.drawsAverage = resultSet.getInt(2);
            this.maxRounds = resultSet.getInt(3);
            this.humanWins = resultSet.getInt(4);
            this.aiWins = resultSet.getInt(5);
        }
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

