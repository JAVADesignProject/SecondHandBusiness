package frames;

import panels.LeftPanel;
import panels.RightPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static int DEFAULT_WIDTH = 900;
    public static int DEFAULT_HEIGHT = 650;

    public int currentWindowWidth = DEFAULT_WIDTH;
    public int currentWindowHeight = DEFAULT_HEIGHT;

    private LeftPanel leftPanel;
    private RightPanel rightPanel;

    private static MainFrame context;

    public MainFrame()
    {
        context = this;
        initComponents();
        initView();
        centerScreen();
//        initResource();

//         连接WebSocket
//        startWebSocket();
    }
    private void initComponents()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        leftPanel = new LeftPanel();
        leftPanel.setPreferredSize(new Dimension(60, currentWindowHeight));
        rightPanel = new RightPanel();
    }

    private void initView()
    {
        setResizable(false);
        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }

    private void centerScreen()
    {
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocation((tk.getScreenSize().width - currentWindowWidth) / 2,
                (tk.getScreenSize().height - currentWindowHeight) / 2);
    }
}
