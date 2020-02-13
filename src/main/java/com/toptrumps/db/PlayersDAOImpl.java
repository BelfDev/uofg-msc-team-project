package com.toptrumps.db;
import java.util.*;
import java.sql.*;
public class PlayersDAOImpl{

    public PlayersDAOImpl(){

    }

    public Map retrieve(){

        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement playerSTMT = null;
        ResultSet rs = null;
        Map<Integer,String> map = new HashMap<Integer,String>();

        String playerSTMTSQL = "SELECT * FROM player;";

        try{

            playerSTMT = conn.prepareStatement(playerSTMTSQL);
            rs = playerSTMT.executeQuery();

            while(rs.next()){
                map.put(rs.getInt("player_id"),rs.getString("name"));
            }
           
        }catch(SQLException e){e.printStackTrace();}

        finally{
            try{
                if(conn != null){conn.close();}
                if(playerSTMT != null){playerSTMT.close();}
                if(rs != null){rs.close();}
            }catch(SQLException e){
                e.printStackTrace();}
        }
        return map;
    }
}