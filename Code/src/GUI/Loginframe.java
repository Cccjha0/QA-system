/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package GUI;

import backend.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class LoginFrame {

    JFrame loginframe = new JFrame("Login");

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenWidth = screenSize.width; //屏幕宽度
    int screenHeight = screenSize.height; //屏幕高度

    JPanel mainpanel = new JPanel(new CardLayout());
    JPanel loginpanel = new JPanel();
    JPanel registerpanel = new JPanel();
    
    JLabel failedLabel = new JLabel("Failed login, ID or password is incorrect.");
    JLabel passwordFailLabel = new JLabel("Failed login, password is necessary.");
    JLabel idFailLabel = new JLabel("Failed login, ID is necessary.");

    public LoginFrame() {
        loginframe.setSize(400, 250);
        loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginframe.setLocation((screenWidth - 400) / 2, (screenHeight - 250) / 2);

        mainpanel.add(loginpanel, "login");
        mainpanel.add(registerpanel, "Register");
        registerpanelComponents(registerpanel);
        loginComponents(loginpanel);

        loginframe.add(mainpanel);
        loginframe.setVisible(true);
    }

    private void loginComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("ID:");
        userLabel.setBounds(10, 40, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 40, 240, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 80, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 80, 240, 25);
        panel.add(passwordText);

        failedLabel.setBounds(95, 170, 300, 25);
        passwordFailLabel.setBounds(95, 170, 300, 25);
        idFailLabel.setBounds(95, 170, 300, 25);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 120, 100, 40);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearErrorLabels(panel); // 清除之前的错误提示
                
                String userIdText = userText.getText().trim();
                if (userIdText.isEmpty()) {
                    panel.add(idFailLabel);
                    panel.validate();
                    panel.repaint();
                    return;
                }

                char[] passwordChars = passwordText.getPassword();
                String password = new String(passwordChars); // 获取密码
                Arrays.fill(passwordChars, ' '); // 清除字符数组内容，增强安全性

                if (password.isEmpty()) {
                    panel.add(passwordFailLabel);
                    panel.validate();
                    panel.repaint();
                    return;
                }

                try {
                    int userId = Integer.parseInt(userIdText);
                    User user = backend.UserDAO.loginUser(userId, password);

                    if (user != null) {
                        boolean right = user.isStudent();
                        loginframe.dispose(); // 关闭登录窗口

                        if (right) {
                            new StudentFrame(user);
                        } else {
                            new LecturerFrame(user);
                        }
                    } else {
                        panel.add(failedLabel);
                        panel.validate();
                        panel.repaint();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "请输入有效的用户ID", "输入错误", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "登录过程中出现问题，请稍后重试。", "系统错误", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(240, 120, 100, 40);
        panel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToPanel("Register", "Register");
            }
        });
    }

    public void registerpanelComponents(JPanel registerpanel) {
        registerpanel.setLayout(null);

        JLabel userLabel = new JLabel("Real Name:");
        userLabel.setBounds(10, 40, 80, 25);
        registerpanel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(110, 40, 240, 25);
        registerpanel.add(userText);

        JLabel passwordLabel = new JLabel("Set Password:");
        passwordLabel.setBounds(10, 90, 100, 25);
        registerpanel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(110, 90, 240, 25);
        registerpanel.add(passwordText);

        JCheckBox checkBox = new JCheckBox("Is Student");
        checkBox.setBounds(30, 130, 120, 25);
        registerpanel.add(checkBox);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(50, 160, 140, 40);
        registerpanel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText().trim();
                String password = new String(passwordText.getPassword());

                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "请输入用户名", "输入错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "请输入密码", "输入错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                User newUser = new User(username, password, checkBox.isSelected());
                int id = backend.UserDAO.registerUser(newUser);
                
                if (id != 0) {
                    Successframe(id);
                } else {
                    JOptionPane.showMessageDialog(null, "注册失败", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        JButton cancelButton = new JButton("Back");
        cancelButton.setBounds(210, 160, 140, 40);
        registerpanel.add(cancelButton);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToPanel("login", "Login");
            }
        });
    }

    public void Successframe(int id) {
        JOptionPane.showMessageDialog(null, "您的用户ID是 " + id + ".", "注册成功", JOptionPane.INFORMATION_MESSAGE);

        switchToPanel("login", "Login");

        JTextField loginIDField = (JTextField) Arrays.stream(loginpanel.getComponents())
                .filter(c -> c instanceof JTextField).findFirst().orElse(null);
        if (loginIDField != null) {
            loginIDField.setText(String.valueOf(id)); // 预填注册的ID
        }
    }

    private void clearErrorLabels(JPanel panel) {
        panel.remove(failedLabel);
        panel.remove(passwordFailLabel);
        panel.remove(idFailLabel);
    }

    private void switchToPanel(String panelName, String title) {
        CardLayout cl = (CardLayout) (mainpanel.getLayout());
        cl.show(mainpanel, panelName);
        loginframe.setTitle(title);
    }
    
}