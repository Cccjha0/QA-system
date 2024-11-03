/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

/**
 * User class represents a user in the Q/A system. It contains fields for user information such as
 * ID, name, password, and role (student or lecturer).
 * Provides methods to access and modify these fields.
 *
 * @author 陈炯昊
 */
public class User {

    private int id; // Unique identifier for the user
    private String name; // Name of the user
    private String password; // Password for the user account
    private boolean isStudent; // Role of the user, true if the user is a student, false if a lecturer

    /**
     * Constructor to initialize a User object with a name, password, and role.
     *
     * @param name The name of the user
     * @param password The password for the user account
     * @param isStudent True if the user is a student, false if a lecturer
     */
    public User(String name, String password, boolean isStudent) {
        this.name = name;
        this.password = password;
        this.isStudent = isStudent;
    }

    /**
     * Gets the unique identifier for this user.
     *
     * @return The ID of the user
     */
    public int getId() {
        return id;
    }
    
    /**
     * Sets the unique identifier for this user.
     *
     * @param id The ID to set for this user
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the user.
     *
     * @return The name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Checks if the user is a student.
     *
     * @return True if the user is a student, false if a lecturer
     */
    public boolean isStudent() {
        return isStudent;
    }
}
