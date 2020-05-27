package client.panels;

import javax.swing.*;
import client.components.Colors;
import client.components.MKButton;
import client.frames.MainFrame;
import client.frames.PostFrame;
import client.listener.AbstractMouseListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainLeftPanel extends JPanel {
    private JLabel homeLabel;               //主页
    private JLabel askLabel;                //求购
    private MKButton postButton;            //发布
    private JLabel chatLabel;               //消息
    private JLabel meLabel;                 //我的
    private ImageIcon homeIconActive;
    private ImageIcon homeIconNormal;
    private ImageIcon askIconActive;
    private ImageIcon askIconNormal;
    private ImageIcon chatIconActive;
    private ImageIcon chatIconNormal;
    private ImageIcon meIconActive;
    private ImageIcon meIconNormal;

    public MainLeftPanel() {
        initComponents();
        initView();
        MainRightPanel.getInstance().showHomePanel();
    }

    private void initComponents() {
        homeIconActive = new ImageIcon("res/image/home_active.png");
        homeIconNormal = new ImageIcon("res/image/home_normal.png");
        homeLabel = new JLabel();
        homeLabel.setIcon(homeIconActive);
        homeLabel.addMouseListener(new MenuClickListener());
        homeLabel.setHorizontalAlignment(SwingUtilities.CENTER);

        askIconActive = new ImageIcon("res/image/ask_active.png");
        askIconNormal = new ImageIcon("res/image/ask_normal.png");
        askLabel = new JLabel();
        askLabel.setIcon(askIconNormal);
        askLabel.addMouseListener(new MenuClickListener());
        askLabel.setHorizontalAlignment(SwingUtilities.CENTER);

        //TODO 发布按钮
        postButton = new MKButton(null, Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER, new ImageIcon("res/image/add.png"));
        postButton.addMouseListener(new MenuClickListener());

        chatIconActive = new ImageIcon("res/image/chat_active.png");
        chatIconNormal = new ImageIcon("res/image/chat_normal.png");
        chatLabel = new JLabel();
        chatLabel.setIcon(chatIconNormal);
        chatLabel.addMouseListener(new MenuClickListener());
        chatLabel.setHorizontalAlignment(SwingUtilities.CENTER);

        meIconActive = new ImageIcon("res/image/me_active.png");
        meIconNormal = new ImageIcon("res/image/me_normal.png");
        meLabel = new JLabel();
        meLabel.setIcon(meIconNormal);
        meLabel.addMouseListener(new MenuClickListener());
        meLabel.setHorizontalAlignment(SwingUtilities.CENTER);
}

    private void initView() {
        this.setBackground(Colors.DARK);
        this.setLayout(new GridLayout(5,1));
        add(homeLabel);
        add(askLabel);
        add(postButton);
        add(chatLabel);
        add(meLabel);
    }

    class MenuClickListener extends AbstractMouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            if (e.getComponent() == homeLabel) {
                homeLabel.setIcon(homeIconActive);
                askLabel.setIcon(askIconNormal);
                chatLabel.setIcon(chatIconNormal);
                meLabel.setIcon(meIconNormal);
                MainRightPanel.getInstance().showHomePanel();
            } else if (e.getComponent() == askLabel) {
                homeLabel.setIcon(homeIconNormal);
                askLabel.setIcon(askIconActive);
                chatLabel.setIcon(chatIconNormal);
                meLabel.setIcon(meIconNormal);
                MainRightPanel.getInstance().showAskPanel();
            } else if (e.getComponent() == postButton) {
                MainFrame.context.setEnabled(false);
                PostFrame frame = new PostFrame();
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        MainFrame.context.setEnabled(true);
                    }
                });
            } else if (e.getComponent() == chatLabel) {
                homeLabel.setIcon(homeIconNormal);
                askLabel.setIcon(askIconNormal);
                chatLabel.setIcon(chatIconActive);
                meLabel.setIcon(meIconNormal);
                MainRightPanel.getInstance().showChatPanel();
            } else if (e.getComponent() == meLabel) {
                homeLabel.setIcon(homeIconNormal);
                askLabel.setIcon(askIconNormal);
                chatLabel.setIcon(chatIconNormal);
                meLabel.setIcon(meIconActive);
                MainRightPanel.getInstance().showMePanel();
            }
        }
    }
}
