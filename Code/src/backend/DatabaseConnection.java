/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.sql.*;

/**
 *
 * @author 陈炯昊
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:derby:..\\db\\QAsystemDB;create=true";
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                System.out.println("数据库驱动程序加载成功");
                connection = DriverManager.getConnection(URL);
                System.out.print("连接数据库成功");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void shutdown() {
        try {
            if (connection != null && !connection.isClosed()) {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            }
        } catch (SQLException e) {
            if (!e.getSQLState().equals("XJ015")) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
