package frames;

import panels.LeftPanel;
import panels.RightPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 650;

    private LeftPanel leftPanel;
    private RightPanel rightPanel;

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

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        leftPanel = new LeftPanel();
        leftPanel.setPreferredSize(new Dimension(60, WINDOW_HEIGHT));
        rightPanel = new RightPanel();
    }

    private void initView() {
        setResizable(false);
        setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }

    private void centerScreen() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocation((tk.getScreenSize().width - WINDOW_WIDTH) / 2,
                (tk.getScreenSize().height - WINDOW_HEIGHT) / 2);
    }
}
