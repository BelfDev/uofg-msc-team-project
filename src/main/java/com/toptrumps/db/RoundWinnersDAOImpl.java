package com.toptrumps.db;
import java.sql.*; 
import java.sql.Connection; 
import java.sql.SQLException; 
import java.sql.Statement; 

public class RoundWinnersDAOImpl implements RoundWinnersDAO{

    public RoundWinnersDAOImpl(){
    }

    public boolean create(){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement createRow = null;
        String createRowSQL = 

            "INSERT INTO round_winners" +
            "(human_wins,ai_1_wins," +
            "ai_2_wins,ai_3_wins,ai_4_wins)" +
            "VALUES (?,?,?,?,?);";

        try {

            createRow = con.prepareStatement(createRowSQL);
            createRow.setInt(1,5);
            createRow.setInt(2,5);
            createRow.setInt(3,5);
            createRow.setInt(4,0);
            createRow.setInt(5,0);
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

