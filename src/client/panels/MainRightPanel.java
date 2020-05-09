package client.panels;

import javax.swing.*;

public class MainRightPanel extends JPanel {
    private HomePanel homePanel;
    private AskPanel askPanel;
    private ChatPanel chatPanel;
    private MePanel mePanel;

    public static MainRightPanel context;

    private MainRightPanel() {
        initComponents();
        initView();
    }

    public static MainRightPanel getInstance() {
        if(context == null) {
            context = new MainRightPanel();
            System.out.println("创建成功");
        }
        return context;
    }

    private void initComponents() {
        homePanel = new HomePanel();
        askPanel = new AskPanel();
        chatPanel = new ChatPanel();
        mePanel = new MePanel();
    }

    private void initView() {
        homePanel.setVisible(false);
        askPanel.setVisible(false);
        chatPanel.setVisible(false);
        mePanel.setVisible(false);
        add(homePanel);
        add(askPanel);
        add(chatPanel);
        add(mePanel);
    }

    public void showHomePanel() {
        homePanel.setVisible(true);
        askPanel.setVisible(false);
        chatPanel.setVisible(false);
        mePanel.setVisible(false);
    }

    public void showAskPanel() {
        homePanel.setVisible(false);
        askPanel.setVisible(true);
        chatPanel.setVisible(false);
        mePanel.setVisible(false);
    }

    public void showChatPanel() {
        homePanel.setVisible(false);
        askPanel.setVisible(false);
        chatPanel.setVisible(true);
        mePanel.setVisible(false);
    }

    public void showMePanel() {
        homePanel.setVisible(false);
        askPanel.setVisible(false);
        chatPanel.setVisible(false);
        mePanel.setVisible(true);
    }

}
