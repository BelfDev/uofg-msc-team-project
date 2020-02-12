package com.toptrumps.db;
import java.util.Map;
import java.sql.*; 
import com.toptrumps.core.statistics.GameStateCollector;
import com.toptrumps.core.player.Player;

public class PerformanceDAOImpl implements GameDAO{

    public PerformanceDAOImpl(){

    }

    public int getGameID(){

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement gameIdSTMT = null;
        ResultSet rs = null;
        int id = 0;

        String gameIdSTMTSQL = "SELECT MAX(game_id) FROM individual_game_data;";

        try{

            gameIdSTMT = conn.prepareStatement(gameIdSTMTSQL);
            rs = gameIdSTMT.executeQuery();
            while(rs.next()){ id = rs.getInt(1);}
           
        }catch(SQLException e){e.printStackTrace();}

        return id;
    }

    public boolean create(GameStateCollector gameState){

        int gameID = getGameID();
        Map<Player,Integer> map = gameState.getRoundWinsMap();
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement createRow = null;
        String createRowSQL = 

            "INSERT INTO player_performance" +
            "(game_id,player_id,rounds_won)" +
            "VALUES (?,?,?);";

        try {

            for (Map.Entry<Player,Integer> entry : map.entrySet()){
                createRow = con.prepareStatement(createRowSQL);
                createRow.setInt(1,gameID);
                createRow.setInt(2,entry.getKey().getId());
                createRow.setInt(3,entry.getValue());
                createRow.executeUpdate();
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        finally{
            try{
                if(con != null){con.close();}
                if(createRow != null){createRow.close();}
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return true;
    } 

    public ResultSet retrieve(){

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement retrieveRow = null;
        ResultSet rs = null;
        String retrieveRowSQL = 

            "SELECT *" +
            "FROM player_performance";
        
        try{
            retrieveRow = conn.prepareStatement(retrieveRowSQL);
            rs = retrieveRow.executeQuery();
        }catch(SQLException e){e.printStackTrace();}
    
        return rs;
        }
    }
