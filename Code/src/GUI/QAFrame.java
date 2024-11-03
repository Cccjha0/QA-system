/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import backend.RecentQueriesDAO;
import backend.User;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatButton;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

/**
 *
 * @author 陈炯昊
 */
public abstract class QAFrame extends BaseFrame {

    protected User user;
    protected JPanel mainPanel = new JPanel(new CardLayout());
    protected JPanel queryPanel = new JPanel();
    protected JPanel recentPanel = new JPanel();
    protected JLabel noResultLabel;
    protected JTextArea qArea;
    protected JLabel recentQueriesLabel;
    protected JButton userCenterButton;
    protected Font font = new Font("Arial", Font.PLAIN, 20);

    public QAFrame(User user) {
        this.user = user;
        initializeFrame();
    }

    protected void initializeFrame() {
        setTitle("QA System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation((screenSize.width - 1000) / 2, (screenSize.height - 700) / 2);

        FlatButton usersButton = new FlatButton();
        usersButton.setIcon(new FlatSVGIcon("..\\Code\\resources\\users.svg"));
        usersButton.setButtonType(FlatButton.ButtonType.toolBarButton);
        usersButton.setFocusable(false);
        usersButton.addActionListener(e -> CenterFrame.getInstance(user));
        menuBar.add(Box.createGlue());
        menuBar.add(usersButton);

    }

    protected JToolBar createCommonToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        Dimension buttonSize = new Dimension(100, 30);
        toolBar.setMargin(new Insets(5, 20, 5, 20));

        JButton queryButton = createButton("Query", e -> switchPanel(mainPanel, "query"));
        JButton recentButton = createButton("Recent Search", e -> {
            initializeRecentPanel();
            switchPanel(mainPanel, "recent");
        });

        queryButton.setPreferredSize(buttonSize);
        recentButton.setPreferredSize(buttonSize);

        toolBar.add(queryButton);
        toolBar.add(recentButton);
        return toolBar;
    }

    protected JButton createButton(String title, ActionListener listener) {
        JButton button = new JButton(title);
        button.addActionListener(listener);
        return button;
    }

    protected void switchPanel(JPanel panel, String name) {
        CardLayout cl = (CardLayout) (panel.getLayout());
        cl.show(panel, name);
    }

    @Override
    protected void clearErrorLabels(JPanel panel, JLabel... labels) {
        for (JLabel label : labels) {
            label.setVisible(false);
            panel.remove(label);
        }
    }

    protected void initializeRecentPanel() {
        recentPanel.setLayout(null);
        userCenterButton = addButton(user.getName(), 10, 10, 100, 25, recentPanel);
        recentQueriesLabel = new JLabel("Recent Queries");
        recentQueriesLabel.setBounds(0, 50, 1000, 60);
        recentQueriesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        recentQueriesLabel.setFont(font);
        recentPanel.add(recentQueriesLabel);

        updateRecentPanelData(); // 初始化时更新数据

        JButton quitButton = addButton("Quit", 20, 570, 80, 35, recentPanel);
        quitButton.addActionListener(e -> restartApplication());
        recentPanel.add(quitButton);

        userCenterButton.addActionListener(e -> CenterFrame.getInstance(user));
    }

    protected void updateRecentPanelData() {
        // 清除现有组件中的旧数据
        recentPanel.removeAll();
        recentPanel.repaint();

        // 获取最新的查询记录
        List<String> listQuestion = RecentQueriesDAO.getRecentQueries(user.getId());
        JComboBox<String> questionComboBox = new JComboBox<>(listQuestion.toArray(new String[0]));
        questionComboBox.setBounds(320, 150, 360, 30);
        recentPanel.add(questionComboBox);

        JButton queryButton = addButton("Query", 700, 150, 80, 30, recentPanel);
        queryButton.addActionListener(e -> handleRecentQuery(questionComboBox));
        recentPanel.add(queryButton);
    }

    protected JButton addButton(String title, int x, int y, int width, int height, JPanel panel) {
        JButton button = new JButton(title);
        button.setBounds(x, y, width, height);
        panel.add(button);
        return button;
    }

    protected JLabel addLabel(String text, int x, int y, int width, int height, JPanel panel) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        panel.add(label);
        return label;
    }

    protected void handleRecentQuery(JComboBox<String> questionComboBox) {
        String selectedOption = (String) questionComboBox.getSelectedItem();
        if (selectedOption != null && !selectedOption.trim().isEmpty()) {
            switchPanel(mainPanel, "query");
            qArea.setText(selectedOption);
            // Optionally, call a method to perform the search or display the query results
            // e.g., performSearch(selectedOption);
        }
    }

}
