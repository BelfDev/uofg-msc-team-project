package com.toptrumps.db;
import com.toptrumps.core.statistics.GameStateCollector;
import com.toptrumps.core.player.Player;
import java.sql.*;

    public class GameDAOImpl implements GameDAO{

        public GameDAOImpl(){

        }
    
        public boolean create(GameStateCollector gameState){
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement createRow = null;
            String createRowSQL = "INSERT INTO _game_data(winner_id,rounds_played,draws)" +
            "VALUES (?,?,?);";
    
            try {
    
                createRow = conn.prepareStatement(createRowSQL);
                createRow.setInt(1,gameState.getFinalWinner().getId());
                createRow.setInt(2,gameState.getNumberOfRounds());
                createRow.setInt(3,gameState.getNumberOfDraws());
                createRow.executeUpdate();
    
            }catch(SQLException e){
                e.printStackTrace();
            }
    
            finally{
                try{
                    if(conn != null){conn.close();}
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
            String retrieveRowSQL = "SELECT *" + "FROM individual_game_data";

            try{
                retrieveRow = conn.prepareStatement(retrieveRowSQL);
                rs = retrieveRow.executeQuery();
            }catch(SQLException e){e.printStackTrace();}
        
            // finally{
            //     try{
            //         if(conn != null){conn.close();}
            //         if(retrieveRow != null){retrieveRow.close();}
            //     }catch(SQLException e){
            //         e.printStackTrace();
            //     }
            // }
            return rs;
    }
}