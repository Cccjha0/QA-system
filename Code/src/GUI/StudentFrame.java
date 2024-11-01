
package GUI;
import backend.QA;
import backend.User;
import backend.RecentQueriesDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentFrame extends QAFrame implements Searchable{
    private JFrame studentFrame=new JFrame();
    JPanel mainPanel = new JPanel(new CardLayout());
    JPanel queryPanel = new JPanel();
    JPanel recentPanel = new JPanel();
    JLabel noReseltLabel = new JLabel();
    JTextArea qArea = new JTextArea();
    public StudentFrame(User user) {
        this.user = user;
        Frametitle="Students QA_System";
        FrameComponents();
    }

    public StudentFrame() {
    }

    @Override
    void FrameComponents() {
        // 窗口设置
        studentFrame.setSize(1000, 700);
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        studentFrame.setTitle(Frametitle);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        studentFrame.setLocation((screenSize.width - 1000) / 2, (screenSize.height - 700) / 2);

        // 面板设置
        mainPanel.add(recentPanel,"recent");
        mainPanel.add(queryPanel, "query");

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        JButton queryButton = new JButton("Query");
        JButton recentButton = new JButton("Recent Search");

        Dimension buttonSize = new Dimension(100, 30);
        queryButton.setPreferredSize(buttonSize);

        toolBar.add(queryButton);
        toolBar.add(recentButton);

        studentFrame.add(toolBar, BorderLayout.NORTH);
        queryPanelComponents(queryPanel);
        recentPanelInitital();

        studentFrame.add(mainPanel);
        studentFrame.setVisible(true);

        // 保持左上角按钮功能
        queryButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel, "query");
        });
        recentButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel, "recent");

        });
    }

    public void queryPanelComponents(JPanel queryPanel) {
        queryPanel.setLayout(null);

        JLabel userLabel = addLabel(user.getName(),10,10,200,25,queryPanel);
        JLabel qLabel = addLabel("Query your question:",150,20,200,25,queryPanel);
        qLabel.setFont(font);
        noReseltLabel = addLabel("No results found.",250,165,200,35,queryPanel);
        noReseltLabel.setFont(font);
        noReseltLabel.setVisible(false);

        qArea = new JTextArea(5, 55);
        JScrollPane qjScrollPane = new JScrollPane(qArea);
        qjScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        qjScrollPane.setBounds(150, 55, 700, 100);
        queryPanel.add(qjScrollPane);
        qArea.setLineWrap(true);
        qArea.setWrapStyleWord(true);

        JButton queryButton = addButton("Query",145,165,80,35,queryPanel);
        queryButton.addActionListener(e -> handleQuery(qArea, queryPanel,noReseltLabel,user));
        JButton quitButton = addButton("Quit",20,590,80,35,queryPanel);
        quitButton.addActionListener(e -> {
             new LoginFrame();
            dispose();
        });


    }
    private void handleQuery(JTextArea qArea, JPanel queryPanel, JLabel noReseltLabel,User user) {
        if (qArea.getText().trim().isEmpty()) {
            return;
        }
        QA qa[] = searchable(user.getId(),qArea.getText());
        JPanel rollPanel = new JPanel();
        rollPanel.setLayout(new BoxLayout(rollPanel, BoxLayout.Y_AXIS));
        //  clearErrorLabels(queryPanel, noReseltLabel);
        isScrollPanePresent(queryPanel,"scrollPane");
        queryPanel.remove(noReseltLabel);

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
    public void recentPanelInitital(){
        JLabel RecentQueriesLabel = addLabel("Recent Queries",100,50,250,25,recentPanel);
        RecentQueriesLabel.setFont(font);
        recentPanel.add(RecentQueriesLabel);
        List<String> qusetion = RecentQueriesDAO.getRecentQueries(user.getId());
        JComboBox questionComboBox = new JComboBox<>((ComboBoxModel) qusetion);
        recentPanel.add(questionComboBox);
        questionComboBox.setBounds(20,100,300,300);
        String selectedOption = (String) questionComboBox.getSelectedItem();

        JButton querybutton = addButton("Query",350,100,80,25,recentPanel);
        querybutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(mainPanel,"query");
                searchable(user.getId(),selectedOption);
                qArea.setText(selectedOption);
                handleQuery(qArea,queryPanel,noReseltLabel,user);
            }
        });
        recentPanel.add(querybutton);
        //recentPanel.add(userCenterButton);
        JButton quitButton = addButton("Quit",20,590,80,35,queryPanel);
        quitButton.addActionListener(e -> {
             new LoginFrame();
            dispose();
        });
    }
    private void generateQAResults(JPanel panel, QA qa[]) {  //学生端显示有问题
        int i = 0;
        while (qa[i] != null) {

            JTextArea questionArea = createTextArea(qa[i].getQuestion());
            questionArea.setEditable(false);  // 禁用编辑
            panel.add(questionArea);

            // questionArea.setPreferredSize(new Dimension(650, questionArea.getPreferredSize().height));
            panel.add(Box.createVerticalStrut(7));

            JTextArea answerArea = createTextArea(qa[i].getAnswer());

            answerArea.setEditable(false);  // 禁用编辑
            panel.add(answerArea);

            //answerArea.setPreferredSize(new Dimension(650, answerArea.getPreferredSize().height));

            panel.add(Box.createVerticalStrut(25));


            questionArea.revalidate();
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


    private void showLabel(JPanel panel, JLabel label) {
        panel.add(label);
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

    public static void main(String[] args) {
        new StudentFrame(new User("Ponder","123",true));
    }
    private void switchPanel(JPanel mainPanel, String query) {
                CardLayout cl = (CardLayout) (mainPanel.getLayout());
                cl.show(mainPanel, "query");
            }
    @Override
    public QA[] searchable(int userid, String keyword) {
        QA qa[] = backend.QADAO.searchQA(userid,keyword);
        return qa;
    }
}// 按两次 Shift 打开“随处搜索”对话框并输入 `show whitespaces`，
