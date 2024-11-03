package GUI;

import backend.User;

import javax.swing.*;
import java.awt.*;

public abstract class BaseFrame extends JFrame{
    static final Font font = new Font("Arial", Font.PLAIN, 20);
    JMenuBar menuBar = new JMenuBar();
    String Frametitle;
    User user;

    public BaseFrame(String Frametitle) {
        this.Frametitle = Frametitle;
    }

    protected BaseFrame() {
    }

    abstract void initializeFrameComponents();

    protected JLabel addLabel(String title, int x, int y, int width, int high, JPanel panel){
        JLabel label = new JLabel(title);
        label.setBounds(x, y, width, high);
        panel.add(label);
        return label;
    }
    protected JTextField addTextField(int columns, int x, int y, int width, int high, JPanel panel){
        JTextField textField = new JTextField(columns);
        textField.setBounds(x, y, width, high);
        panel.add(textField);
        return textField;
    }
    protected JPasswordField addPasswordField(int columns, int x, int y, int width, int high, JPanel panel){
        JPasswordField passwordField = new JPasswordField(columns);
        passwordField.setBounds(x, y, width, high);
        panel.add(passwordField);
        return passwordField;
    }
    protected JButton addButton(String text, int x, int y, int width, int high, JPanel panel){
        JButton button = new JButton(text);
        button.setBounds(x, y, width, high);
        panel.add(button);
        return button;
    }
    protected void clearErrorLabels(JPanel panel, JLabel... labels) {
        for (JLabel label : labels) {
            panel.remove(label);
        }
        panel.revalidate();
        panel.repaint();
    }
    static void restartApplication() {
        try {
            // 获取当前 Java 运行时和类路径
            String javaBin = System.getProperty("java.home") + "/bin/java";
            String classPath = System.getProperty("java.class.path");
            String className = "APPMain";

            // 构建命令行参数，重新启动当前程序
            ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classPath, className);
            builder.start(); // 启动新进程

            System.exit(0); // 退出当前程序
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
