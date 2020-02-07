package com.toptrumps.db;
import java.sql.*; 
import java.sql.Connection; 
import java.sql.SQLException; 
import java.sql.Statement; 

public class IndividualGameDAOImpl implements IndividualGameDAO{

    public IndividualGameDAOImpl(){

    }

    public boolean create(){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement createRow = null;
        String createRowSQL = "INSERT INTO individual_game_data(winner,rounds_played,draws)" +
        "VALUES (?,?,?);";

        try {

            createRow = con.prepareStatement(createRowSQL);
            createRow.setString(1,"Human");
            createRow.setInt(2,20);
            createRow.setInt(3,20);
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