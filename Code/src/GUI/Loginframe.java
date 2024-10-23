/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author peter
 */
import backend.*;
import backend.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Loginframe {

    JFrame loginframe = new JFrame("Login");

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenWidth = screenSize.width; //屏幕宽度
    int screenHeight = screenSize.height; //屏幕高度

    JPanel mainpanel = new JPanel(new CardLayout());
    JPanel loginpanel = new JPanel();
    JPanel registerpanel = new JPanel();

    public Loginframe() {
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
        //输入ID的文本框
        JTextField userText = new JTextField(20);
        userText.setBounds(100, 40, 240, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 80, 80, 25);
        panel.add(passwordLabel);
        //输入密码的文本框
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 80, 240, 25);
        panel.add(passwordText);

        JLabel failedLabel = new JLabel("Failed login, ID or password is incorrect.");
        failedLabel.setBounds(95, 170, 300, 25);
        // 登录按钮
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 120, 100, 40);
        panel.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.remove(failedLabel);//删除之前的报错标签
                User user = backend.UserDAO.loginUser(Integer.parseInt(userText.getText()), passwordText.getPassword().toString());

                if (user != null) {
                    boolean right = user.isStudent();
                    if (right) {//是学生
                        loginframe.dispose();
                        QAframe qAframe = new QAframe(user);
                     
                    } else if (!right) {
                        loginframe.dispose();
                        QAframe qAframe = new QAframe(user);
                    }
                } else {
                    panel.add(failedLabel);
                    panel.validate();
                    panel.repaint();
                }

            }
        });
        //注册按钮
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(240, 120, 100, 40);
        panel.add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (mainpanel.getLayout());
                cl.show(mainpanel, "Register");
                loginframe.setTitle("Register");
            }
        });
    }

    public void registerpanelComponents(JPanel registerpanel) {
        registerpanel.setLayout(null);

        JLabel userLabel = new JLabel("Real Name:");
        userLabel.setBounds(10, 40, 80, 25);
        registerpanel.add(userLabel);
        //输入Name的文本框
        JTextField userText = new JTextField(20);
        userText.setBounds(110, 40, 240, 25);
        registerpanel.add(userText);

        JLabel passwordLabel = new JLabel("Set Password:");
        passwordLabel.setBounds(10, 90, 100, 25);
        registerpanel.add(passwordLabel);
        //输入密码的文本框
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(110, 90, 240, 25);
        registerpanel.add(passwordText);

        JCheckBox checkBox = new JCheckBox("Is Student");
        checkBox.setBounds(30, 130, 120, 25);
        checkBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {

                } else {
                }
            }
        });
        registerpanel.add(checkBox);

        // 注册按钮
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(50, 160, 140, 40);
        registerpanel.add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User newUser = new User(userText.getText(), passwordText.getPassword().toString(), checkBox.isSelected());
                //int id = backend.UserDAO.registerUser(newUser);
                int id = 1;
                Successframe(id);

            }
        });

        JButton CancelButton = new JButton("Back");
        CancelButton.setBounds(210, 160, 140, 40);
        CancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (mainpanel.getLayout());
                cl.show(mainpanel, "login");
                loginframe.setTitle("Login");
            }
        });
        registerpanel.add(CancelButton);
    }

    public void Successframe(int id) {
        JFrame successframe = new JFrame("Successful Register");

        JOptionPane.showMessageDialog(null, "Your ID is " + id + ".", "Successful Register", JOptionPane.INFORMATION_MESSAGE);

    }

    public static void main(String[] args) {
        new Loginframe();
    }

}
