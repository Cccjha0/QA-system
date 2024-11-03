package GUI;

import backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CenterFrame extends JFrame {

    private User user;
    private static CenterFrame instance = null; // Singleton instance
    private JLabel usernameLabel = new JLabel();
    private JLabel userLabel = new JLabel("User:");
    private JLabel idLabel = new JLabel("ID:");
    private JLabel idValueLabel = new JLabel();
    private JButton resetButton = new JButton("Reset Password");

    private CenterFrame(User user) {
        this.user = user;
        initializeFrame();
        initializeComponents();
        addComponents();
        setComponentProperties();
        setVisible(true);
    }

    public static CenterFrame getInstance(User user) {
        if (instance == null) {
            instance = new CenterFrame(user);
            instance.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    instance = null; // Set instance to null when the window is closed
                }
            });
        }
        return instance;
    }

    private void initializeFrame() {
        setTitle("Personal Center");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
    }

    private void initializeComponents() {
        usernameLabel.setText(user.getName());
        idValueLabel.setText(String.valueOf(user.getId()));
        resetButton.addActionListener(e -> {
            dispose();
            new ResetPasswordFrame(user);
        });
    }

    private void addComponents() {
        JPanel centerPanel = new JPanel(null);
        add(centerPanel);

        centerPanel.add(userLabel);
        centerPanel.add(usernameLabel);
        centerPanel.add(idLabel);
        centerPanel.add(idValueLabel);
        centerPanel.add(resetButton);
    }

    private void setComponentProperties() {
        userLabel.setBounds(50, 20, 80, 30);
        usernameLabel.setBounds(110, 20, 200, 30);
        idLabel.setBounds(50, 70, 80, 30);
        idValueLabel.setBounds(110, 70, 200, 30);
        resetButton.setBounds(80, 120, 140, 30);
    }
}
