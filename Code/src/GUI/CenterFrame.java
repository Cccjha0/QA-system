package GUI;

import backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CenterFrame extends baseFrame {

    private static CenterFrame instance = null; // Singleton instance
    private JLabel userLable = new JLabel(user.getName());
    private static final Font namefont = new Font("Courier New", Font.PLAIN, 30);
    private JButton resetButton = new JButton("ResetPassword");

    private CenterFrame(User user) {
        super(user);
        Frametitle = "Personal Center";
    }

    @Override
    protected void layoutComponents() {
        userLable.setBounds(0,20,300,60);
        userLable.setFont(namefont);
        userLable.setHorizontalAlignment(SwingConstants.CENTER);
        userLable.setVerticalAlignment(SwingConstants.CENTER);
        add(userLable);
        resetButton.setBounds(90,120,120,30);
        resetButton.addActionListener(e -> {
            new ResetPasswordFrame(user);
            dispose();
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose(); // 仅隐藏窗口
            }
        });
    }

    @Override
    protected void initializeComponents() {
        this.setTitle(Frametitle);
        this.setSize(300, 300);
//        Toolkit kit = Toolkit.getDefaulccctToolkit();
//        Dimension screenSize = kit.getScreenSize();
        this.setLocation(0,0);
        this.setVisible(true);
        setLayout(null);
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
}