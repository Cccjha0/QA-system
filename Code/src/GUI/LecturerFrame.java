/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package GUI;

import backend.*;
import backend.User;
import javax.swing.*;
import java.awt.*;

public class LecturerFrame extends StudentFrame implements Editable,Searchable{
    private JPanel inputPanel = new JPanel();
    private JButton inputPanelButton = new JButton("Input");
    private JLabel inputquestionLabel,inputanswerLabel,getInputquestionLabel,pleaseInputQuestionLabel,pleaseInputAnswerLabel,successfulInputLabel,failLabel = new JLabel();
    private JTextArea aArea = new JTextArea();
    private JButton  inputButton = new JButton();
    public LecturerFrame(User user) {
        super(user);
        Frametitle ="Lecture QA_System";
    }
    @Override
    protected void initializeComponents(){
        setTitle(Frametitle);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation((screenSize.width - 1000) / 2, (screenSize.height - 700) / 2);

        mainPanel.add(queryPanel, "query");
        mainPanel.add(inputPanel, "input");
        mainPanel.add(searchRecordPanel,"searchRecord");
        queryPanel.setLayout(null);
        searchRecordPanel.setLayout(null);
        inputPanel.setLayout(null);
        mainPanel.add(inputPanel);
        mainPanel.add(queryPanel);
        mainPanel.add(searchRecordPanel);

        queryPanelButton = new JButton("Query");
        searchRecordButton = new JButton("Search Record");

        toolBar.setFloatable(false);
        Dimension buttonSize = new Dimension(100, 30);
        queryButton.setPreferredSize(buttonSize);
        inputPanelButton.setPreferredSize(buttonSize);
        searchRecordButton.setPreferredSize(buttonSize);

        toolBar.setMargin(new Insets(5, 20, 5, 20));
        toolBar.add(queryPanelButton);
        toolBar.add(inputPanelButton);
        toolBar.add(searchRecordButton);

        add(toolBar,BorderLayout.NORTH);
        add(mainPanel);
        setVisible(true);

        userCenterButton = addButton(user.getName(),10,10,200,25);
        userCenterButton.addActionListener(e -> {
            CenterFrame.getInstance(user);
        });
    }
    @Override
    protected void layoutComponents() {
        super.layoutComponents();
        inputPanelComponents();
    }
    protected void inputPanelComponents(){
        inputquestionLabel = addLabel("Input your question:",150,20,200,25);
        inputquestionLabel.setFont(font);
        inputanswerLabel = addLabel("Input your answer:",150,170,200,25);
        inputanswerLabel.setFont(font);
        pleaseInputQuestionLabel = new JLabel("Please input question");
        pleaseInputAnswerLabel = new JLabel("Please input answer");
        successfulInputLabel = new JLabel("Input Successful");
        failLabel = new JLabel("Input Failed");

        qArea = new JTextArea(5, 55);
        JScrollPane qjScrollPane = new JScrollPane(qArea);
        qjScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        qjScrollPane.setBounds(150, 55, 700, 100);
        inputPanel.add(qjScrollPane);
        qArea.setLineWrap(true);
        qArea.setWrapStyleWord(true);

        aArea = new JTextArea(20, 55);
        JScrollPane ajScrollPane = new JScrollPane(aArea);
        ajScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        ajScrollPane.setBounds(150, 215, 700, 330);
        inputPanel.add(ajScrollPane);
        aArea.setLineWrap(true);
        aArea.setWrapStyleWord(true);

        inputButton = new JButton("Input");
        inputButton.setBounds(770, 555, 80, 35);
        inputPanel.add(inputButton);
        quitButton = new JButton("Quit");
        quitButton.setBounds(20, 590, 80, 35);
        inputPanel.add(quitButton);

        inputButton.addActionListener(e -> {
            inputable();
        });
        quitButton.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });
    }
    private void showLabel(JPanel panel, JLabel label) {
        label.setBounds(400,170,300,30);
        label.setFont(font);
        panel.add(label);
        panel.revalidate();
        panel.repaint();
    }
    @Override
    public void inputable() {
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
    }


//    public static void main(String[] args) {
//        new LecturerFrame(new User("Ponder","123",false));
//    }
}