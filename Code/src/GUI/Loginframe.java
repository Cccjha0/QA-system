/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author peter
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Loginframe {

    public static void main(String[] args) {
        JFrame loginframe = new JFrame("Login");
        loginframe.setSize(400, 250);
        loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width; //屏幕宽度
        int screenHeight = screenSize.height; //屏幕高度
        loginframe.setLocation((screenWidth-400)/2, (screenHeight-250)/2);

        JPanel loginpanel = new JPanel();
        loginframe.add(loginpanel);
        loginComponents(loginpanel);
        loginframe.setVisible(true);
    }

    private static void loginComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("ID:");
        userLabel.setBounds(10,40,80,25);
        panel.add(userLabel);
        //输入用户名的文本框
        JTextField userText = new JTextField(20);
        userText.setBounds(100,40,240,25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10,80,80,25);
        panel.add(passwordLabel);
        //输入密码的文本框
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,80,240,25);
        panel.add(passwordText);

        // 登录按钮
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 140, 80, 40);
        panel.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //测试 user：ponder  password：12345
                if(userText.getText().equals("ponder")&& new String(passwordText.getPassword()).equals("12345")) {
                    //关闭loginframe，打开QAFrame，当关闭QAFrame时，再次打开loginframe

                    QAframe.QAComponents();
                } else {
                    JOptionPane.showConfirmDialog(null,"登陆失败");}
            }
        });

        //重置按钮
        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(220,140,80,40);
        panel.add(resetButton);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(userText!=null);
                userText.setText(null);
                if(passwordText!=null);
                passwordText.setText(null);
            }
        });
    }

}


