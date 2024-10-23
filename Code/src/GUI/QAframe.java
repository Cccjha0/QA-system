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
import backend.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QAframe {

    static Font font = new Font("Arial", Font.PLAIN, 20);
    static JFrame QAframe;
    static User user = null;

    public static void main(String[] args) {
        QAComponents();
    }

    public QAframe(User user) {
        this.user = user;
        QAComponents();
    }

    public static void QAComponents() {
        //窗口设置
        QAframe = new JFrame("QA_Syetem");
        QAframe.setSize(1000, 700);
        QAframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width; //屏幕宽度
        int screenHeight = screenSize.height; //屏幕高度
        QAframe.setLocation((screenWidth - 1000) / 2, (screenHeight - 700) / 2);

        //面板设置
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
        queryButton.setMinimumSize(buttonSize);
        queryButton.setMaximumSize(buttonSize);
        inputButton.setPreferredSize(buttonSize);
        inputButton.setMinimumSize(buttonSize);
        inputButton.setMaximumSize(buttonSize);

        toolBar.add(queryButton);
        toolBar.add(inputButton);

        QAframe.add(toolBar, BorderLayout.NORTH);
        QApanelComponents(queryPanel);
        InputPanelComponents(inputPanel);

        QAframe.add(QAPanel);
        QAframe.setVisible(true);

        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (QAPanel.getLayout());
                cl.show(QAPanel, "query");
            }
        });

        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (QAPanel.getLayout());
                cl.show(QAPanel, "input");
            }
        });
    }

    private static void InputPanelComponents(JPanel inputPanel) {
        inputPanel.setLayout(null);
        JLabel userLabel = new JLabel("User name");
        userLabel.setBounds(10, 10, 80, 25);
        inputPanel.add(userLabel);

        JLabel qLabel = new JLabel("Input your question:");
        qLabel.setFont(font);
        qLabel.setBounds(150, 20, 200, 25);
        inputPanel.add(qLabel);

        JTextArea qArea = new JTextArea(20, 55);
        JScrollPane qjScrollPane = new JScrollPane(qArea);
        qjScrollPane.setBounds(150, 55, 700, 100);
        inputPanel.add(qjScrollPane);
        qArea.setLineWrap(true);
        qArea.setWrapStyleWord(true);
        qArea.setRows(qArea.getColumns());
        qArea.revalidate();

        JLabel aLabel = new JLabel("Input your answer:");
        aLabel.setFont(font);
        aLabel.setBounds(150, 170, 200, 25);
        inputPanel.add(aLabel);

        JTextArea aArea = new JTextArea(30, 55);
        JScrollPane ajScrollPane = new JScrollPane(aArea);
        ajScrollPane.setBounds(150, 215, 700, 330);
        inputPanel.add(ajScrollPane);
        aArea.setLineWrap(true);
        aArea.setWrapStyleWord(true);
        aArea.setRows(aArea.getColumns());
        aArea.revalidate();

        JButton inputButton = new JButton("Input");
        inputButton.setBounds(770, 555, 80, 35);
        inputPanel.add(inputButton);
        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (qArea.getText().trim().isEmpty()) {
                    JLabel Label = new JLabel("Plese input question");
                    Label.setFont(font);
                    Label.setBounds(150, 555, 300, 25);
                    inputPanel.add(Label);
                } else if (aArea.getText().trim().isEmpty()) {
                    JLabel Label = new JLabel("Plese input answer");
                    Label.setFont(font);
                    Label.setBounds(150, 555, 300, 25);
                    inputPanel.add(Label);

                } else {
                    QA qa = new QA(qArea.getText(), aArea.getText(), user.getId());
                    boolean result = backend.QADAO.insertQA(qa);
                    if (result) {
                        //Successful
                    } else {//wrong
                    }
                }
                inputPanel.revalidate();
                inputPanel.repaint();
            }
        });
        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(20, 590, 80, 35);
        inputPanel.add(quitButton);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QAframe.dispose();
            }
        });
    }

    private static void QApanelComponents(JPanel queryPanel) {
        queryPanel.setLayout(null);

        JLabel userLabel = new JLabel("User name");
        userLabel.setBounds(10, 10, 80, 25);
        queryPanel.add(userLabel);

        JLabel qLabel = new JLabel("Query your question:");
        qLabel.setFont(font);
        qLabel.setBounds(150, 20, 200, 25);
        queryPanel.add(qLabel);

        JTextArea qArea = new JTextArea(20, 55);
        JScrollPane qjScrollPane = new JScrollPane(qArea);
        qjScrollPane.setBounds(150, 55, 700, 100);
        queryPanel.add(qjScrollPane);
        qArea.setLineWrap(true);
        qArea.setWrapStyleWord(true);
        qArea.setRows(qArea.getColumns());
        qArea.revalidate();

        JButton queryButton = new JButton("Query");
        queryButton.setBounds(145, 165, 80, 35);
        queryPanel.add(queryButton);
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet res = backend.QADAO.searchQA(qArea.getText());

                JPanel rollpanel = new JPanel();
                rollpanel.setLayout(new BoxLayout(rollpanel, BoxLayout.Y_AXIS));
                genTextArea(rollpanel, res);
                JScrollPane scrollPane = new JScrollPane(rollpanel);
                queryPanel.add(scrollPane);
                scrollPane.setBounds(145, 215, 700, 400);
                queryPanel.revalidate();
                queryPanel.repaint();
            }
        });
        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(20, 590, 80, 35);
        queryPanel.add(quitButton);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QAframe.dispose();
            }
        });
    }

    public static void genTextArea(JPanel panel, ResultSet res) {
        try {
            while (res.next()) {
                //a 是问题
                JTextArea aArea = new JTextArea(3, 55);
                aArea.setText(res.getString(1));
                aArea.setLineWrap(true);
                aArea.setWrapStyleWord(true);

                // 根据内容计算高度
                int lineCount = aArea.getLineCount();
                int height = Math.min(lineCount * 25, 100); // 25是每行的高度，100是最大高度
                aArea.setPreferredSize(new Dimension(650, height));
                panel.add(aArea);

                JTextArea qArea = new JTextArea(3, 55);
                qArea.setText(res.getString(2));
                qArea.setLineWrap(true);
                qArea.setWrapStyleWord(true);

                lineCount = qArea.getLineCount();
                height = Math.min(lineCount * 25, 100); // 25是每行的高度，100是最大高度
                qArea.setPreferredSize(new Dimension(650, height));
                panel.add(Box.createVerticalStrut(7));
                panel.add(qArea);

                panel.add(Box.createVerticalStrut(25));

                aArea.revalidate();
                qArea.revalidate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        panel.revalidate();
        panel.repaint();
    }

}
