package com.toptrumps.db;

import com.toptrumps.core.statistics.GameStateCollector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import com.toptrumps.core.player.Player;

public class Statistics {

    private int gamesPlayed;
    private int aiWins;
    private int humanWins;
    private int drawsAverage;
    private int maxRounds; 
    private GameDAOImpl concreteGameDAO;
    private PerformanceDAOImpl concretePerformanceDAO;
    private PlayersDAOImpl concretePlayerDAO;
  
    public Statistics(){

        try{

            buildStatistics(retrieveHighStats());
            concreteGameDAO = new GameDAOImpl();
            concretePerformanceDAO = new PerformanceDAOImpl();
            concretePlayerDAO = new PlayersDAOImpl();

        }catch(SQLException e){e.printStackTrace();}
    }

    public void persistData(GameStateCollector gameState){

        concreteGameDAO.create(gameState);
        concretePerformanceDAO.create(gameState);
    }

    private ResultSet retrieveHighStats(){

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

        try {
            playedStats = conn.prepareStatement(playedStatsSQL);
            rs = playedStats.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }
 
    public List retrieveAllStats(){

        Map<Integer,String> players = concretePlayerDAO.retrieve();
        List<FullStats> allStats = new ArrayList<FullStats>();
        ResultSet gameStats = concreteGameDAO.retrieve();
        ResultSet roundStats = concretePerformanceDAO.retrieve();
        int counter = 0;
        int gameId = 0;

        try{ 
            while(gameStats.next()){
                allStats.add(new FullStats(gameStats,players));
            }

            while(roundStats.next()){
                gameId = roundStats.getInt("game_id");
                while(gameId != allStats.get(counter).getGameID()){
                    counter++;
                }
                allStats.get(counter).buildRoundStats(roundStats,players);
            }
            }catch(SQLException e){e.printStackTrace();}
            
        return allStats;
        }
        
    private void buildStatistics(ResultSet resultSet)throws SQLException{

        while(resultSet.next()){
            this.gamesPlayed = resultSet.getInt("gamesplayed");
            this.drawsAverage = resultSet.getInt("avgdraws");
            this.maxRounds = resultSet.getInt("mostrounds");
            this.humanWins = resultSet.getInt("humanwins");
            this.aiWins = resultSet.getInt("aiwins");
        }
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getAiWins() {
        return aiWins;
    }

    public int getHumanWins() {
        return humanWins;
    }

    public int getAverageDraws() {
        return drawsAverage;
    }

    public int getMaxRounds() {
        return maxRounds;
    }
    
}
