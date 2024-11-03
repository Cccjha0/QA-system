package GUI;

import backend.User;
import backend.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class ResetPasswordFrame extends BaseFrame {
    private String frameTitle = "Reset Password"; // Declare frameTitle
    private JLabel passwordLabel = new JLabel("New Password:");
    private JPasswordField passwordText = new JPasswordField(20);
    private JButton resetButton = new JButton("Reset");

    public ResetPasswordFrame(User user) {
        this.user = user;
        initializeFrameComponents();
    }

    @Override
    void initializeFrameComponents() {
        setTitle(frameTitle);
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);

        initializeResetComponents();

        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private void initializeResetComponents() {
        JPanel resetPanel = new JPanel(null);
        add(resetPanel);

        passwordLabel.setBounds(10, 15, 150, 25);
        passwordText.setBounds(60, 65, 240, 25);
        resetButton.setBounds(135, 125, 80, 25);

        resetPanel.add(passwordLabel);
        resetPanel.add(passwordText);
        resetPanel.add(resetButton);

        resetButton.addActionListener(e -> handlePasswordReset());
    }

    private void handlePasswordReset() {
        char[] passwordChars = passwordText.getPassword();
        String password = new String(passwordChars);
        Arrays.fill(passwordChars, ' '); // Clear character array for security

        boolean successReset = UserDAO.updatePassword(user.getId(), password);
        if (successReset) {
            JOptionPane.showMessageDialog(this, "Your password has been changed", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to change the password", "Failure", JOptionPane.ERROR_MESSAGE);
        }
    }
}
