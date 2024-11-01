/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 陈炯昊
 */
public class RecentQueriesDAO {

    // 添加查询记录，并控制记录数量上限
    public static void saveOrUpdateQuery(int userId, String queryText) {
        String updateQuery = "UPDATE RecentQuery SET query_time = CURRENT_TIMESTAMP WHERE user_id = ? AND query_text = ?";
        String insertQuery = "INSERT INTO RecentQuery (user_id, query_text) VALUES (?, ?)";
        String countQuery = "SELECT COUNT(*) FROM RecentQuery WHERE user_id = ?";
        String deleteOldestQuery = "DELETE FROM RecentQuery WHERE query_id = (SELECT query_id FROM RecentQuery WHERE user_id = ? ORDER BY query_time ASC FETCH FIRST ROW ONLY)";

        try ( Connection conn = DatabaseConnection.getConnection()) {

            // 更新现有相同查询的时间戳
            try ( PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setInt(1, userId);
                updateStmt.setString(2, queryText);
                int rowsAffected = updateStmt.executeUpdate();

                // 如果没有相同查询记录，则插入新记录
                if (rowsAffected == 0) {
                    // 检查用户的查询记录数是否超过 15 条
                    try ( PreparedStatement countStmt = conn.prepareStatement(countQuery)) {
                        countStmt.setInt(1, userId);
                        ResultSet rs = countStmt.executeQuery();
                        if (rs.next() && rs.getInt(1) >= 15) {
                            // 删除最早的记录
                            try ( PreparedStatement deleteStmt = conn.prepareStatement(deleteOldestQuery)) {
                                deleteStmt.setInt(1, userId);
                                deleteStmt.executeUpdate();
                            }
                        }
                    }

                    // 插入新记录
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

    // 获取用户的最近10条查询记录
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
