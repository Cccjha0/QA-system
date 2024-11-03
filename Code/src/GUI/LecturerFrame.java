package GUI;

import backend.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * LecturerFrame class extending StudentFrame and implementing Editable.
 * This frame allows lecturers to input questions and answers in a QA system.
 */
public class LecturerFrame extends StudentFrame implements Editable {

    /**
     * Constructor for LecturerFrame, setting the frame title.
     * @param user The User instance associated with the lecturer.
     */
    public LecturerFrame(User user) {
        super(user);     
    }

    /**
     * Initializes frame components by setting up the toolbar and input panels.
     */
    @Override
    public void initializeFrameComponents() {
        this.Frametitle = "Lectures QA_System";
        setTitle(Frametitle);
        initializeToolbarComponents();
        initializePanels();
        setVisible(true);
    }

    /**
     * Sets up the toolbar with an Input button for switching to the input panel.
     */
    private void initializeToolbarComponents() {
        JToolBar toolBar = createCommonToolBar(); // Common toolbar method
        JButton inputButton = createButton("Input", e -> switchPanel(mainPanel, "input"));
        inputButton.setPreferredSize(new Dimension(100, 30));
        toolBar.add(inputButton, 0); // Adds the Input button at the first position
        add(toolBar, BorderLayout.NORTH);
    }

    /**
     * Initializes the panels used in this frame, including query and input panels.
     */
    private void initializePanels() {
        JPanel inputPanel = new JPanel();
        mainPanel.add(queryPanel, "query"); // Adds the query panel to mainPanel
        mainPanel.add(inputPanel, "input"); // Adds the input panel to mainPanel
        mainPanel.add(recentPanel, "recent"); // Adds the recent panel to mainPanel
        add(mainPanel); // Adds mainPanel to the frame

        initializeQueryPanel(queryPanel); // Initializes components for query panel
        initializeInputPanelComponents(inputPanel); // Initializes components for input panel
    }

    /**
     * Sets up the input panel components, allowing lecturers to input questions and answers.
     * @param inputPanel The panel where components are added.
     */
    @Override
    public void initializeInputPanelComponents(JPanel inputPanel) {
        inputPanel.setLayout(null);

        // User center button
        userCenterButton = addButton(user.getName(), 10, 10, 100, 25, inputPanel);

        // Labels for question and answer input
        JLabel qLabel = addLabel("Input your question:", 150, 20, 200, 25, inputPanel);
        qLabel.setFont(font);
        JLabel aLabel = addLabel("Input your answer:", 150, 170, 200, 25, inputPanel);
        aLabel.setFont(font);

        // Text areas with scroll panes for question and answer input
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

        // Input button to handle submission of question and answer
        JButton inputButton = new JButton("Input");
        inputButton.setBounds(770, 555, 80, 35);
        inputPanel.add(inputButton);
        inputButton.addActionListener(e -> handleInputAction(qArea, aArea, inputPanel));

        // Quit button to restart the application
        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(20, 570, 80, 35);
        inputPanel.add(quitButton);
        quitButton.addActionListener(e -> restartApplication());

        // User center button action to open the CenterFrame
        userCenterButton.addActionListener(e -> CenterFrame.getInstance(user));
    }

    /**
     * Handles the input action, checking for empty fields and saving the question-answer pair.
     * @param qArea The text area for question input.
     * @param aArea The text area for answer input.
     * @param inputPanel The panel where the input occurs.
     */
    private void handleInputAction(JTextArea qArea, JTextArea aArea, JPanel inputPanel) {
        QA qa = new QA(qArea.getText(), aArea.getText(), user.getId());

        // Check if the question area is empty
        if (qArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please input question", "Input Error", JOptionPane.INFORMATION_MESSAGE);
        } 
        // Check if the answer area is empty
        else if (aArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please input answer", "Input Error", JOptionPane.INFORMATION_MESSAGE);
        } 
        // Attempt to save the question and answer, showing success or failure message
        else if (backend.QADAO.insertQA(qa)) {
            JOptionPane.showMessageDialog(null, "Input Successful", "Input Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Input Failed", "Input Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
