/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package GUI;
import backend.QA;
import backend.RecentQueriesDAO;
import backend.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentFrame extends baseFrame implements Searchable{
    protected JPanel mainPanel = new JPanel();
    protected JPanel queryPanel = new JPanel();
    protected JPanel searchRecordPanel = new JPanel();
    protected JToolBar toolBar = new JToolBar();
    protected JLabel qLabel,noReseltLabel = new JLabel();
    protected JTextArea qArea = new JTextArea(5,55);
    protected JScrollPane qjScrollPane = new JScrollPane(qArea);
    protected JButton queryPanelButton,searchRecordButton,userCenterButton,queryButton,quitButton= new JButton();
    protected Dimension buttonSize = new Dimension(100, 30);

    public StudentFrame(User user) {
        super(user);
        Frametitle="Students QA_System";
    }
    @Override
    protected void initializeComponents(){
        setTitle(Frametitle);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation((screenSize.width - 1000) / 2, (screenSize.height - 700) / 2);

        mainPanel.setLayout(new CardLayout());
        mainPanel.add(queryPanel, "query");
        mainPanel.add(searchRecordPanel,"searchRecord");
        queryPanel.setLayout(null);
        searchRecordPanel.setLayout(null);

        queryPanelButton = new JButton("Query");
        searchRecordButton = new JButton("Search Record");

        toolBar.setFloatable(false);
        queryPanelButton.setPreferredSize(buttonSize);
        searchRecordPanel.setPreferredSize(buttonSize);

        toolBar.add(queryPanelButton);
        toolBar.add(searchRecordButton);

        add(toolBar, BorderLayout.NORTH);

        add(mainPanel);
        setVisible(true);

        userCenterButton = addButton(user.getName(),10,10,200,25);
        userCenterButton.addActionListener(e -> {
            CenterFrame.getInstance(user);
        });
    }
    @Override
    protected void layoutComponents(){
        queryPanelInitital();
        recentPanelInitital();
    }
    public void recentPanelInitital(){
        JLabel RecentQueriesLabel = addLabel("Recent Queries",100,50,250,25);
        RecentQueriesLabel.setFont(font);
        searchRecordPanel.add(RecentQueriesLabel);
        List<String> qusetion = RecentQueriesDAO.getRecentQueries(user.getId());
        JComboBox questionComboBox = new JComboBox<>((ComboBoxModel) qusetion);
        searchRecordPanel.add(questionComboBox);
        questionComboBox.setBounds(20,100,300,300);
        String selectedOption = (String) questionComboBox.getSelectedItem();

        JButton querybutton = addButton("Query",350,100,80,25);
        querybutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(mainPanel,"query");
                queryable(user.getId(),selectedOption);
                qArea.setText(selectedOption);
                handleQuery(qArea,queryPanel,noReseltLabel,user);
            }
        });
        searchRecordPanel.add(querybutton);
        searchRecordPanel.add(userCenterButton);
    }
    public void queryPanelInitital(){
            qLabel = addLabel("Query your question:",150,20,200,25);
            qLabel.setFont(font);
            noReseltLabel = addLabel("No results found.",250,165,200,35);
            noReseltLabel.setFont(font);
            noReseltLabel.setVisible(false);
            queryButton = addButton("Query",145,165,80,35);
            quitButton = addButton("Quit",20,590,80,35);
            qjScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            qjScrollPane.setBounds(150, 55, 700, 100);
            qArea.setLineWrap(true);
            qArea.setWrapStyleWord(true);

            queryPanelButton.addActionListener(e -> {
                switchPanel(mainPanel,"query");
            });
            searchRecordButton.addActionListener(e -> {
                switchPanel(mainPanel,"searchRecord");
            });
            queryButton.addActionListener(e -> handleQuery(qArea, queryPanel,noReseltLabel,user));

            quitButton.addActionListener(e -> {
                new LoginFrame();
                dispose();
            });
            addComponent(queryPanel,userCenterButton,qLabel,noReseltLabel,qjScrollPane);
    }

    private void handleQuery(JTextArea qArea, JPanel queryPanel, JLabel noReseltLabel,User user) {
        if (qArea.getText().trim().isEmpty()) {
            return;
        }
        String keyword =qArea.getText();
        QA qa[] = queryable(user.getId(),keyword);
        JPanel rollPanel = new JPanel();
        rollPanel.setLayout(new BoxLayout(rollPanel, BoxLayout.Y_AXIS));
      //  clearErrorLabels(queryPanel, noReseltLabel);
        isScrollPanePresent(queryPanel,"scrollPane");
        noReseltLabel.setVisible(false);
        
        if (qa != null) {
            JScrollPane scrollPane = new JScrollPane(rollPanel);
            generateQAResults(rollPanel, qa);
            scrollPane.setName("scrollPane");
            queryPanel.add(scrollPane);
            scrollPane.setBounds(145, 215, 700, 400);
            }else{
                showLabel(queryPanel, noReseltLabel); 
            }

        queryPanel.revalidate();
        queryPanel.repaint();
    }

    private void generateQAResults(JPanel panel, QA[] qa) {
        panel.setLayout(new GridBagLayout()); // 使用 GridBagLayout 布局
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // 组件填充网格单元格
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        int i = 0;
        while (qa[i] != null) {
            // 创建问题文本域
            JTextArea questionArea = createTextArea(qa[i].getQuestion());
            gbc.gridx = 0; // 设置问题在第 1 列
            gbc.weightx = 0.5; // 设置列宽占比
            panel.add(questionArea, gbc);

            // 创建回答文本域
            JTextArea answerArea = createTextArea(qa[i].getAnswer());
            gbc.gridx = 1; // 设置回答在第 2 列
            gbc.weightx = 0.5;
            panel.add(answerArea, gbc);

            // 设置文本域的宽度和高度一致
            int questionHeight = getTextAreaHeight(questionArea);
            int answerHeight = getTextAreaHeight(answerArea);
            int preferredHeight = Math.max(questionHeight, answerHeight);
            questionArea.setPreferredSize(new Dimension(300, preferredHeight));
            answerArea.setPreferredSize(new Dimension(300, preferredHeight));

            questionArea.revalidate();
            answerArea.revalidate();

            // 增加行
            gbc.gridy++;
            i++;
        }

        panel.revalidate();
        panel.repaint();
    }

    // 根据内容动态获取文本区域高度
    private int getTextAreaHeight(JTextArea textArea) {
        int lineCount = textArea.getLineCount();
        return Math.min(lineCount * 25, 100); // 每行25像素，高度最大100像素
    }

    // 创建文本域
    private JTextArea createTextArea(String text) {
        JTextArea textArea = new JTextArea(3, 20); // 设置初始行数和宽度
        textArea.setText(text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        return textArea;
    }
//    private void generateQAResults(JPanel panel, QA qa[]) {  //学生端显示有问题
//        int i = 0;
//        while (qa[i] != null) {
//                JTextArea questionArea = createTextArea(qa[i].getQuestion());
//                questionArea.setEditable(false);  // 禁用编辑
//                panel.add(questionArea);
//                panel.add(Box.createVerticalStrut(7));
//                JTextArea answerArea = createTextArea(qa[i].getAnswer());
//                answerArea.setEditable(false);  // 禁用编辑
//                panel.add(answerArea);
//                panel.add(Box.createVerticalStrut(25));
//                questionArea.revalidate();
//                answerArea.revalidate();
//                i++;
//            }
//        panel.revalidate();
//        panel.repaint();
//    }
//    private JTextArea createTextArea(String text) {
//        JTextArea textArea = new JTextArea(3, 55);
//        textArea.setText(text);
//        textArea.setLineWrap(true);
//        textArea.setWrapStyleWord(true);
//        textArea.setEditable(false);
//
//        int lineCount = textArea.getLineCount();
//        int height = Math.min(lineCount * 25, 100);
//        textArea.setPreferredSize(new Dimension(650, height));
//
//        return textArea;
//    }


    private void showLabel(JPanel panel, JLabel label) {
        label.setVisible(true);
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

    @Override
    public QA[] queryable(int id, String keyword) {
        QA qa[] = backend.QADAO.searchQA(user.getId(),keyword);
        return qa;
    }
    public void switchPanel(JPanel panel, String name) {
        CardLayout cl = (CardLayout) panel.getLayout();
        cl.show(panel, name);
    }

//    public static void main(String[] args) {
//        new StudentFrame(new User("Ponder","123",true));
//    }
}