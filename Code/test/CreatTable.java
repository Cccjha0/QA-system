/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.*;

/**
 * CreatTable class is used to execute SQL statements against the Apache Derby database.
 * It includes functionality to create tables, delete entries, and manage the database schema.
 * 
 * Note: The provided SQL statements for creating tables are commented out for easy modification.
 * 
 * Author: wang
 */
public class CreatTable {

    public static void main(String[] args) {
        String url = "jdbc:derby:..\\db\\QAsystemDB;create=true";
        try ( Connection conn = DriverManager.getConnection(url);  Statement stmt = conn.createStatement()) { // 创建表 
//            String sql = "create table QA (id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n"
//                    + "question CLOB NOT NULL,\n"
//                    + "answer CLOB NOT NULL ,\n"
//                    + "created_by INT,"
//                    + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
//                    + "FOREIGN KEY (created_by) REFERENCES Users(id))";
            
//            String sql = "create table Users (id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
//                    + "password VARCHAR(255) NOT NULL, name  VARCHAR(255) NOT NULL,"
//                    + "isStudent BOOLEAN NOT NULL, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            
//            String sql = "CREATE TABLE RecentQuery (\n"
//                    + "    query_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n"
//                    + "    user_id INT,\n"
//                    + "    query_text VARCHAR(1023),\n"
//                    + "    query_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n"
//                    + "    FOREIGN KEY (user_id) REFERENCES Users(id)\n"
//                    + ")";
            String sql = "DELETE FROM QA WHERE ID = 1001";
            stmt.executeUpdate(sql);
            System.out.println("表创建成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//DROP TABLE RecentQueries
