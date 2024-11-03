package GUI;

import backend.*;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class LoginFrame extends BaseFrame implements Loginable {

    private final JFrame loginframe = new JFrame(Frametitle);
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final JPanel mainpanel = new JPanel(new CardLayout());
    private final JPanel loginpanel = new JPanel();
    private final JPanel registerpanel = new JPanel();

    public LoginFrame() {
        super("Login");
        initializeFrameComponents();
        loginframe.setVisible(true);
    }

    @Override
    void initializeFrameComponents() {
        initializeFrame();
        initializeMainPanel();
    }

    private void initializeFrame() {
        loginframe.setSize(400, 250);
        loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginframe.setResizable(false);
        loginframe.setLocation((screenSize.width - 400) / 2, (screenSize.height - 250) / 2);
        loginframe.add(mainpanel);
    }

    private void initializeMainPanel() {
        mainpanel.add(loginpanel, "Login");
        mainpanel.add(registerpanel, "Register");
        initializeLoginPanel();
        initializeRegisterPanel();
    }

    private void initializeLoginPanel() {
        loginpanel.setLayout(null);

        JLabel userLabel = addLabel("ID:", 10, 40, 80, 25, loginpanel);
        JTextField userText = addTextField(20, 100, 40, 240, 25, loginpanel);
        JLabel passwordLabel = addLabel("Password:", 10, 80, 80, 25, loginpanel);
        JPasswordField passwordText = addPasswordField(20, 100, 80, 240, 25, loginpanel);
        JLabel errorLabel = addLabel("", 95, 170, 300, 25, loginpanel);
        errorLabel.setForeground(Color.RED);

        JButton loginButton = addButton("Login", 100, 120, 100, 40, loginpanel);
        JButton registerButton = addButton("Register", 240, 120, 100, 40, loginpanel);

        loginButton.addActionListener(e -> handleLogin(userText, passwordText, errorLabel));
        registerButton.addActionListener(e -> switchToPanel("Register", "Register"));
    }

    private void handleLogin(JTextField userText, JPasswordField passwordText, JLabel errorLabel) {
        clearErrorLabel(errorLabel);
        String userIdText = userText.getText().trim();
        String password = new String(passwordText.getPassword()).trim();

        if (userIdText.isEmpty()) {
            showError(errorLabel, "Failed login, ID is necessary.");
            return;
        }

        if (password.isEmpty()) {
            showError(errorLabel, "Failed login, password is necessary.");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdText);
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

    private void initializeRegisterPanel() {
        registerpanel.setLayout(null);

        JLabel userLabel = addLabel("Real Name", 10, 40, 80, 25, registerpanel);
        JTextField userText = addTextField(20, 110, 40, 240, 25, registerpanel);
        JLabel passwordLabel = addLabel("Set Password:", 10, 90, 100, 25, registerpanel);
        JPasswordField passwordText = addPasswordField(20, 110, 90, 240, 25, registerpanel);
        JCheckBox checkBox = new JCheckBox("Is Student");
        checkBox.setBounds(30, 130, 120, 25);
        registerpanel.add(checkBox);

        JButton registerButton = addButton("Register", 50, 160, 140, 40, registerpanel);
        JButton cancelButton = addButton("Back", 210, 160, 140, 40, registerpanel);

        registerButton.addActionListener(e -> handleRegister(userText, passwordText, checkBox));
        cancelButton.addActionListener(e -> switchToPanel("Login", "Login"));
    }

    private void handleRegister(JTextField userText, JPasswordField passwordText, JCheckBox checkBox) {
        String username = userText.getText().trim();
        String password = new String(passwordText.getPassword()).trim();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter your name", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a password", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User newUser = new User(username, password, checkBox.isSelected());
        int id = backend.UserDAO.registerUser(newUser);

        if (id != 0) {
            userText.setText("");
            passwordText.setText("");
            registerSuccessFrame(id);
        } else {
            JOptionPane.showMessageDialog(null, "Registration failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registerSuccessFrame(int id) {
        JOptionPane.showMessageDialog(null, "Your user ID is " + id + ".", "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
        switchToPanel("Login", "Login");
        prefillLoginId(id);
    }

    private void prefillLoginId(int id) {
        JTextField loginIDField = (JTextField) Arrays.stream(loginpanel.getComponents())
                .filter(c -> c instanceof JTextField).findFirst().orElse(null);
        if (loginIDField != null) {
            loginIDField.setText(String.valueOf(id));
        }
    }

    private void showError(JLabel label, String message) {
        label.setText(message);
        label.setVisible(true);
    }

    private void clearErrorLabel(JLabel label) {
        label.setText("");
        label.setVisible(false);
    }

    private void switchToPanel(String panelName, String title) {
        CardLayout cl = (CardLayout) (mainpanel.getLayout());
        cl.show(mainpanel, panelName);
        loginframe.setTitle(title);
    }

    @Override
    public boolean login(int userId, String password) {
        User user = UserDAO.loginUser(userId, password);
        if (user != null) {
            loginframe.dispose();
            if (user.isStudent()) {
                new StudentFrame(user);
            } else {
                new LecturerFrame(user);
            }
            return true;
        }
        return false;
    }
}
