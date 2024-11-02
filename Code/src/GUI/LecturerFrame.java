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
import java.util.List;

public class LecturerFrame extends StudentFrame implements Editable{
    
    
    public LecturerFrame(User user) {
        super(user);
    }
    
    @Override
     void FrameComponents() {
        // 窗口设置
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation((screenSize.width - 1000) / 2, (screenSize.height - 700) / 2);
        setTitle("Lecture QA_System");

        // 面板设置
        
        JPanel inputPanel = new JPanel();
        mainPanel.add(queryPanel, "query");
        mainPanel.add(inputPanel, "input");
        mainPanel.add(recentPanel,"recent");


        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        JButton queryButton = new JButton("Query");
        JButton inputButton = new JButton("Input");
        JButton recentButton = new JButton("Recent Search");
        Dimension buttonSize = new Dimension(100, 30);
        queryButton.setPreferredSize(buttonSize);
        inputButton.setPreferredSize(buttonSize);
        toolBar.setMargin(new Insets(5, 20, 5, 20));
        toolBar.add(queryButton);
        toolBar.add(inputButton);
        toolBar.add(recentButton);

        add(toolBar, BorderLayout.NORTH);
        queryPanelComponents(queryPanel);
        InputPanelComponents(inputPanel);
        

        add(mainPanel);
        setVisible(true);

        // 使用抽取的方法进行切换
        queryButton.addActionListener(e -> {
            switchPanel(mainPanel, "query");
            recentPanel.removeAll();
        });
        inputButton.addActionListener(e -> switchPanel(mainPanel, "input"));
        recentButton.addActionListener(e -> {
            switchPanel(mainPanel, "recent");
            lectureRecentPanelInitital();
            
        });
    }

    private void switchPanel(JPanel panel, String name) {
        CardLayout cl = (CardLayout) (panel.getLayout());
        cl.show(panel, name);
    }

    public void InputPanelComponents(JPanel inputPanel) {
        inputPanel.setLayout(null);
        userCenterButton = addButton(user.getName(),10,10,100,25,inputPanel);
        JLabel qLabel = addLabel("Input your question:",150,20,200,25,inputPanel);
        qLabel.setFont(font);
        JLabel aLabel = addLabel("Input your answer:",150,170,200,25,inputPanel);
        aLabel.setFont(font);
        JLabel pleaseInputQuestionLabel = new JLabel("Please input question");
        JLabel pleaseInputAnswerLabel = new JLabel("Please input answer");
        JLabel successfulInputLabel = new JLabel("Input Successful");
        JLabel failLabel = new JLabel("Input Failed");

        JTextArea qArea = new JTextArea(5, 55);
        JScrollPane qjScrollPane = new JScrollPane(qArea);
        qjScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        qjScrollPane.setBounds(150, 55, 700, 100);
        inputPanel.add(qjScrollPane);
        qArea.setLineWrap(true);
        qArea.setWrapStyleWord(true);

        JTextArea aArea = new JTextArea(20, 55);
        JScrollPane ajScrollPane = new JScrollPane(aArea);
        ajScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        ajScrollPane.setBounds(150, 215, 700, 330);
        inputPanel.add(ajScrollPane);
        aArea.setLineWrap(true);
        aArea.setWrapStyleWord(true);

        JButton inputButton = new JButton("Input");
        inputButton.setBounds(770, 555, 80, 35);
        inputPanel.add(inputButton);
        inputButton.addActionListener(e -> {
            // 清除所有标签
            clearErrorLabels(inputPanel, pleaseInputQuestionLabel, pleaseInputAnswerLabel, successfulInputLabel, failLabel);
            
            if (qArea.getText().trim().isEmpty()) {
                showLabel(inputPanel, pleaseInputQuestionLabel);
            } else if (aArea.getText().trim().isEmpty()) {
                showLabel(inputPanel, pleaseInputAnswerLabel);
            } else {
                QA qa = new QA(qArea.getText(), aArea.getText(), user.getId());
                boolean result = backend.QADAO.insertQA(qa);
                if (result) {
                    showLabel(inputPanel, successfulInputLabel);
                } else {
                    showLabel(inputPanel, failLabel);
                }
            }
        });

        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(20, 570, 80, 35);
        inputPanel.add(quitButton);
        quitButton.addActionListener(e -> {
            restartApplication();
        });
        userCenterButton.addActionListener(e -> {
            CenterFrame.getInstance(user);
            //new CenterFrame(user);
        });
    }
    public void lectureRecentPanelInitital() {
        recentPanel.setLayout(null);
        userCenterButton = addButton(user.getName(),10,10,100,25,recentPanel);
        RecentQueriesLabel.setBounds(0, 50, 1000, 60);
        RecentQueriesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        RecentQueriesLabel.setFont(font1);
        recentPanel.add(RecentQueriesLabel);


        JButton querybutton = addButton("Query", 700, 150, 80, 30, recentPanel);
        recentPanel.add(RecentQueriesLabel);
        RecentQueriesLabel.setFont(font);

        List<String> listQuestion = RecentQueriesDAO.getRecentQueries(user.getId());
        String[] question= listQuestion.toArray(new String[0]);

        JComboBox questionComboBox = new JComboBox<>(question);
        recentPanel.add(questionComboBox);
        recentPanel.repaint();

        questionComboBox.setBounds(320,150,360,30);

        querybutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) questionComboBox.getSelectedItem();
                switchPanel(mainPanel, "query");
                searchable(user.getId(),selectedOption);
                qArea.setText(selectedOption);
                handleQuery(qArea, queryPanel, noReseltLabel, user);
            }
        });
        recentPanel.add(querybutton);
        //recentPanel.add(userCenterButton);
        JButton quitButton = addButton("Quit",20,570,80,35,queryPanel);
        quitButton.addActionListener(e -> {
            restartApplication();
        });
        userCenterButton.addActionListener(e -> {
            CenterFrame.getInstance(user);
            //new CenterFrame(user);
        });
        recentPanel.add(quitButton);
    }
    private void showLabel(JPanel panel, JLabel label) {
        label.setBounds(400,170,300,30);
        label.setFont(font);
        panel.add(label);
        panel.revalidate();
        panel.repaint();
    }
    public boolean isScrollPanePresent(JPanel panel,String scrollPaneName) {
    for (Component comp : panel.getComponents()) {
        if (comp instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) comp;
            if (scrollPaneName.equals(scrollPane.getName())) {
             panel.remove(scrollPane); // 找到 JScrollPane
            }
        }
    }
    return false; // 没有找到 JScrollPane
}

    public static void main(String[] args) {
        new LecturerFrame(new User("Ponder","123",false));
    }
}