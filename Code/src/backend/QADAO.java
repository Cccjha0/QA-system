/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import backend.*;
import java.sql.*;

/**
 *
 * @author 陈炯昊
 */
public class QADAO {

    public static boolean insertQA(QA qa) {
        String query = "INSERT INTO QA (question, answer, created_by) VALUES (?, ?, ?)";
        try ( Connection connection = DatabaseConnection.getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, qa.getQuestion());
            statement.setString(2, qa.getAnswer());
            statement.setInt(3, qa.getCreatedBy());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *
     *return null -> not found Q&A
     *
     **/
    
    public static QA[] searchQA(int userId, String keyword) {
        QA qa[] = null;
        int cnt = 0;

        String query = "SELECT * FROM QA WHERE question LIKE ?";
        try ( Connection connection = DatabaseConnection.getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, "%" + keyword + "%");
            ResultSet rs = statement.executeQuery();
            
            if (!rs.next()) {  // 直接使用 next() 检查
                return qa;
            }
            qa = new QA[100];
            do { // 使用 do-while 处理至少有一条结果的情况
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
            e.printStackTrace();
        }
        
        //将查询更新到RecentQueries表中
        RecentQueriesDAO.saveOrUpdateQuery(userId, keyword);

        return qa; 
    }

}
