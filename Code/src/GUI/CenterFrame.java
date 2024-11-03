package GUI;

import backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Singleton frame for the user's personal center, displaying user details and allowing password reset.
 */
public class CenterFrame extends JFrame {

    private User user; // User instance associated with this frame
    private static CenterFrame instance = null; // Singleton instance of CenterFrame

    // GUI components
    private JLabel usernameLabel = new JLabel();
    private JLabel userLabel = new JLabel("User:");
    private JLabel idLabel = new JLabel("ID:");
    private JLabel idValueLabel = new JLabel();
    private JButton resetButton = new JButton("Reset Password");

    /**
     * Private constructor to initialize the frame with the user's information.
     * @param user The User instance with details to display.
     */
    private CenterFrame(User user) {
        this.user = user;
        initializeFrame();        // Set up frame properties
        initializeComponents();   // Initialize component properties
        addComponents();          // Add components to the frame
        setComponentProperties(); // Set bounds and positioning
        setVisible(true);         // Make the frame visible
    }

    /**
     * Returns the singleton instance of CenterFrame, creating it if necessary.
     * @param user The User instance with details to display.
     * @return The singleton instance of CenterFrame.
     */
    public static CenterFrame getInstance(User user) {
        if (instance == null) {
            instance = new CenterFrame(user);
            instance.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    instance = null; // Reset instance to null when window is closed
                }
            });
        }
        return instance;
    }

    /**
     * Initializes the frame properties, such as title, size, and location.
     */
    private void initializeFrame() {
        setTitle("Personal Center");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame, not the entire app
        setResizable(false); // Prevent resizing

        // Center the frame on the screen
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
    }

    /**
     * Sets the initial values of the components based on the user's information.
     */
    private void initializeComponents() {
        usernameLabel.setText(user.getName()); // Display user name
        idValueLabel.setText(String.valueOf(user.getId())); // Display user ID

        // Set up reset button action to open ResetPasswordFrame
        resetButton.addActionListener(e -> {
            dispose(); // Close this frame
            new ResetPasswordFrame(user); // Open password reset frame
        });
    }

    /**
     * Adds components to the frame's content panel.
     */
    private void addComponents() {
        JPanel centerPanel = new JPanel(null); // Use null layout for custom positioning
        add(centerPanel); // Add panel to frame

        // Add components to panel
        centerPanel.add(userLabel);
        centerPanel.add(usernameLabel);
        centerPanel.add(idLabel);
        centerPanel.add(idValueLabel);
        centerPanel.add(resetButton);
    }

    /**
     * Sets the bounds and layout properties of the components within the frame.
     */
    private void setComponentProperties() {
        userLabel.setBounds(50, 20, 80, 30);
        usernameLabel.setBounds(110, 20, 200, 30);
        idLabel.setBounds(50, 70, 80, 30);
        idValueLabel.setBounds(110, 70, 200, 30);
        resetButton.setBounds(80, 120, 140, 30);
    }
}
