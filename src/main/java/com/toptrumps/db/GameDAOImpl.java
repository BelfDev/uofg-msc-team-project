package com.toptrumps.db;
import com.toptrumps.core.statistics.GameStateCollector;
import com.toptrumps.core.player.Player;
import java.sql.*;

    public class GameDAOImpl implements GameDAO{

        public GameDAOImpl(GameStateCollector gameState){
    
            winnerID = gameState.getFinalWinner().getId();
            noRoundsPlayed = gameState.getNumberOfRounds();
            noDraws = gameState.getNumberOfDraws();
        }
    
        private int winnerID;
        private int noRoundsPlayed;
        private int noDraws;
    
        public boolean create(){
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement createRow = null;
            String createRowSQL = "INSERT INTO _game_data(winner_id,rounds_played,draws)" +
            "VALUES (?,?,?);";
    
            try {
    
                createRow = con.prepareStatement(createRowSQL);
                createRow.setInt(1,winnerID);
                createRow.setInt(2,noRoundsPlayed);
                createRow.setInt(3,noDraws);
                createRow.executeUpdate();
    
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
    }