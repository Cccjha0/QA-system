package GUI;

import backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class baseFrame extends JFrame{
    String Frametitle;
    protected User user;
    static final Font font = new Font("Arial", Font.PLAIN, 20);
    public baseFrame(String Frametitle) {
        this.Frametitle = Frametitle;
        setTitle(Frametitle);
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());
        initializeComponents();
        layoutComponents();
    }
    public baseFrame(User user) {
        this.user =user;
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());
        initializeComponents();
        layoutComponents();
    }

    protected abstract void layoutComponents();
    protected abstract void initializeComponents();
    public JLabel addLabel(String title, int x, int y, int width, int high){
        JLabel label = new JLabel(title);
        label.setBounds(x, y, width, high);
        return label;
    }
    public JTextField addTextField(int columns, int x, int y, int width, int high){
        JTextField textField = new JTextField(columns);
        textField.setBounds(x, y, width, high);
        return textField;
    }
    public JPasswordField addPasswordField(int columns, int x, int y, int width, int high){
        JPasswordField passwordField = new JPasswordField(columns);
        passwordField.setBounds(x, y, width, high);
        return passwordField;
    }
    public JButton addButton(String text, int x, int y, int width, int high ){
        JButton button = new JButton(text);
        button.setBounds(x, y, width, high);
        return button;
    }
    public void clearErrorLabels(JPanel panel, JLabel... labels) {
        for (JLabel label : labels) {
            label.setVisible(false);
        }
        panel.revalidate();
        panel.repaint();
    }
    public void addComponent(JPanel panel,Component... components){
        for (Component component : components){
            panel.add(component);
        }
        panel.revalidate();
        panel.repaint();
    }

}


