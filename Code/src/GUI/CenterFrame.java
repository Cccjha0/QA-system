package GUI;

import backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CenterFrame extends JFrame{
    private User user;
    private JLabel userlabel =new JLabel();
    private JButton resetButton = new JButton("Reset Password");
    public CenterFrame(User user){
        this.user=user;
        FrameComponents();
    }
    void FrameComponents() {
        setSize(300, 200);
        setTitle("Personal Center");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation((screenSize.width - 300) / 2, (screenSize.height - 200) / 2);

        JPanel CenterPanel = new JPanel();
        CenterPanel.setLayout(null);
        add(CenterPanel);
        userlabel.setText(user.getName());
        userlabel.setBounds(0, 40, 300, 40);
        userlabel.setHorizontalAlignment(SwingConstants.CENTER);
        setVisible(true);
        resetButton.setBounds(50, 100, 200, 30);
        resetButton.addActionListener(e -> {
            dispose();
            new ResetPasswordFrame(user);
        });
        CenterPanel.add(resetButton);
        CenterPanel.add(userlabel);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                    dispose();
            }
        });

    }

}