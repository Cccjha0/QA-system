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
 * Abstract class QAFrame provides a framework for creating a Question-Answer (QA) system GUI.
 * This class handles initializing the main application frame, setting up panels for querying
 * and viewing recent searches, and switching between panels.
 */
public abstract class QAFrame extends BaseFrame {

    // Fields for storing the current user, main panel, and individual sub-panels
    protected User user;
    protected JPanel mainPanel = new JPanel(new CardLayout());
    protected JPanel queryPanel = new JPanel();
    protected JPanel recentPanel = new JPanel();
    protected JLabel noResultLabel;
    protected JTextArea qArea;
    protected JLabel recentQueriesLabel;
    protected JButton userCenterButton;
    protected Font font = new Font("Arial", Font.PLAIN, 20);

    /**
     * Constructor initializes the QAFrame with the given User object and sets up the frame.
     * @param user The current user using the QA system
     */
    public QAFrame(User user) {
        this.user = user;
        initializeFrame();
    }

    /**
     * Initializes the main frame, setting up properties and adding a user button to the menu bar.
     */
    protected void initializeFrame() {
        setTitle("QA System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation((screenSize.width - 1000) / 2, (screenSize.height - 700) / 2);

        // Add a user icon button to the menu bar that opens the CenterFrame when clicked
        FlatButton usersButton = new FlatButton();
        usersButton.setIcon(new FlatSVGIcon("..\\Code\\resources\\users.svg"));
        usersButton.setButtonType(FlatButton.ButtonType.toolBarButton);
        usersButton.setFocusable(false);
        usersButton.addActionListener(e -> CenterFrame.getInstance(user));
        menuBar.add(Box.createGlue());
        menuBar.add(usersButton);
    }

    /**
     * Creates a common toolbar with "Query" and "Recent Search" buttons.
     * @return A JToolBar containing the buttons for query and recent search actions
     */
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

    /**
     * Creates a JButton with the specified title and action listener.
     * @param title The button's title text
     * @param listener The action listener for button clicks
     * @return The created JButton
     */
    protected JButton createButton(String title, ActionListener listener) {
        JButton button = new JButton(title);
        button.addActionListener(listener);
        return button;
    }

    /**
     * Switches the visible panel within the mainPanel based on the specified name.
     * @param panel The panel container with CardLayout
     * @param name The name of the panel to display
     */
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

    /**
     * Initializes the "Recent Search" panel, adding components for recent queries, quit button, and user center access.
     */
    protected void initializeRecentPanel() {
        recentPanel.setLayout(null);
        userCenterButton = addButton(user.getName(), 10, 10, 100, 25, recentPanel);
        recentQueriesLabel = new JLabel("Recent Queries");
        recentQueriesLabel.setBounds(0, 50, 1000, 60);
        recentQueriesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        recentQueriesLabel.setFont(font);
        recentPanel.add(recentQueriesLabel);

        updateRecentPanelData(); // Update data when initializing

        JButton quitButton = addButton("Quit", 20, 570, 80, 35, recentPanel);
        quitButton.addActionListener(e -> restartApplication());
        recentPanel.add(quitButton);

        userCenterButton.addActionListener(e -> CenterFrame.getInstance(user));
    }

    /**
     * Updates the "Recent Search" panel with the latest query data.
     * Fetches recent queries from the database and displays them in a combo box.
     */
    protected void updateRecentPanelData() {
        // Clear existing components with old data
        recentPanel.removeAll();
        recentPanel.repaint();

        // Get the latest query records
        List<String> listQuestion = RecentQueriesDAO.getRecentQueries(user.getId());
        JComboBox<String> questionComboBox = new JComboBox<>(listQuestion.toArray(new String[0]));
        questionComboBox.setBounds(320, 150, 360, 30);
        recentPanel.add(questionComboBox);

        JButton queryButton = addButton("Query", 700, 150, 80, 30, recentPanel);
        queryButton.addActionListener(e -> handleRecentQuery(questionComboBox));
        recentPanel.add(queryButton);
    }

    /**
     * Adds a button to the specified panel at given coordinates with specified dimensions.
     * @param title The button's title text
     * @param x X-coordinate
     * @param y Y-coordinate
     * @param width Button width
     * @param height Button height
     * @param panel The panel to which the button is added
     * @return The created JButton
     */
    protected JButton addButton(String title, int x, int y, int width, int height, JPanel panel) {
        JButton button = new JButton(title);
        button.setBounds(x, y, width, height);
        panel.add(button);
        return button;
    }

    /**
     * Adds a label to the specified panel at given coordinates with specified dimensions.
     * @param text The label's text
     * @param x X-coordinate
     * @param y Y-coordinate
     * @param width Label width
     * @param height Label height
     * @param panel The panel to which the label is added
     * @return The created JLabel
     */
    protected JLabel addLabel(String text, int x, int y, int width, int height, JPanel panel) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        panel.add(label);
        return label;
    }

    /**
     * Handles a recent query selection by switching to the query panel and populating the query area with the selected text.
     * @param questionComboBox Combo box containing recent queries
     */
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
