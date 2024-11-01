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
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation((screenSize.width - 350) / 2, (screenSize.height - 200) / 2);

        ResetComponents(this,user);

        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
    public void ResetComponents(JFrame jFrame,User user){
        JPanel resetPanel = new JPanel();
        resetPanel.setLayout(null);
        jFrame.add(resetPanel);
        JLabel passwordLabel = addLabel("New Password:",10,15,150,25,resetPanel);
        JPasswordField passwordText = addPasswordField(20,60,45,240,25,resetPanel);
        JButton resetButton = addButton("Reset",135,85,80,25,resetPanel);
        JLabel failedLabel = addLabel("Failed Reset",95,170,140,25,resetPanel);
        failedLabel.setVisible(false);
        JLabel successLabel = addLabel("Reset Success",95,170,140,25,resetPanel);
        successLabel.setVisible(false);
        resetButton.addActionListener(e -> {
            clearErrorLabels(resetPanel,failedLabel,successLabel); // 清除之前的错误提示
            char[] passwordChars = passwordText.getPassword();
            String password = new String(passwordChars); // 获取密码
            Arrays.fill(passwordChars, ' '); // 清除字符数组内容，增强安全性
            boolean successRest = UserDAO.updatePassword(user.getId(),password);
            if (successRest){
                resetPanel.add(successLabel);
                successLabel.setVisible(true);
                resetPanel.validate();
                resetPanel.repaint();

            }else {
                resetPanel.add(failedLabel);
                failedLabel.setVisible(true);
                resetPanel.validate();
                resetPanel.repaint();
            }
        });

    }
}
