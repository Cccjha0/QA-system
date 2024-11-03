package GUI;

import backend.QA;
import backend.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * StudentFrame class provides a user interface for students to query questions
 * and view recent queries. It extends QAFrame and implements the Searchable interface.
 */
public class StudentFrame extends QAFrame implements Searchable {

    /**
     * Constructor to initialize the StudentFrame with the user information.
     * @param user The current user (student) using the frame
     */
    public StudentFrame(User user) {
        super(user);
        this.Frametitle = "Students QA_System";
        initializeFrameComponents();
    }

    /**
     * Sets up the main components of the frame, including the toolbar and main panel.
     */
    @Override
    protected void initializeFrameComponents() {
        setTitle(Frametitle);

        // Adding query and recent panels to the main panel
        mainPanel.add(queryPanel, "query");
        mainPanel.add(recentPanel, "recent");

        // Create and add a toolbar at the top
        JToolBar toolBar = createCommonToolBar();
        add(toolBar, BorderLayout.NORTH);

        // Initialize the query panel for user input
        initializeQueryPanel(queryPanel);
        add(mainPanel);
        setVisible(true);
    }

    /**
     * Initializes the query panel, setting up labels, text areas, and buttons.
     * @param queryPanel The panel where query components will be added
     */
    protected void initializeQueryPanel(JPanel queryPanel) {
        queryPanel.setLayout(null);

        // User center button and label setup
        userCenterButton = addButton(user.getName(), 10, 10, 100, 25, queryPanel);
        JLabel qLabel = addLabel("Query your question:", 150, 20, 200, 25, queryPanel);
        qLabel.setFont(font);

        // No result label setup, initially hidden
        noResultLabel = addLabel("No results found.", 250, 165, 200, 35, queryPanel);
        noResultLabel.setFont(font);
        noResultLabel.setVisible(false);

        // Query input area setup with a scroll pane
        qArea = createQueryTextArea();
        JScrollPane qScrollPane = createScrollPane(qArea, 150, 55, 700, 100);
        queryPanel.add(qScrollPane);

        // Query and Quit button setup
        JButton queryButton = addButton("Query", 145, 165, 80, 35, queryPanel);
        queryButton.addActionListener(e -> handleQuery());

        JButton quitButton = addButton("Quit", 20, 570, 80, 35, queryPanel);
        quitButton.addActionListener(e -> restartApplication());

        userCenterButton.addActionListener(e -> CenterFrame.getInstance(user));
    }

    /**
     * Creates and returns a text area for user queries, with specific settings.
     * @return Configured JTextArea for querying
     */
    private JTextArea createQueryTextArea() {
        JTextArea textArea = new JTextArea(5, 55);
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Prevents Enter key from creating a new line
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                }
            }
        });
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }

    /**
     * Creates and returns a JScrollPane for the provided text area with specific bounds.
     * @param textArea Text area to be added to the scroll pane
     * @param x X-coordinate of the scroll pane
     * @param y Y-coordinate of the scroll pane
     * @param width Width of the scroll pane
     * @param height Height of the scroll pane
     * @return Configured JScrollPane
     */
    private JScrollPane createScrollPane(JTextArea textArea, int x, int y, int width, int height) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(x, y, width, height);
        return scrollPane;
    }

    /**
     * Handles the query action, checking if input is empty and displaying results if available.
     */
    private void handleQuery() {
        clearErrorLabels(queryPanel, noResultLabel);

        // Check if the query input is empty
        if (qArea.getText().trim().isEmpty()) {
            removeScrollPaneIfExists(queryPanel);
            initializeQueryPanel(queryPanel);
            showLabel(queryPanel, noResultLabel);
            return;
        }

        // Search for query results
        QA[] qaResults = searchable(user.getId(), qArea.getText());
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        removeScrollPaneIfExists(queryPanel);
        clearErrorLabels(queryPanel, noResultLabel);

        // Display results if found, otherwise show "No results" label
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

    /**
     * Displays the search results in a scroll pane within the query panel.
     * @param queryPanel Panel to display results in
     * @param qaResults Array of QA results to display
     * @param resultPanel Panel containing result components
     */
    private void displayResults(JPanel queryPanel, QA[] qaResults, JPanel resultPanel) {
        initializeQueryPanel(queryPanel);
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setName("scrollPane");
        generateQAResults(resultPanel, qaResults);
        queryPanel.add(scrollPane);
        scrollPane.setBounds(145, 215, 700, 400);
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(10));
        scrollPane.repaint();
    }

    /**
     * Processes a recent query, displaying results for the selected recent question.
     * @param questionComboBox Combo box containing recent queries
     */
    @Override
    protected void handleRecentQuery(JComboBox<String> questionComboBox) {
        String selectedOption = (String) questionComboBox.getSelectedItem();
        switchPanel(mainPanel, "query");      
        searchable(user.getId(), selectedOption);
        qArea.setText(selectedOption);
        handleQuery();
    }

    /**
     * Generates QA results by creating text areas for each question and answer and adding them to the panel.
     * @param panel The panel where results will be added
     * @param qaResults Array of QA objects containing question and answer data
     */
    private void generateQAResults(JPanel panel, QA[] qaResults) {
        for (int i = 0; i < qaResults.length && qaResults[i] != null; i++) {
            JTextArea questionArea = createTextArea("Question" + (i + 1) + ": " + qaResults[i].getQuestion());
            JTextArea answerArea = createTextArea("Answer" + (i + 1) + ": " + qaResults[i].getAnswer());
            panel.add(questionArea);
            panel.add(answerArea);

            // Divider line between questions
            if (i < qaResults.length - 1 && qaResults[i + 1] != null) {
                panel.add(new JTextArea("-----------------------------------------------------------------------------------------------------------------------------"));
            }
        }
        panel.revalidate();
        panel.repaint();
    }

    /**
     * Creates and returns a read-only JTextArea displaying the specified text.
     * @param text Text to display in the text area
     * @return Configured JTextArea
     */
    private JTextArea createTextArea(String text) {
        JTextArea textArea = new JTextArea(3, 55);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setText(text);

        // Adjust height based on line count for better visibility
        SwingUtilities.invokeLater(() -> {
            textArea.setPreferredSize(new Dimension(650, Math.max(textArea.getLineCount() * 17, 100)));
        });
        return textArea;
    }

    /**
     * Shows a specified label in the provided panel.
     * @param panel The panel to show the label in
     * @param label The label to be shown
     */
    private void showLabel(JPanel panel, JLabel label) {
        panel.add(label);
        label.setVisible(true);
        panel.revalidate();
        panel.repaint();
    }

    /**
     * Removes any existing JScrollPane components from the specified panel.
     * @param panel The panel to clear of JScrollPanes
     */
    private void removeScrollPaneIfExists(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JScrollPane) {
                panel.remove(comp);
            }
        }
    }

    /**
     * Searches for QA records based on user ID and keyword, retrieving relevant results.
     * @param userid The user ID for the search
     * @param keyword The keyword to search for
     * @return An array of QA results matching the search criteria
     */
    @Override
    public QA[] searchable(int userid, String keyword) {
        return backend.QADAO.searchQA(userid, keyword);
    }
}
