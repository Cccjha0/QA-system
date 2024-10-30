package GUI;

import backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import backend.UserDAO;


public class ResetPasswordFrame extends baseFrame {
JFrame resetFrame;
    public ResetPasswordFrame(User user) {
        super(user);
        Frametitle="Reset Password";
    }

    @Override
    protected void layoutComponents() {
        JPanel resetPanel = new JPanel();
        resetPanel.setLayout(null);
        add(resetPanel);
        JLabel passwordLabel = addLabel("New Password:",10,15,150,25);
        JPasswordField passwordText = addPasswordField(20,60,45,240,25);
        JButton resetButton = addButton("Reset",135,85,80,25);
        JLabel failedLabel = addLabel("Failed Reset",95,170,140,25);
        failedLabel.setVisible(false);

        resetButton.addActionListener(e -> {
            clearErrorLabels(resetPanel,failedLabel); // 清除之前的错误提示
            char[] passwordChars = passwordText.getPassword();
            String password = new String(passwordChars); // 获取密码
            Arrays.fill(passwordChars, ' '); // 清除字符数组内容，增强安全性
            boolean successRest = UserDAO.updatePassword(user.getId(),password);
            if (successRest){
                JOptionPane.showMessageDialog(null, "New Password takes effect","Success Reset", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }else {
                resetPanel.add(failedLabel);
                failedLabel.setVisible(true);
                resetPanel.validate();
                resetPanel.repaint();
            }
        });
    }

    @Override
    protected void initializeComponents() {
        setSize(350,200);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation((screenSize.width - 350) / 2, (screenSize.height - 200) / 2);
        setVisible(true);
    }
    }

//    public static void main(String[] args) {
//        new ResetPasswordFrame(new User("Ponder","123",false));
//    }

