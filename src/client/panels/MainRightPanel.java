package client.panels;

import javax.swing.*;

public class MainRightPanel extends JPanel {

    public static MainRightPanel context;

    public static MainRightPanel getInstance() {
        if(context == null) {
            context = new MainRightPanel();
            System.out.println("创建成功");
        }
        return context;
    }

    public void showHomePanel() {
        removeAll();
        updateUI();
        HomePanel homePanel = new HomePanel();
        add(homePanel);
    }

    public void showAskPanel() {
        removeAll();
        updateUI();
        AskPanel askPanel = new AskPanel();
        add(askPanel);
    }

    public void showChatPanel() {
        removeAll();
        updateUI();
        ChatPanel chatPanel = new ChatPanel();
        add(chatPanel);
    }

    public void showMePanel() {
        removeAll();
        updateUI();
        MePanel mePanel = new MePanel();
        add(mePanel);
    }

}
