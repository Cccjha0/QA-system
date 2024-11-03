/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.sql.*;

/**
 * UserDAO class provides methods to interact with the Users table in the database.
 * It allows registering a new user, logging in, and updating the user's password.
 *
 * @author 陈炯昊
 */
public class UserDAO {

    /**
     * Registers a new user in the database.
     *
     * @param user The User object containing the user's information.
     * @return The generated ID of the newly registered user, or 0 if registration failed.
     */
    public static int registerUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        int userId = 0;
        String query = "INSERT INTO Users (password, name, isStudent) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }

            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getPassword());
                statement.setString(2, user.getName());
                statement.setBoolean(3, user.isStudent());
                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                    user.setId(userId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }

    /**
     * Logs in a user by verifying their ID and password.
     *
     * @param id The ID of the user attempting to log in.
     * @param password The password of the user.
     * @return The User object if login is successful, or null if login fails.
     */
    public static User loginUser(int id, String password) {
        User user = null;
        String query = "SELECT name, isStudent FROM Users WHERE id=? AND password=?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
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
    
    /**
     * Updates the user's password.
     *
     * @param userId The ID of the user.
     * @param newPassword The new password to set.
     * @return true if the password was updated successfully, false otherwise.
     */
    public static boolean updatePassword(int userId, String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("New password cannot be null or empty");
        }

        String query = "UPDATE Users SET password = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, newPassword);
            statement.setInt(2, userId);
            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
