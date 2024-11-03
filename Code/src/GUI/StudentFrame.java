package GUI;

import backend.QA;
import backend.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class StudentFrame extends QAFrame implements Searchable {

    public StudentFrame(User user) {
        super(user);
        this.Frametitle = "Students QA_System";
        initializeFrameComponents();
    }

    @Override
    protected void initializeFrameComponents() {
        // Frame setup
        setTitle(Frametitle);

        // Panel setup
        mainPanel.add(queryPanel, "query");
        mainPanel.add(recentPanel, "recent");

        JToolBar toolBar = createCommonToolBar();
        add(toolBar, BorderLayout.NORTH);

        initializeQueryPanel(queryPanel);
        add(mainPanel);
        setVisible(true);
    }

    protected void initializeQueryPanel(JPanel queryPanel) {
        queryPanel.setLayout(null);

        userCenterButton = addButton(user.getName(), 10, 10, 100, 25, queryPanel);
        JLabel qLabel = addLabel("Query your question:", 150, 20, 200, 25, queryPanel);
        qLabel.setFont(font);

        noResultLabel = addLabel("No results found.", 250, 165, 200, 35, queryPanel);
        noResultLabel.setFont(font);
        noResultLabel.setVisible(false);

        qArea = createQueryTextArea();
        JScrollPane qScrollPane = createScrollPane(qArea, 150, 55, 700, 100);
        queryPanel.add(qScrollPane);

        JButton queryButton = addButton("Query", 145, 165, 80, 35, queryPanel);
        queryButton.addActionListener(e -> handleQuery());

        JButton quitButton = addButton("Quit", 20, 570, 80, 35, queryPanel);
        quitButton.addActionListener(e -> restartApplication());

        userCenterButton.addActionListener(e -> CenterFrame.getInstance(user));
    }

    private JTextArea createQueryTextArea() {
        JTextArea textArea = new JTextArea(5, 55);
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                }
            }
        });
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }

    private JScrollPane createScrollPane(JTextArea textArea, int x, int y, int width, int height) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(x, y, width, height);
        return scrollPane;
    }

    private void handleQuery() {
        clearErrorLabels(queryPanel, noResultLabel);
        if (qArea.getText().trim().isEmpty()) {
            removeScrollPaneIfExists(queryPanel);
            initializeQueryPanel(queryPanel);
            showLabel(queryPanel, noResultLabel);
            return;
        }
        System.out.printf("hello");
        QA[] qaResults = searchable(user.getId(), qArea.getText());
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        removeScrollPaneIfExists(queryPanel);
        clearErrorLabels(queryPanel, noResultLabel);

        // Check if results are available
        if (qaResults != null && qaResults.length > 0) {
            displayResults(queryPanel, qaResults, resultPanel);
        } else {
            removeScrollPaneIfExists(queryPanel);
            initializeQueryPanel(queryPanel);
            showLabel(queryPanel, noResultLabel);
        }

        qArea.setVisible(true);
        queryPanel.revalidate();
        queryPanel.repaint();
    }

    private void displayResults(JPanel queryPanel, QA[] qaResults, JPanel resultPanel) {
        initializeQueryPanel(queryPanel);
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(100);
        scrollPane.setName("scrollPane");
        generateQAResults(resultPanel, qaResults);
        queryPanel.add(scrollPane);
        scrollPane.setBounds(145, 215, 700, 400);
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(10));
        scrollPane.repaint();
    }

    @Override
    protected void handleRecentQuery(JComboBox<String> questionComboBox) {
        String selectedOption = (String) questionComboBox.getSelectedItem();
        switchPanel(mainPanel, "query");
        searchable(user.getId(), selectedOption);
        qArea.setText(selectedOption);
        handleQuery();
    }

    private void generateQAResults(JPanel panel, QA[] qaResults) {
        for (int i = 0; i < qaResults.length && qaResults[i] != null; i++) {
            JTextArea questionArea = createTextArea("Question" + (i + 1) + ": " + qaResults[i].getQuestion());
            JTextArea answerArea = createTextArea("Answer" + (i + 1) + ": " + qaResults[i].getAnswer());
            panel.add(questionArea);
            panel.add(answerArea);
        }
        panel.revalidate();
        panel.repaint();
    }

    private JTextArea createTextArea(String text) {
        JTextArea textArea = new JTextArea(3, 55);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setText(text);
        //textArea.setPreferredSize(new Dimension(650, Math.max(textArea.getLineCount() * 17, 120)));
        SwingUtilities.invokeLater(() -> {
            textArea.setPreferredSize(new Dimension(650, Math.max(textArea.getLineCount() * 17, 100)));
        });
        return textArea;
    }

    private void showLabel(JPanel panel, JLabel label) {
        panel.add(label);
        label.setVisible(true);
        panel.revalidate();
        panel.repaint();
    }

    private void removeScrollPaneIfExists(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JScrollPane) {
                panel.remove(comp);
            }
        }
    }

    @Override
    public QA[] searchable(int userid, String keyword) {
        return backend.QADAO.searchQA(userid, keyword);
    }
}
