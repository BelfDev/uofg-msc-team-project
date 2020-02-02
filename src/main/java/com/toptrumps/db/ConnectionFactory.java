package com.toptrumps.db;
    import java.sql.DriverManager; 
    import java.sql.Connection; 
    import java.sql.SQLException; 
    
    public class ConnectionFactory { 

        private static String localhost = "jdbc:postgresql://localhost:5432/";
        private static String username = "postgres";
        private static String password = "postgres";

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