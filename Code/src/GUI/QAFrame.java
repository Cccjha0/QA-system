package GUI;

import backend.User;

import javax.swing.*;
import java.awt.*;

public abstract class QAFrame extends JFrame{
    static final Font font = new Font("Arial", Font.PLAIN, 20);
    String Frametitle;
    User user;

    public QAFrame(String Frametitle) {
        this.Frametitle = Frametitle;
    }

    protected QAFrame() {
    }

    abstract void FrameComponents();

    public JLabel addLabel(String title, int x, int y, int width, int high, JPanel panel){
        JLabel label = new JLabel(title);
        label.setBounds(x, y, width, high);
        panel.add(label);
        return label;
    }
    public JTextField addTextField(int columns, int x, int y, int width, int high, JPanel panel){
        JTextField textField = new JTextField(columns);
        textField.setBounds(x, y, width, high);
        panel.add(textField);
        return textField;
    }
    public JPasswordField addPasswordField(int columns, int x, int y, int width, int high, JPanel panel){
        JPasswordField passwordField = new JPasswordField(columns);
        passwordField.setBounds(x, y, width, high);
        panel.add(passwordField);
        return passwordField;
    }
    public JButton addButton(String text, int x, int y, int width, int high, JPanel panel){
        JButton button = new JButton(text);
        button.setBounds(x, y, width, high);
        panel.add(button);
        return button;
    }
    public void clearErrorLabels(JPanel panel, JLabel... labels) {
        for (JLabel label : labels) {
            panel.remove(label);
        }
        panel.revalidate();
        panel.repaint();
    }
}
