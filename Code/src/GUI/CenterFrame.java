package GUI;

import backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CenterFrame extends JFrame {

    private User user;
    private static CenterFrame instance = null; // Singleton instance
    private JLabel username = new JLabel();
    private JLabel userLabel = new JLabel();
    private JLabel idLabel = new JLabel();
    private JLabel ID = new JLabel();
    private JButton resetButton = new JButton("Reset Password");

    private CenterFrame(User user) {
        this.user = user;
        FrameComponents();
    }

    public static CenterFrame getInstance(User user) {
        if (instance == null) {
            instance = new CenterFrame(user);
            instance.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    instance = null; // 当窗口关闭时，将实例设为 null
                    System.out.print("hello");
                }
            });
        }
        return instance;
    }

    void FrameComponents() {
        setSize(300, 200);
        setTitle("Personal Center");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation((screenSize.width - 300) / 2, (screenSize.height - 200) / 2);

        JPanel CenterPanel = new JPanel();
        CenterPanel.setLayout(null);
        add(CenterPanel);
        userLabel.setText("User:");
        username.setText(user.getName());
        username.setBounds(110, 20, 200, 30);
        //username.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setBounds(50,20,80,30);
        idLabel.setText("ID:");
        idLabel.setBounds(50,70,80,30);
        ID.setText(""+user.getId());
        ID.setBounds(110,70,200,30);
        setVisible(true);
        resetButton.setBounds(80, 120, 140, 30);
        resetButton.addActionListener(e -> {
            dispose();
            new ResetPasswordFrame(user);
        });
        CenterPanel.add(resetButton);
        CenterPanel.add(userLabel);
        CenterPanel.add(username);
        CenterPanel.add(idLabel);
        CenterPanel.add(ID);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

    }
    public static void main(String[] args) {
        new CenterFrame(new User("Ponder","123",false));
    }
}
