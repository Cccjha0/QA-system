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
public class QAframe {
    public static void main(String[] args) {
        QAframe.QAComponents();
    }
    public static void QAComponents() {
        JFrame QAframe = new JFrame("QA_Syetem");
        QAframe.setSize(1000, 700);
        QAframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width; //屏幕宽度
        int screenHeight = screenSize.height; //屏幕高度
        QAframe.setLocation((screenWidth-1000)/2, (screenHeight-700)/2);

        JPanel QApanel = new JPanel();
        QAframe.add(QApanel);
        QApanelComponents(QApanel);
        QAframe.setVisible(true);
    }

    private static void QApanelComponents(JPanel qapanel) {
        qapanel.setLayout(null);

        JLabel userLabel = new JLabel("User name");
        userLabel.setBounds(10,10,80,25);
        qapanel.add(userLabel);

        JTextField questiomText = new JTextField(20);
        questiomText.setBounds(190,50,600,30);
        qapanel.add(questiomText);

        JButton queryButton = new JButton("Query");
        queryButton.setBounds(800, 48, 80, 35);
        qapanel.add(queryButton);
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea jTextArea = new JTextArea(30,80);
                jTextArea.setText("答案：略");
                JScrollPane jScrollPane = new JScrollPane(jTextArea);
                jScrollPane.setBounds(190,130,600,450);
                qapanel.add(jScrollPane);

                qapanel.validate();
            }
        });

        JButton inputButton = new JButton("Input");
        inputButton.setBounds(890, 48, 80, 35);
        qapanel.add(inputButton);
        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JTextArea jTextArea = new JTextArea(30,80);
                jTextArea.setText("请输入答案");
                JScrollPane jScrollPane = new JScrollPane(jTextArea);
                jScrollPane.setBounds(190,130,600,450);
                qapanel.add(jScrollPane);

                JButton inputButton2 = new JButton("Input");
                inputButton2.setBounds(800, 130, 80, 35);
                qapanel.add(inputButton2);

                qapanel.validate();
            }
        });



    }
}
