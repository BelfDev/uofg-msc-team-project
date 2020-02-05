package com.toptrumps.db;
import java.sql.*; 
import java.sql.Connection; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 

public class PersistentDataDAO{

    public static void updatePersistentData(){
        
        PreparedStatement updateBasicData = null;
        PreparedStatement updateAiWins = null;
        PreparedStatement updateHumanWins = null;

        Connection con = ConnectionFactory.getConnection();

        String updateBasicDataSQL = 

        "UPDATE persistent_game_data " +  
                "SET games_played = subquery.games_played, " +  
                "draws_average = subquery.draws_average, " +   
                "max_rounds = subquery.max_rounds " +  
                "FROM (SELECT COUNT (*) AS games_played, " +  
                "  AVG(draws) AS draws_average, " +  
                "  MAX(rounds_played) AS max_rounds " +   
                "  FROM  individual_game_data) " + 
                "  AS subquery; ";

        String updateAiWinsSQL = 

        "UPDATE persistent_game_data " +
                "SET ai_wins = subquery.ai_wins " +  
                "FROM (SELECT COUNT (winner) AS ai_wins " +  
                "  FROM individual_game_data " +
                "  WHERE (winner != 'Human')) " +  
                "  AS subquery; ";

        String updateHumanWinsSQL =

        "UPDATE persistent_game_data " +  
        "SET human_wins = subquery.human_wins " + 
        "FROM (SELECT COUNT (winner) AS human_wins " +  
        "  FROM individual_game_data " + 
        "  WHERE (winner = 'Human')) " +  
        "  AS subquery; ";

    try {

        updateBasicData = con.prepareStatement(updateBasicDataSQL);
        updateBasicData.executeUpdate();

        updateAiWins = con.prepareStatement(updateAiWinsSQL);
        updateAiWins.executeUpdate();

        updateHumanWins = con.prepareStatement(updateHumanWinsSQL);
        updateHumanWins.executeUpdate();
        
    } catch (SQLException e) {
        e.printStackTrace();}

    finally {
        try {

            if (updateBasicData != null) {updateBasicData.close();}
            if (updateAiWins != null) {updateAiWins.close();}
            if (updateHumanWins != null) {updateHumanWins.close();}
            if (con != null) {con.close();}

        } catch (SQLException e) {
            e.printStackTrace();
        }
      }
    }

    public static Statistics getStatistics(){

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement getStats = null;
        String getStatsSQL = "SELECT * FROM persistent_game_data;";
        ResultSet results = null;
        Statistics statistics = null;

        try{

            getStats = conn.prepareStatement(getStatsSQL);
            results = getStats.executeQuery();

            while(results.next()) {statistics = new Statistics(results);}
            
        }catch(SQLException e){
            e.printStackTrace();   
        }

        finally{
            try{
               if(getStats != null){getStats.close();}
               if(conn != null){conn.close();}
               results.close();
            }catch(SQLException e){
               e.printStackTrace();}
            }

        return statistics;
    }
}
