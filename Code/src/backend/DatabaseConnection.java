/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.sql.*;

/**
 * DatabaseConnection class is responsible for managing the connection to the Derby database.
 * It provides methods to establish and close the connection.
 * 
 * @author 陈炯昊
 */
public class DatabaseConnection {

    // Database URL to connect to the Derby database (create=true will create the database if it does not exist)
    private static final String URL = "jdbc:derby:..\\db\\QAsystemDB;create=true";
    private static Connection connection = null;

    /**
     * This method returns the database connection. If the connection does not exist or is closed, it will create a new one.
     *
     * @return Connection object to the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        // Check if connection is null or closed, then establish a new connection
        if (connection == null || connection.isClosed()) {
            try {
                // Load the Derby JDBC driver
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                System.out.println("数据库驱动程序加载成功");
                // Establish the connection
                connection = DriverManager.getConnection(URL);
                System.out.print("连接数据库成功");
            } catch (ClassNotFoundException e) {
                // Handle the exception if the JDBC driver class is not found
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * This method is used to shut down the database connection.
     * It attempts to shut down the Derby database cleanly.
     */
    public static void shutdown() {
        try {
            // Check if the connection is not null and is open before shutting down
            if (connection != null && !connection.isClosed()) {
                // Shutdown the Derby database
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            }
        } catch (SQLException e) {
            // A shutdown exception with SQL state "XJ015" indicates that Derby shut down normally
            if (!e.getSQLState().equals("XJ015")) {
                // Print the stack trace for any other SQL exception
                e.printStackTrace();
            }
        }
    }
    
}
