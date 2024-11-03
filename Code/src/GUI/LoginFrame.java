package GUI;

import backend.*;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * LoginFrame class, extending BaseFrame and implementing the Loginable interface.
 * This class provides a login and registration interface for users.
 */
public class LoginFrame extends BaseFrame implements Loginable {

    // Main JFrame for the login window
    private final JFrame loginframe = new JFrame(Frametitle);
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    // Panels for the main content, login view, and registration view
    private final JPanel mainpanel = new JPanel(new CardLayout());
    private final JPanel loginpanel = new JPanel();
    private final JPanel registerpanel = new JPanel();

    /**
     * Constructor for LoginFrame that initializes and displays the login window.
     */
    public LoginFrame() {
        super("Login");
        initializeFrameComponents();
        loginframe.setVisible(true);
    }

    /**
     * Initializes components for the frame.
     */
    @Override
    void initializeFrameComponents() {
        initializeFrame();
        initializeMainPanel();
    }

    /**
     * Sets up the frame properties, size, and adds the main panel.
     */
    private void initializeFrame() {
        loginframe.setSize(400, 250);
        loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginframe.setResizable(false);
        loginframe.setLocation((screenSize.width - 400) / 2, (screenSize.height - 250) / 2);
        loginframe.add(mainpanel);
    }

    /**
     * Sets up the main panel with login and registration views.
     */
    private void initializeMainPanel() {
        mainpanel.add(loginpanel, "Login");      // Adds login panel to main panel
        mainpanel.add(registerpanel, "Register"); // Adds register panel to main panel
        initializeLoginPanel();                   // Initializes login panel components
        initializeRegisterPanel();                // Initializes register panel components
    }

    /**
     * Sets up the components for the login panel.
     */
    private void initializeLoginPanel() {
        loginpanel.setLayout(null);

        // Labels and input fields for user ID and password
        JLabel userLabel = addLabel("ID:", 10, 40, 80, 25, loginpanel);
        JTextField userText = addTextField(20, 100, 40, 240, 25, loginpanel);
        JLabel passwordLabel = addLabel("Password:", 10, 80, 80, 25, loginpanel);
        JPasswordField passwordText = addPasswordField(20, 100, 80, 240, 25, loginpanel);
        
        // Error label to display login errors
        JLabel errorLabel = addLabel("", 95, 170, 300, 25, loginpanel);
        errorLabel.setForeground(Color.RED);

        // Login and Register buttons
        JButton loginButton = addButton("Login", 100, 120, 100, 40, loginpanel);
        JButton registerButton = addButton("Register", 240, 120, 100, 40, loginpanel);

        // Action listeners for buttons
        loginButton.addActionListener(e -> handleLogin(userText, passwordText, errorLabel));
        registerButton.addActionListener(e -> switchToPanel("Register", "Register"));
    }

    /**
     * Handles the login action, validating user input and checking credentials.
     */
    private void handleLogin(JTextField userText, JPasswordField passwordText, JLabel errorLabel) {
        clearErrorLabel(errorLabel);
        String userIdText = userText.getText().trim();
        String password = new String(passwordText.getPassword()).trim();

        // Check if the user ID field is empty
        if (userIdText.isEmpty()) {
            showError(errorLabel, "Failed login, ID is necessary.");
            return;
        }

        // Check if the password field is empty
        if (password.isEmpty()) {
            showError(errorLabel, "Failed login, password is necessary.");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdText);  // Parse user ID
            if (!login(userId, password)) {
                showError(errorLabel, "Login failed. Check your credentials.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid user ID", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error occurred during login. Please try again later.", "System Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Sets up the components for the registration panel.
     */
    private void initializeRegisterPanel() {
        registerpanel.setLayout(null);

        // Labels and input fields for name and password
        JLabel userLabel = addLabel("Real Name", 10, 40, 80, 25, registerpanel);
        JTextField userText = addTextField(20, 110, 40, 240, 25, registerpanel);
        JLabel passwordLabel = addLabel("Set Password:", 10, 90, 100, 25, registerpanel);
        JPasswordField passwordText = addPasswordField(20, 110, 90, 240, 25, registerpanel);
        
        // Checkbox for selecting student status
        JCheckBox checkBox = new JCheckBox("Is Student");
        checkBox.setBounds(30, 130, 120, 25);
        registerpanel.add(checkBox);

        // Register and Back buttons
        JButton registerButton = addButton("Register", 50, 160, 140, 40, registerpanel);
        JButton cancelButton = addButton("Back", 210, 160, 140, 40, registerpanel);

        // Action listeners for buttons
        registerButton.addActionListener(e -> handleRegister(userText, passwordText, checkBox));
        cancelButton.addActionListener(e -> switchToPanel("Login", "Login"));
    }

    /**
     * Handles the registration action, creating a new user if valid input is provided.
     */
    private void handleRegister(JTextField userText, JPasswordField passwordText, JCheckBox checkBox) {
        String username = userText.getText().trim();
        String password = new String(passwordText.getPassword()).trim();

        // Check if the username field is empty
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter your name", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if the password field is empty
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a password", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Register the new user and get the user ID
        User newUser = new User(username, password, checkBox.isSelected());
        int id = backend.UserDAO.registerUser(newUser);

        // Display success or error message based on registration result
        if (id != 0) {
            userText.setText("");
            passwordText.setText("");
            registerSuccessFrame(id);
        } else {
            JOptionPane.showMessageDialog(null, "Registration failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays a success message with user ID and switches back to the login panel.
     */
    private void registerSuccessFrame(int id) {
        JOptionPane.showMessageDialog(null, "Your user ID is " + id + ".", "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
        switchToPanel("Login", "Login");
        prefillLoginId(id);
    }

    /**
     * Prefills the login ID field with the newly registered user ID.
     */
    private void prefillLoginId(int id) {
        JTextField loginIDField = (JTextField) Arrays.stream(loginpanel.getComponents())
                .filter(c -> c instanceof JTextField).findFirst().orElse(null);
        if (loginIDField != null) {
            loginIDField.setText(String.valueOf(id));
        }
    }

    /**
     * Shows an error message in the specified label.
     */
    private void showError(JLabel label, String message) {
        label.setText(message);
        label.setVisible(true);
    }

    /**
     * Clears the error message in the specified label.
     */
    private void clearErrorLabel(JLabel label) {
        label.setText("");
        label.setVisible(false);
    }

    /**
     * Switches between the login and registration panels.
     */
    private void switchToPanel(String panelName, String title) {
        CardLayout cl = (CardLayout) (mainpanel.getLayout());
        cl.show(mainpanel, panelName);
        loginframe.setTitle(title);
    }

    /**
     * Logs in the user based on user ID and password.
     */
    @Override
    public boolean login(int userId, String password) {
        User user = UserDAO.loginUser(userId, password);
        if (user != null) {
            loginframe.dispose();
            if (user.isStudent()) {
                new StudentFrame(user); // Open student frame if user is a student
            } else {
                new LecturerFrame(user); // Open lecturer frame if user is a lecturer
            }
            return true;
        }
        return false;
    }
}
