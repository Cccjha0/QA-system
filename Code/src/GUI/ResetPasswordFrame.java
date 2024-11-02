package GUI;

import backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import backend.UserDAO;


public class ResetPasswordFrame extends QAFrame {
    JLabel passwordLabel = new  JLabel("New Password:");
    JPasswordField passwordText = new JPasswordField(20);
    JButton resetButton = new JButton("Reset");

    public ResetPasswordFrame(User user) {
        this.user = user;
        Frametitle="Reset Password";
        FrameComponents();
    }

    @Override
    void FrameComponents() {
        setTitle(Frametitle);
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation((screenSize.width - 350) / 2, (screenSize.height - 200) / 2);

        ResetComponents(user);

        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
    public void ResetComponents(User user){
        JPanel resetPanel = new JPanel();
        resetPanel.setLayout(null);
        add(resetPanel);
        passwordLabel.setBounds(10,15,150,25);
        passwordText.setBounds(60,65,240,25);
        resetButton.setBounds(135,125,80,25);
        resetPanel.add(passwordLabel);
        resetPanel.add(passwordText);
        resetPanel.add(resetButton);
        resetButton.addActionListener(e -> {
            char[] passwordChars = passwordText.getPassword();
            String password = new String(passwordChars); // 获取密码
            Arrays.fill(passwordChars, ' '); // 清除字符数组内容，增强安全性
            boolean successRest = UserDAO.updatePassword(user.getId(),password);
            if (successRest){
                JOptionPane.showMessageDialog(null, "Your password has been changed", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }else {
                JOptionPane.showMessageDialog(null, "Failed to change the password", "Fail", JOptionPane.INFORMATION_MESSAGE);
            }
        });

    }
}
