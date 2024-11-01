package GUI;

import backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import backend.UserDAO;


public class ResetPasswordFrame extends QAFrame {
JFrame resetFrame;
    public ResetPasswordFrame(User user) {
        this.user = user;
        Frametitle="Reset Password";
        FrameComponents();
    }

    @Override
    void FrameComponents() {
        resetFrame = new JFrame(Frametitle);
        resetFrame.setSize(350, 200);
        resetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        resetFrame.setLocation(400,250);
        ResetComponents(resetFrame,user);
        resetFrame.setVisible(true);
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
                int delay = 2000;
                Timer timer = new Timer(delay, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        resetFrame.dispose(); // 关闭窗口
                    }
                });
                timer.setRepeats(false); // 设置定时器只触发一次
                timer.start();
            }else {
                resetPanel.add(failedLabel);
                failedLabel.setVisible(true);
                resetPanel.validate();
                resetPanel.repaint();
            }
        });

    }
}
