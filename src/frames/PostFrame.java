package frames;

import panels.PostPanel;

import javax.swing.*;
import java.awt.*;

public class PostFrame extends JFrame {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 550;

    private PostPanel postPanel;

    private static PostFrame context;

    public PostFrame() {
        //super(MainFrame.context, "发布二手商品", true);
        context = this;
        initComponents();
        centerScreen();
        initView();


    }

    private void initComponents() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        postPanel = new PostPanel();

    }

    private void initView() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("发布二手商品");
        setResizable(false);
        setVisible(true);
        add(postPanel);
        System.out.println("set");
    }

    private void centerScreen() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocation((tk.getScreenSize().width - WINDOW_WIDTH) / 2,
                (tk.getScreenSize().height - WINDOW_HEIGHT) / 2);
    }
}
