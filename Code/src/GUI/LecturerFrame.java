package GUI;

import backend.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LecturerFrame extends StudentFrame implements Editable {
    public LecturerFrame(User user) {
        super(user);
        //initializeFrameComponents();
    }

    @Override
    public void initializeFrameComponents() {
        initializeToolbarComponents();
        initializePanels();
        setVisible(true);
    }

    private void initializeToolbarComponents() {
        JToolBar toolBar = createCommonToolBar();
        JButton inputButton = createButton("Input", e -> switchPanel(mainPanel, "input"));
        inputButton.setPreferredSize(new Dimension(100, 30));
        toolBar.add(inputButton, 0); // 将 Input 按钮添加到第一个位置
        add(toolBar, BorderLayout.NORTH);
    }

    private void initializePanels() {
        JPanel inputPanel = new JPanel();
        mainPanel.add(queryPanel, "query");
        mainPanel.add(inputPanel, "input");
        mainPanel.add(recentPanel, "recent");
        add(mainPanel);

        initializeQueryPanel(queryPanel);
        initializeInputPanelComponents(inputPanel);
    }

    @Override
    public void initializeInputPanelComponents(JPanel inputPanel) {
        inputPanel.setLayout(null);
        userCenterButton = addButton(user.getName(), 10, 10, 100, 25, inputPanel);
        JLabel qLabel = addLabel("Input your question:", 150, 20, 200, 25, inputPanel);
        qLabel.setFont(font);
        JLabel aLabel = addLabel("Input your answer:", 150, 170, 200, 25, inputPanel);
        aLabel.setFont(font);
        JLabel pleaseInputQuestionLabel = new JLabel("Please input question");
        JLabel pleaseInputAnswerLabel = new JLabel("Please input answer");
        JLabel successfulInputLabel = new JLabel("Input Successful");
        JLabel failLabel = new JLabel("Input Failed");

        JTextArea qArea = new JTextArea(5, 55);
        JScrollPane qScrollPane = new JScrollPane(qArea);
        qScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        qScrollPane.setBounds(150, 55, 700, 100);
        inputPanel.add(qScrollPane);
        qArea.setLineWrap(true);
        qArea.setWrapStyleWord(true);

        JTextArea aArea = new JTextArea(20, 55);
        JScrollPane aScrollPane = new JScrollPane(aArea);
        aScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        aScrollPane.setBounds(150, 215, 700, 330);
        inputPanel.add(aScrollPane);
        aArea.setLineWrap(true);
        aArea.setWrapStyleWord(true);

        JButton inputButton = new JButton("Input");
        inputButton.setBounds(770, 555, 80, 35);
        inputPanel.add(inputButton);
        //inputButton.addActionListener(e -> handleInputAction(qArea, aArea, inputPanel, pleaseInputQuestionLabel, pleaseInputAnswerLabel, successfulInputLabel, failLabel));
        inputButton.addActionListener(e -> handleInputAction(qArea, aArea, inputPanel));
        
        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(20, 570, 80, 35);
        inputPanel.add(quitButton);
        quitButton.addActionListener(e -> restartApplication());
        userCenterButton.addActionListener(e -> CenterFrame.getInstance(user));
    }
    //private void handleInputAction(JTextArea qArea, JTextArea aArea, JPanel inputPanel, JLabel... labels)
    private void handleInputAction(JTextArea qArea, JTextArea aArea, JPanel inputPanel) {
        //clearErrorLabels(inputPanel, labels);
        QA qa = new QA(qArea.getText(), aArea.getText(), user.getId());
        //boolean result = ;        
        if (qArea.getText().trim().isEmpty()) {
            //showLabel(inputPanel, labels[0]);
            JOptionPane.showMessageDialog(null, "Please input question","Input Error",JOptionPane.INFORMATION_MESSAGE);
        } else if (aArea.getText().trim().isEmpty()) {
            //showLabel(inputPanel, labels[1]);
            JOptionPane.showMessageDialog(null, "Please input answer","Input Error",JOptionPane.INFORMATION_MESSAGE);
        } else if(backend.QADAO.insertQA(qa)){
            JOptionPane.showMessageDialog(null, "Input Successful","Input Success",JOptionPane.INFORMATION_MESSAGE);
            //showLabel(inputPanel, result ? labels[2] : labels[3]);
        }else{
            JOptionPane.showMessageDialog(null, "Input Failed","Input Error",JOptionPane.INFORMATION_MESSAGE);
        }
    }
//
//    private void showLabel(JPanel panel, JLabel label) {
//        label.setBounds(400, 170, 300, 30);
//        label.setFont(font);
//        panel.add(label);
//        panel.revalidate();
//        panel.repaint();
//    }
}
