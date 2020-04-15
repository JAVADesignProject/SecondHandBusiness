package frames;

import components.VerticalFlowLayout;
import utils.FontUtil;

import javax.swing.*;
import java.awt.*;

public class AboutFrame extends JDialog {
    private static final int WINDOW_WIDTH = 300;
    private static final int WINDOW_HEIGHT = 300;

    JPanel panel;
    JLabel label1;
    JLabel label2;
    JLabel label3;

    public AboutFrame() {
        super(MainFrame.context, "关于",true);
        initComponents();
        initView();
        setVisible(true);
    }

    private void initView() {
        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.MIDDLE,70,25,false,false));
        setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(MainFrame.context);
        add(panel);
    }

    private void initComponents() {
        panel = new JPanel();
        label1 = new JLabel("欢迎使用氪氪二手");
        label2 = new JLabel("v1.0.0");
        label3 = new JLabel("©郭东骞 王克");
        label1.setFont(FontUtil.getDefaultFont(20));
        label2.setFont(FontUtil.getDefaultFont(20));
        label3.setFont(FontUtil.getDefaultFont(20));
    }

}
