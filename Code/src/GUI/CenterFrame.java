package GUI;

import backend.User;

import javax.swing.*;
import java.awt.*;

public class CenterFrame extends QAFrame{
    private User user;
    JFrame CenterFrame;
    int fatherx;
    int fathery;
    public CenterFrame(User user,int fatherx,int fathery){
        this.user=user;
        Frametitle="Personal Center";
        this.fatherx=fatherx;
        this.fathery=fathery;
        FrameComponents();
    }
    @Override
    void FrameComponents() {
        CenterFrame = new JFrame(Frametitle);
        CenterFrame.setSize(350, 400);
        CenterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        CenterFrame.setLocation(fatherx-350,fathery);
        PersonalCenterPanelComponents(CenterFrame,user);
        CenterFrame.setVisible(true);
    }
    private void PersonalCenterPanelComponents(JFrame jFrame,User user){
        JPanel CenterPanel = new JPanel();
        CenterPanel.setLayout(null);
        jFrame.add(CenterPanel);
        JLabel nameLable = addLabel(user.getName(),75,40,200,60,CenterPanel);
        nameLable.setFont(font);
        JLabel idLanle = addLabel("ID"+user.getId()+"感觉有点空啊，功能太少了，不如砍了",30,120,200,30,CenterPanel);
        JButton resetButton = addButton("Reset Password",80,200,190,30,CenterPanel);
        JButton backButtom = addButton("Back",80,240,100,30,CenterPanel);

        resetButton.addActionListener(e -> {
            new  ResetPasswordFrame(user);
        });
        backButtom.addActionListener(e -> {
            jFrame.dispose();
        });
    }
}
