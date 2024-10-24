/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package GUI;
import backend.QA;
import backend.User;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentFrame {

    private static final Font font = new Font("Arial", Font.PLAIN, 20);
    private JFrame studentFrame;
    private User user;

    public StudentFrame(User user) {
        this.user = user;
        studentComponents();
    }

    private void studentComponents() {
        // 窗口设置
        studentFrame = new JFrame("QA_System");
        studentFrame.setSize(1000, 700);
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        studentFrame.setLocation((screenSize.width - 1000) / 2, (screenSize.height - 700) / 2);

        // 面板设置
        JPanel studentPanel = new JPanel(new CardLayout());
        JPanel queryPanel = new JPanel();
        studentPanel.add(queryPanel, "query");

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        JButton queryButton = new JButton("Query");

        Dimension buttonSize = new Dimension(100, 30);
        queryButton.setPreferredSize(buttonSize);

        toolBar.add(queryButton);

        studentFrame.add(toolBar, BorderLayout.NORTH);
        studentPanelComponents(queryPanel);

        studentFrame.add(studentPanel);
        studentFrame.setVisible(true);

        // 保持左上角按钮功能
        queryButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) (studentPanel.getLayout());
            cl.show(studentPanel, "query");
        });
    }

    private void studentPanelComponents(JPanel queryPanel) {
        queryPanel.setLayout(null);

        JLabel userLabel = new JLabel(user.getName());
        userLabel.setBounds(10, 10, 200, 25);
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

        JButton queryButton = new JButton("Query");
        queryButton.setBounds(145, 165, 80, 35);
        queryPanel.add(queryButton);
        queryButton.addActionListener(e -> handleQuery(qArea, queryPanel));

        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(20, 590, 80, 35);
        queryPanel.add(quitButton);
        quitButton.addActionListener(e -> System.exit(0));
        
        
    }

    private void handleQuery(JTextArea qArea, JPanel queryPanel) {
        QA qa[] = backend.QADAO.searchQA(qArea.getText());

        JLabel noReseltLabel = new JLabel("No results found.");
        noReseltLabel.setBounds(200, 165, 150, 35);
        noReseltLabel.setFont(font);
        JPanel rollPanel = new JPanel();
        rollPanel.setLayout(new BoxLayout(rollPanel, BoxLayout.Y_AXIS));
        clearErrorLabels(queryPanel, noReseltLabel);
        if (!qa.equals(null) ) {
                generateQAResults(rollPanel, qa);
                JScrollPane scrollPane = new JScrollPane(rollPanel);
                queryPanel.add(scrollPane);
                scrollPane.setBounds(145, 215, 700, 400);
            }else{
                showLabel(queryPanel, noReseltLabel);
            }
        

        JScrollPane scrollPane = new JScrollPane(rollPanel);
        scrollPane.setBounds(145, 215, 700, 400);
        queryPanel.add(scrollPane);

        queryPanel.revalidate();
        queryPanel.repaint();
    }

    private void generateQAResults(JPanel panel, QA qa[]) {
        int i = 0;
        while (qa[i] != null) {
            
                
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
                
                 panel.add(Box.createVerticalStrut(25));
             

                answerArea.revalidate();
                answerArea.revalidate();
                
                i++;
            }
        
        panel.revalidate();
        panel.repaint();
    }

    private JTextArea createTextArea(String text) {
        JTextArea textArea = new JTextArea(3, 55);
        textArea.setText(text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        int lineCount = textArea.getLineCount();
        int height = Math.min(lineCount * 25, 100);
        textArea.setPreferredSize(new Dimension(650, height));

        return textArea;
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
}