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

public class LoginFrame extends baseFrame {
    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenWidth = screenSize.width; //屏幕宽度
    int screenHeight = screenSize.height; //屏幕高度

    JPanel mainpanel = new JPanel(new CardLayout());
    JPanel loginpanel = new JPanel();
    JPanel registerpanel = new JPanel();

    JLabel userLabel,nullfailedLabel,passwordLabel,passwordFailLabel,idFailLabel,nameLabel,setPasswordLabel  = new JLabel();
    JTextField userText,nameText,setPasswordText = new JTextField();
    JPasswordField passwordText = new JPasswordField();
    JButton loginButton,toRegisterButton,cancelButton,registerButton = new JButton();
    JCheckBox checkBox =new JCheckBox();

    public LoginFrame() {
        super("Login Frame");
    }
    @Override
    protected void initializeComponents() {
        setLocation((screenWidth - 400) / 2, (screenHeight - 250) / 2);
        mainpanel.add(loginpanel, "login");
        mainpanel.add(registerpanel, "Register");
        add(mainpanel);
        setVisible(true);
        loginpanel.setLayout(null);
        registerpanel.setLayout(null);
    }
    @Override
    protected void layoutComponents() {
        userLabel = addLabel("ID:",10,40,80,25);
        passwordLabel = addLabel("Password:",10,80,80,25);
        nullfailedLabel = addLabel("Failed login, ID or password is incorrect.",95,170,300,25);
        passwordFailLabel = addLabel("Failed login, password is necessary.",95,170,300,25);
        idFailLabel = addLabel("Failed login, ID is necessary.",95,170,300,25);
        nullfailedLabel.setVisible(false);
        passwordFailLabel.setVisible(false);
        idFailLabel.setVisible(false);
        userText = addTextField(20,100,40,240,25);
        passwordText = addPasswordField(20,100,80,240,25);
        loginButton = addButton("Login",100,120,100,40);
        toRegisterButton = addButton("Register",240,120,100,40);

        nameLabel = addLabel("Real Name",10,40,80,25);
        nameText = addTextField(20,110,40,240,25);
        setPasswordLabel = addLabel("Set Password:",10,90,100,25);
        setPasswordText = addPasswordField(20,110,90,240,25);
        registerButton = addButton("Register",50,160,140,40);
        cancelButton = addButton("Back",210,160,140,40);
        checkBox = new JCheckBox("Is Student");
        checkBox.setBounds(30, 130, 120, 25);
        registerpanel.add(checkBox);

        toRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToPanel("Register", "Register");
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginButtonComponents(loginpanel);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToPanel("login", "Login");
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerButtonComponents(registerpanel);
            }
        });
        addComponent(loginpanel,userLabel,passwordLabel,nullfailedLabel,passwordFailLabel,idFailLabel,userText,passwordText,loginButton,toRegisterButton);
        addComponent(registerpanel,nameLabel,nameText,setPasswordText,setPasswordLabel,registerButton,cancelButton,checkBox);
    }
    public void LoginButtonComponents(JPanel loginpanel) {
                clearErrorLabels(loginpanel,nullfailedLabel,passwordLabel,idFailLabel); // 清除之前的错误提示
                String userIdText = userText.getText().trim();
                if (userIdText.isEmpty()) {
                    idFailLabel.setVisible(true);
                    loginpanel.validate();
                    loginpanel.repaint();
                    return;
                }
                char[] passwordChars = passwordText.getPassword();
                String password = new String(passwordChars); // 获取密码
                Arrays.fill(passwordChars, ' '); // 清除字符数组内容，增强安全性
                if (password.isEmpty()) {
                    passwordLabel.setVisible(true);
                    loginpanel.validate();
                    loginpanel.repaint();
                    return;
                }
                try {
                    int userId = Integer.parseInt(userIdText);
                    User user = UserDAO.loginUser(userId, password);
                    if (user != null) {
                        boolean right = user.isStudent();
                        dispose(); // 关闭登录窗口
                        if (right) {
                            new StudentFrame(user);
                        } else {
                           LecturerFrame teacher =  new LecturerFrame(user);
                        }
                    } else {
                        nullfailedLabel.setVisible(true);
                        loginpanel.validate();
                        loginpanel.repaint();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "请输入有效的用户ID", "输入错误", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "登录过程中出现问题，请稍后重试。", "系统错误", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }

    }

    public void registerButtonComponents(JPanel registerpanel) {
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
                    registerSuccessFrame(id);
                } else {
                    JOptionPane.showMessageDialog(null, "注册失败", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
    }

    public void registerSuccessFrame(int id) {
        JOptionPane.showMessageDialog(null, "您的用户ID是 " + id + ".", "注册成功", JOptionPane.INFORMATION_MESSAGE);
        switchToPanel("login", "Login");

        JTextField loginIDField = (JTextField) Arrays.stream(loginpanel.getComponents())
                .filter(c -> c instanceof JTextField).findFirst().orElse(null);
        if (loginIDField != null) {
            loginIDField.setText(String.valueOf(id)); // 预填注册的ID
        }
    }

    private void switchToPanel(String panelName, String title) {
        CardLayout cl = (CardLayout) (mainpanel.getLayout());
        cl.show(mainpanel, panelName);
        setTitle(title);
    }
}
