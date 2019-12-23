/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author User
 */
public class Database {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties properties = loadPropreties();
                
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cursojdbc", properties);
                
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return connection;
    }
    
    public static void closeConnection(){
        if(connection != null){
            try{
                connection.close();
            } catch(SQLException e){
                throw new DbException(e.getMessage());
            }
        }
    }

    public static Properties loadPropreties() {
        try (FileInputStream fileInputStream = new FileInputStream("C:\\Users\\User\\Documents\\NetBeansProjects\\JdbcTeste\\build\\classes\\db.properties.txt")) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }
    
    public static void fecharStatement(Statement statement){
        if (statement != null) {
            try{
                statement.close();
            } catch(SQLException e ){
                throw new DbException(e.getMessage());
            }
        }
    }
    
    public static void fecharResultSet(ResultSet resultSet){
        if (resultSet != null) {
            try{
                resultSet.close();
            } catch(SQLException e ){
                throw new DbException(e.getMessage());
            }
        }
    }
    
}
