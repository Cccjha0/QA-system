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
     *
     */
    public static int registerUser(User user) {
        int userId = 0;
        String query = "INSERT INTO User (password, name, isStudent) VALUES (?, ?, ?)";
        try ( Connection connection = DatabaseConnection.getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getName());
            statement.setBoolean(3, user.isStudent());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
                user.setId(userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    /**
     *
     *
     * return null 失败
     *
     */
    public static User loginUser(int id, String password) {
        User user = null;
        String query = "SELECT name, isStudent FROM User WHERE id=? AND password=?";
        try ( Connection connection = DatabaseConnection.getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User(resultSet.getString(1), password, resultSet.getBoolean(2));
                user.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return user;
        }
        return user;
    }
}
