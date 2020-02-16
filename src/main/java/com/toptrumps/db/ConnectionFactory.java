package com.toptrumps.db;
    import java.sql.DriverManager; 
    import java.sql.Connection; 
    import java.sql.SQLException; 
    
    public class ConnectionFactory { 

        private static String localhost = "jdbc:postgresql://52.24.215.108/MadStax";
        private static String username = "MadStax";
        private static String password = "MadStax";

        public static Connection getConnection() { 
          
            Connection connection = null; 
            try { 
                connection = DriverManager.getConnection(localhost,username,password); 
            } catch (SQLException e) { 
                System.out.println("Connection Failed!"); 
                e.printStackTrace(); 
            } 
            if (connection != null) { 
                System.out.println("You are in control."); 
            } 
            else { 
                System.out.println("Failed to establish connection!"); 
            } 
            return connection;
        } 
    } 