package GUI;

import javax.swing.*;

/**
 * The Loginable interface defines a contract for classes that implement login functionality.
 * This interface can be used to standardize the login process across different components of
 * a GUI application.
 */
public interface Loginable {

    /**
     * Attempts to log in a user with the specified user ID and password.
     *
     * @param userId The ID of the user attempting to log in.
     * @param password The password provided by the user for authentication.
     * @return true if the login is successful; false otherwise.
     */
    boolean login(int userId, String password);
}
