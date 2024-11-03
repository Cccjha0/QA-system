/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * RecentQueriesDAO class provides methods to manage recent search queries in the database.
 * It allows saving or updating recent queries and retrieving the last 10 queries made by a user.
 * 
 * @author 陈炯昊
 */
public class RecentQueriesDAO {

    /**
     * Saves or updates a user's recent query in the database.
     * If the user already has a matching query, its timestamp is updated.
     * If the user has more than 15 queries, the oldest one is removed.
     *
     * @param userId The ID of the user making the query
     * @param queryText The text of the query to save or update
     */
    public static void saveOrUpdateQuery(int userId, String queryText) {
        String updateQuery = "UPDATE RecentQuery SET query_time = CURRENT_TIMESTAMP WHERE user_id = ? AND query_text = ?";
        String insertQuery = "INSERT INTO RecentQuery (user_id, query_text) VALUES (?, ?)";
        String countQuery = "SELECT COUNT(*) FROM RecentQuery WHERE user_id = ?";
        String deleteOldestQuery = "DELETE FROM RecentQuery WHERE query_id = (SELECT query_id FROM RecentQuery WHERE user_id = ? ORDER BY query_time ASC FETCH FIRST ROW ONLY)";

        try ( Connection conn = DatabaseConnection.getConnection()) {

            // Update the timestamp of an existing matching query
            try ( PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setInt(1, userId);
                updateStmt.setString(2, queryText);
                int rowsAffected = updateStmt.executeUpdate();

                // If no matching query record exists, insert a new record
                if (rowsAffected == 0) {
                    // Check if the user has more than 15 query records
                    try ( PreparedStatement countStmt = conn.prepareStatement(countQuery)) {
                        countStmt.setInt(1, userId);
                        ResultSet rs = countStmt.executeQuery();
                        if (rs.next() && rs.getInt(1) >= 15) {
                            // Delete the oldest record
                            try ( PreparedStatement deleteStmt = conn.prepareStatement(deleteOldestQuery)) {
                                deleteStmt.setInt(1, userId);
                                deleteStmt.executeUpdate();
                            }
                        }
                    }

                    // Insert a new record
                    try ( PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                        insertStmt.setInt(1, userId);
                        insertStmt.setString(2, queryText);
                        insertStmt.executeUpdate();
                    }
                }
            }
            System.out.println("Query saved or updated successfully with limits.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the most recent 10 queries made by a user.
     *
     * @param userId The ID of the user whose queries are being retrieved
     * @return A list of the most recent 10 queries made by the user
     */
    public static List<String> getRecentQueries(int userId) {
        List<String> recentQueries = new ArrayList<>();
        String query = "SELECT query_text FROM RecentQuery WHERE user_id = ? ORDER BY query_time DESC FETCH FIRST 10 ROWS ONLY";

        try ( Connection conn = DatabaseConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                recentQueries.add(rs.getString("query_text"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recentQueries;
    }

}
