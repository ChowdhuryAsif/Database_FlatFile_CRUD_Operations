package singletons;

import db.connection.demo.DBConnectionDemo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    private static Connection connection = null;
    private static DBConnection instance = new DBConnection();
    
    private DBConnection(){
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost/semester_management_system", "root", "");
            System.out.println("Connected!");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Connection getConnection(){
        return connection;
    }
}
