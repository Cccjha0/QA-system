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
public class QADAO {
    public void insertQA(QA qa) {
        String query = "INSERT INTO QA (question, answer, created_by) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, qa.getQuestion());
            statement.setString(2, qa.getAnswer());
            statement.setInt(3, qa.getCreatedBy());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void searchQA(String keyword) {
        String query = "SELECT question, answer FROM QA WHERE question LIKE ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + keyword + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println("Quesiton: " + rs.getString("question"));
                System.out.println("Answer: " + rs.getString("answer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
