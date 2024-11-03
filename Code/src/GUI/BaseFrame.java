package GUI;

import backend.User;

import javax.swing.*;
import java.awt.*;

/**
 * Abstract base frame class that provides common functionalities for GUI frames.
 */
public abstract class BaseFrame extends JFrame {
    // Default font for labels and other components
    static final Font font = new Font("Arial", Font.PLAIN, 20);
    JMenuBar menuBar = new JMenuBar(); // Menu bar for the frame
    String Frametitle; // Title of the frame
    User user; // User object associated with this frame

    /**
     * Constructor to initialize the frame with a specific title.
     * @param Frametitle The title of the frame.
     */
    public BaseFrame(String Frametitle) {
        this.Frametitle = Frametitle;
    }

    /**
     * Default constructor for BaseFrame.
     */
    protected BaseFrame() {
    }

    /**
     * Abstract method to initialize frame components.
     * Each subclass should implement this method to set up its components.
     */
    abstract void initializeFrameComponents();

    /**
     * Helper method to add a JLabel to the specified panel.
     * @param title The text of the label.
     * @param x X coordinate of the label.
     * @param y Y coordinate of the label.
     * @param width Width of the label.
     * @param high Height of the label.
     * @param panel The panel to add the label to.
     * @return The created JLabel.
     */
    protected JLabel addLabel(String title, int x, int y, int width, int high, JPanel panel) {
        JLabel label = new JLabel(title);
        label.setBounds(x, y, width, high);
        panel.add(label);
        return label;
    }

    /**
     * Helper method to add a JTextField to the specified panel.
     * @param columns Number of columns in the text field.
     * @param x X coordinate of the text field.
     * @param y Y coordinate of the text field.
     * @param width Width of the text field.
     * @param high Height of the text field.
     * @param panel The panel to add the text field to.
     * @return The created JTextField.
     */
    protected JTextField addTextField(int columns, int x, int y, int width, int high, JPanel panel) {
        JTextField textField = new JTextField(columns);
        textField.setBounds(x, y, width, high);
        panel.add(textField);
        return textField;
    }

    /**
     * Helper method to add a JPasswordField to the specified panel.
     * @param columns Number of columns in the password field.
     * @param x X coordinate of the password field.
     * @param y Y coordinate of the password field.
     * @param width Width of the password field.
     * @param high Height of the password field.
     * @param panel The panel to add the password field to.
     * @return The created JPasswordField.
     */
    protected JPasswordField addPasswordField(int columns, int x, int y, int width, int high, JPanel panel) {
        JPasswordField passwordField = new JPasswordField(columns);
        passwordField.setBounds(x, y, width, high);
        panel.add(passwordField);
        return passwordField;
    }

    /**
     * Helper method to add a JButton to the specified panel.
     * @param text The text of the button.
     * @param x X coordinate of the button.
     * @param y Y coordinate of the button.
     * @param width Width of the button.
     * @param high Height of the button.
     * @param panel The panel to add the button to.
     * @return The created JButton.
     */
    protected JButton addButton(String text, int x, int y, int width, int high, JPanel panel) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, high);
        panel.add(button);
        return button;
    }

    /**
     * Clears the specified error labels from the panel and refreshes the display.
     * @param panel The panel containing the labels.
     * @param labels The labels to remove from the panel.
     */
    protected void clearErrorLabels(JPanel panel, JLabel... labels) {
        for (JLabel label : labels) {
            panel.remove(label);
        }
        panel.revalidate(); // Revalidates the panel layout
        panel.repaint(); // Repaints the panel
    }

    /**
     * Restarts the application by launching a new instance of the main class.
     * This method exits the current process and starts a new one.
     */
    static void restartApplication() {
        try {
            // Get the Java runtime path and the classpath
            String javaBin = System.getProperty("java.home") + "/bin/java";
            String classPath = System.getProperty("java.class.path");
            String className = "APPMain";

            // Build command line arguments to restart the application
            ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classPath, className);
            builder.start(); // Starts a new process for the application

            System.exit(0); // Exits the current application
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
