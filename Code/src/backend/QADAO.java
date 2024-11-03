/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import backend.*;
import java.sql.*;

/**
 * QADAO class provides methods to interact with the QA table in the database.
 * It allows inserting new QA entries and searching for QA entries based on keywords.
 * 
 * @author 陈炯昊
 */
public class QADAO {

    /**
     * Inserts a QA entry into the database.
     *
     * @param qa The QA object containing the question, answer, and creator's ID
     * @return true if the insertion was successful, false otherwise
     */
    public static boolean insertQA(QA qa) {
        String query = "INSERT INTO QA (question, answer, created_by) VALUES (?, ?, ?)";
        try ( Connection connection = DatabaseConnection.getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, qa.getQuestion());
            statement.setString(2, qa.getAnswer());
            statement.setInt(3, qa.getCreatedBy());
            statement.executeUpdate();
        } catch (SQLException e) {
            // Print the stack trace if an SQL exception occurs
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Searches for QA entries in the database that match the given keyword.
     * If no matching QA is found, returns null.
     *
     * @param userId The ID of the user performing the search
     * @param keyword The keyword to search for in the question field
     * @return An array of QA objects that match the keyword, or null if none found
     */
    public static QA[] searchQA(int userId, String keyword) {
        QA qa[] = null;
        int cnt = 0;

        String query = "SELECT * FROM QA WHERE question LIKE ?";
        try ( Connection connection = DatabaseConnection.getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, "%" + keyword + "%");
            ResultSet rs = statement.executeQuery();
            
            if (!rs.next()) {  // If no results are found, return null
                return qa;
            }
            qa = new QA[100]; // Initialize an array to hold up to 100 QA entries
            do { // Process at least one result if found
                String question = rs.getString("question");
                String answer = rs.getString("answer");
                int createdBy = rs.getInt("created_by");
                int id = rs.getInt("id");
                String createdAt = rs.getString("created_at");
                
                qa[cnt] = new QA(question, answer, createdBy);
                qa[cnt].setId(id);
                qa[cnt].setCreatedAt(createdAt);
                cnt++;
                
            } while (rs.next());
            
        } catch (SQLException e) {
            // Print the stack trace if an SQL exception occurs
            e.printStackTrace();
        }
        
        // Save or update the search query in the RecentQueries table
        RecentQueriesDAO.saveOrUpdateQuery(userId, keyword);

        return qa; 
    }

}
