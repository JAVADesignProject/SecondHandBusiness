package panels;

import components.Colors;
import components.MKButton;
import components.VerticalFlowLayout;
import frames.AboutFrame;
import listener.AbstractMouseListener;
import utils.FontUtil;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.awt.desktop.AboutEvent;
import java.awt.event.MouseEvent;

public class MePanel extends JPanel {
    private JPanel upPanel;
    private JPanel downPanel;
    private JLabel welcomeLabel;
    private JLabel infoLabel;
    private MKButton changePasswordButton;
    private MKButton clearCacheButton;
    private MKButton aboutButton;
    private MKButton logoutButton;
    private MKButton cartButton;


    public MePanel(){
        initComponents();
        initView();
        setListener();
        //context = this;
    }

    private void initComponents() {
        upPanel = new JPanel();
        downPanel = new JPanel();
//        upPanel.setBorder(new LineBorder(Colors.MAIN_COLOR));
//        downPanel.setBorder(new LineBorder(Colors.MAIN_COLOR));

        welcomeLabel = new JLabel("<html>欢 迎<br>MIKE</html>");
        infoLabel = new JLabel("我发布的     我购买的     我的评论");
        changePasswordButton = new MKButton("修改密码");
        clearCacheButton = new MKButton("清除缓存");
        aboutButton = new MKButton("关于");
        logoutButton = new MKButton("退出登录");
        cartButton = new MKButton("购物车");

        upPanel.setPreferredSize(new Dimension(840,325));
        upPanel.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);
        downPanel.setPreferredSize(new Dimension(840,325));
        downPanel.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);

        welcomeLabel.setFont(FontUtil.getDefaultFont(28));
        infoLabel.setFont(FontUtil.getDefaultFont(22));

        setButton(cartButton);
        setButton(changePasswordButton);
        setButton(clearCacheButton);
        setButton(aboutButton);
        setButton(logoutButton);
    }

    private void initView() {
        upPanel.add(welcomeLabel, BorderLayout.CENTER);
        upPanel.add(infoLabel);
        upPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 250, 20, false, false));
        downPanel.add(cartButton);
        downPanel.add(changePasswordButton);
        downPanel.add(clearCacheButton);
        downPanel.add(aboutButton);
        downPanel.add(logoutButton);
        downPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 330, 10, false, false));

        setBackground(Colors.WINDOW_BACKGROUND_LIGHT);
        setLayout(new BorderLayout());
        add(upPanel, BorderLayout.NORTH);
        add(downPanel, BorderLayout.SOUTH);
    }

    private void setButton (MKButton button) {
        button.setColor(Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER,Colors.MAIN_COLOR_DARKER);
        button.setPreferredSize(new Dimension(180,35));
        button.setFont(FontUtil.getDefaultFont(18));
        button.setForeground(Colors.FONT_WHITE);

    }

    private void setListener() {
        cartButton.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });

        logoutButton.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });

        clearCacheButton.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });

        aboutButton.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AboutFrame f = new AboutFrame();
                System.out.println("click");
            }
        });

        changePasswordButton.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
    }
}
