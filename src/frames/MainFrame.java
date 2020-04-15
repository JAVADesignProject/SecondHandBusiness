package frames;

import panels.MainLeftPanel;
import panels.MainRightPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 650;

    private MainLeftPanel mainLeftPanel;
    private MainRightPanel mainRightPanel;

    public static MainFrame context;

    public MainFrame() {
        context = this;
        initComponents();
        initView();
        centerScreen();
//        initResource();

//         连接WebSocket
//        startWebSocket();
    }

    public MainFrame getContext() {
        return context;
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainLeftPanel = new MainLeftPanel();
        mainLeftPanel.setPreferredSize(new Dimension(60, WINDOW_HEIGHT));
        mainRightPanel = MainRightPanel.getInstance();
    }

    private void initView() {
        setResizable(false);
        setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        add(mainLeftPanel, BorderLayout.WEST);
        add(mainRightPanel, BorderLayout.CENTER);
    }

    private void centerScreen() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocation((tk.getScreenSize().width - WINDOW_WIDTH) / 2,
                (tk.getScreenSize().height - WINDOW_HEIGHT) / 2);
    }
}
