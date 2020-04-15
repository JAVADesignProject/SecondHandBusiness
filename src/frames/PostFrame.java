package frames;

import panels.PostLeftPanel;
import panels.PostRightPanel;

import javax.swing.*;
import java.awt.*;

public class PostFrame extends JFrame {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 550;

    private PostLeftPanel postLeftPanel;
    private PostRightPanel postRightPanel;

    private static PostFrame context;

    public PostFrame() {
        context = this;
        initComponents();
        centerScreen();
        initView();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        postLeftPanel = new PostLeftPanel();
        postLeftPanel.setPreferredSize(new Dimension(150, WINDOW_HEIGHT));
        postRightPanel = new PostRightPanel();
//        postLeftPanel.setBorder(new LineBorder(Colors.MAIN_COLOR_DARKER));
//        postRightPanel.setBorder(new LineBorder(Colors.MAIN_COLOR_DARKER));
    }

    private void initView() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("发布二手商品");
        setResizable(false);
        setVisible(true);
        add(postLeftPanel, BorderLayout.WEST);
        add(postRightPanel,BorderLayout.CENTER);
        System.out.println("set");
    }

    private void centerScreen() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocation((tk.getScreenSize().width - WINDOW_WIDTH) / 2,
                (tk.getScreenSize().height - WINDOW_HEIGHT) / 2);
    }
}
