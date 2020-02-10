package com.toptrumps.db;
import java.util.Map;
import java.sql.*; 
import com.toptrumps.core.statistics.GameStateCollector;
import com.toptrumps.core.player.Player;

public class PerformanceDAOImpl implements GameDAO{

    private int gameID;
    private Map<Player,Integer> map;

    public PerformanceDAOImpl(GameStateCollector gameState){

        this.gameID = getGameID();
        this.map = gameState.getRoundWinsMap();
    }

    public int getGameID(){

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int id = 0;

        String stmtSQL = "SELECT MAX(game_id) FROM individual_game_data;";

        try{

            stmt = conn.prepareStatement(stmtSQL);
            rs = stmt.executeQuery();
            while(rs.next()){ id = rs.getInt(1);}
           
        }catch(SQLException e){e.printStackTrace();}

        return id;
    }

    public boolean create(){
        
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
}