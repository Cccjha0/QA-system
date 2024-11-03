package GUI;

import backend.User;
import backend.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

/**
 * ResetPasswordFrame class provides a graphical user interface for users to reset their password.
 * It prompts the user to enter a new password and handles the update action.
 */
public class ResetPasswordFrame extends BaseFrame {
    private String frameTitle = "Reset Password"; // Title of the frame
    private JLabel passwordLabel = new JLabel("New Password:"); // Label for password input
    private JPasswordField passwordText = new JPasswordField(20); // Field for entering new password
    private JButton resetButton = new JButton("Reset"); // Button to trigger password reset action

    /**
     * Constructor initializes the ResetPasswordFrame with the current user.
     * @param user The current user whose password is to be reset
     */
    public ResetPasswordFrame(User user) {
        this.user = user;
        initializeFrameComponents();
    }

    /**
     * Initializes the main frame properties and adds a window listener.
     */
    @Override
    void initializeFrameComponents() {
        setTitle(frameTitle);
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Centers the frame on the screen
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);

        initializeResetComponents();

        setVisible(true);

        // Adds a window listener to handle the frame's close operation
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    /**
     * Sets up components within the reset panel, including the password label, input field, and reset button.
     */
    private void initializeResetComponents() {
        JPanel resetPanel = new JPanel(null); // Panel with no layout manager for custom positioning
        add(resetPanel);

        // Set positions and sizes of components
        passwordLabel.setBounds(10, 15, 150, 25);
        passwordText.setBounds(60, 65, 240, 25);
        resetButton.setBounds(135, 125, 80, 25);

        // Add components to the panel
        resetPanel.add(passwordLabel);
        resetPanel.add(passwordText);
        resetPanel.add(resetButton);

        // Add action listener for the reset button
        resetButton.addActionListener(e -> handlePasswordReset());
    }

    /**
     * Handles the password reset action by updating the user's password in the database.
     * It retrieves the entered password, clears the input for security, and displays a success or failure message.
     */
    private void handlePasswordReset() {
        // Get password input as a character array for security
        char[] passwordChars = passwordText.getPassword();
        String password = new String(passwordChars);
        Arrays.fill(passwordChars, ' '); // Clear character array for security

        // Attempt to update the password in the database
        boolean successReset = UserDAO.updatePassword(user.getId(), password);
        if (successReset) {
            // Show success message if the password was changed successfully
            JOptionPane.showMessageDialog(this, "Your password has been changed", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            // Show failure message if the password change failed
            JOptionPane.showMessageDialog(this, "Failed to change the password", "Failure", JOptionPane.ERROR_MESSAGE);
        }
    }
}
