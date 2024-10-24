/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package GUI;

import backend.*;
import backend.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LecturerFrame {

    private static final Font font = new Font("Arial", Font.PLAIN, 20);
    private JFrame QAframe;
    private User user;
    
    public LecturerFrame(User user) {
        this.user = user;
        QAComponents();
    }

    private void QAComponents() {
        // 窗口设置
        QAframe = new JFrame("QA_System");
        QAframe.setSize(1000, 700);
        QAframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        QAframe.setLocation((screenSize.width - 1000) / 2, (screenSize.height - 700) / 2);

        // 面板设置
        JPanel QAPanel = new JPanel(new CardLayout());
        JPanel queryPanel = new JPanel();
        JPanel inputPanel = new JPanel();
        QAPanel.add(queryPanel, "query");
        QAPanel.add(inputPanel, "input");

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        JButton queryButton = new JButton("Query");
        JButton inputButton = new JButton("Input");

        Dimension buttonSize = new Dimension(100, 30);
        queryButton.setPreferredSize(buttonSize);
        inputButton.setPreferredSize(buttonSize);

        toolBar.add(queryButton);
        toolBar.add(inputButton);

        QAframe.add(toolBar, BorderLayout.NORTH);
        QApanelComponents(queryPanel);
        InputPanelComponents(inputPanel);

        QAframe.add(QAPanel);
        QAframe.setVisible(true);

        // 使用抽取的方法进行切换
        queryButton.addActionListener(e -> switchPanel(QAPanel, "query"));
        inputButton.addActionListener(e -> switchPanel(QAPanel, "input"));
    
    }

    private void switchPanel(JPanel panel, String name) {
        CardLayout cl = (CardLayout) panel.getLayout();
        cl.show(panel, name);
    }

    private void InputPanelComponents(JPanel inputPanel) {
        inputPanel.setLayout(null);
        JLabel userLabel = new JLabel(user.getName());
        userLabel.setBounds(10, 10, 200, 25);
        inputPanel.add(userLabel);

        JLabel qLabel = new JLabel("Input your question:");
        qLabel.setFont(font);
        qLabel.setBounds(150, 20, 200, 25);
        inputPanel.add(qLabel);

        JTextArea qArea = new JTextArea(5, 55);
        JScrollPane qjScrollPane = new JScrollPane(qArea);
        qjScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        qjScrollPane.setBounds(150, 55, 700, 100);
        inputPanel.add(qjScrollPane);
        qArea.setLineWrap(true);
        qArea.setWrapStyleWord(true);

        JLabel aLabel = new JLabel("Input your answer:");
        aLabel.setFont(font);
        aLabel.setBounds(150, 170, 200, 25);
        inputPanel.add(aLabel);

        JTextArea aArea = new JTextArea(20, 55);
        JScrollPane ajScrollPane = new JScrollPane(aArea);
        ajScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        ajScrollPane.setBounds(150, 215, 700, 330);
        inputPanel.add(ajScrollPane);
        aArea.setLineWrap(true);
        aArea.setWrapStyleWord(true);

        JLabel inputqLabel = new JLabel("Please input question");
        inputqLabel.setFont(font);
        JLabel inputaLabel = new JLabel("Please input answer");
        inputaLabel.setFont(font);
        JLabel successfulLabel = new JLabel("Input Successful");
        successfulLabel.setFont(font);
        JLabel failLabel = new JLabel("Input Failed");
        failLabel.setFont(font);

        JButton inputButton = new JButton("Input");
        inputButton.setBounds(770, 555, 80, 35);
        inputPanel.add(inputButton);
        inputButton.addActionListener(e -> {
            // 清除所有标签
            clearErrorLabels(inputPanel, inputqLabel, inputaLabel, successfulLabel, failLabel);
            
            if (qArea.getText().trim().isEmpty()) {
                showLabel(inputPanel, inputqLabel);
            } else if (aArea.getText().trim().isEmpty()) {
                showLabel(inputPanel, inputaLabel);
            } else {
                QA qa = new QA(qArea.getText(), aArea.getText(), user.getId());
                boolean result = backend.QADAO.insertQA(qa);
                if (result) {
                    showLabel(inputPanel, successfulLabel);
                } else {
                    showLabel(inputPanel, failLabel);
                }
            }
        });

        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(20, 590, 80, 35);
        inputPanel.add(quitButton);
        quitButton.addActionListener(e -> System.exit(0));
    }

    private void clearErrorLabels(JPanel panel, JLabel... labels) {
        for (JLabel label : labels) {
            panel.remove(label);
        }
        panel.revalidate();
        panel.repaint();
    }

    private void showLabel(JPanel panel, JLabel label) {
        panel.add(label);
        panel.revalidate();
        panel.repaint();
    }

    private void QApanelComponents(JPanel queryPanel) {
        queryPanel.setLayout(null);

        JLabel userLabel = new JLabel(user.getName());
        userLabel.setBounds(10, 10, 80, 25);
        queryPanel.add(userLabel);

        JLabel qLabel = new JLabel("Query your question:");
        qLabel.setFont(font);
        qLabel.setBounds(150, 20, 200, 25);
        queryPanel.add(qLabel);

        JTextArea qArea = new JTextArea(5, 55);
        JScrollPane qjScrollPane = new JScrollPane(qArea);
        qjScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        qjScrollPane.setBounds(150, 55, 700, 100);
        queryPanel.add(qjScrollPane);
        qArea.setLineWrap(true);
        qArea.setWrapStyleWord(true);
        
        JLabel noReseltLabel = new JLabel("No results found.");
        noReseltLabel.setBounds(200, 165, 150, 35);
        noReseltLabel.setFont(font);

        JButton queryButton = new JButton("Query");
        queryButton.setBounds(145, 165, 80, 35);
        queryPanel.add(queryButton);
        queryButton.addActionListener(e -> {
            QA qa[] = backend.QADAO.searchQA(qArea.getText());
            clearErrorLabels(queryPanel, noReseltLabel);

            JPanel rollPanel = new JPanel();
            rollPanel.setLayout(new BoxLayout(rollPanel, BoxLayout.Y_AXIS));
            if (!qa.equals(null) ) {
                genTextArea(rollPanel, qa);
                JScrollPane scrollPane = new JScrollPane(rollPanel);
                queryPanel.add(scrollPane);
                scrollPane.setBounds(145, 215, 700, 400);
            }else{
                showLabel(queryPanel, noReseltLabel);
            }
                
            queryPanel.revalidate();
            queryPanel.repaint();
        });

        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(20, 590, 80, 35);
        queryPanel.add(quitButton);
        quitButton.addActionListener(e -> System.exit(0));
    }

    public void genTextArea(JPanel panel, QA qa[]) {
        
         for(int i=0;i<qa.length;i++){
                
                JTextArea questionArea = new JTextArea(qa[i].getQuestion());
                questionArea.setLineWrap(true);
                questionArea.setWrapStyleWord(true);
                questionArea.setEditable(false);  // 禁用编辑
                panel.add(questionArea);

                questionArea.setPreferredSize(new Dimension(650, questionArea.getPreferredSize().height));
                panel.add(Box.createVerticalStrut(7));

                JTextArea answerArea = new JTextArea(qa[i].getAnswer());
                answerArea.setLineWrap(true);
                answerArea.setWrapStyleWord(true);
                answerArea.setEditable(false);  // 禁用编辑
                panel.add(answerArea);

                answerArea.setPreferredSize(new Dimension(650, answerArea.getPreferredSize().height));
                if (i<qa.length-1) {
                 panel.add(Box.createVerticalStrut(25));
             }

                answerArea.revalidate();
                answerArea.revalidate();
            }
        
        panel.revalidate();
        panel.repaint();
    }
    
}