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
public class UserDAO {

    /**
     * return UserID
     **/
    public int registerUser(User user) {
        int userId = 0;
        String query = "INSERT INTO Users (password, name, isStudent) VALUES (?, ?, ?)";
        try ( Connection connection = DatabaseConnection.getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getName());
            statement.setBoolean(3, user.isStudent());
            statement.executeUpdate();
            
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    /**
     *
     * ruturn 0 老师，1 学生
     *
     */
    public boolean loginUser(int id, String password) {
        String query = "SELECT name FROM Users WHERE id=? AND password=?";
        try ( Connection connection = DatabaseConnection.getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
