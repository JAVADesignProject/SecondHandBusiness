package client.panels;

import client.components.Colors;
import client.components.MKButton;
import client.frames.NormalProductionFrame;
import client.listener.AbstractMouseListener;
import client.utils.CurrentUser;
import client.utils.FontUtil;
import client.utils.ImageTools;

import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class HomePanel extends JPanel {
    private JPanel upPanel;
    private JPanel downPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel centerPanel;
    private MKButton nextPage;
    private MKButton previousPage;

    public HomePanel(){
        initComponents();
        initView();
    }

    private void initComponents() {
        upPanel = new JPanel();
        downPanel = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        centerPanel = new JPanel();

        nextPage = new MKButton(null, Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER, new ImageIcon("res/image/nextPage.png"));
        previousPage = new MKButton(null, Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER, new ImageIcon("res/image/previousPage.png"));
        nextPage.setPreferredSize(new Dimension(30,20));
        previousPage.setPreferredSize(new Dimension(30,20));


        upPanel.setPreferredSize(new Dimension(840,40));
        downPanel.setPreferredSize(new Dimension(840,40));
        leftPanel.setPreferredSize(new Dimension(30,25));
        rightPanel.setPreferredSize(new Dimension(30,25));
        centerPanel.setPreferredSize(new Dimension(750,550));

        for (var i : CurrentUser.productions) {
            if (!i.auction) {
                File temp = new File("res/cache/" + i.name + ".jpeg");
                try {
                    if (!temp.exists()) {
                        FileImageOutputStream imageOutput = new FileImageOutputStream(temp);
                        imageOutput.write(i.pic, 0, i.pic.length);
                        imageOutput.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ImageIcon icon = new ImageIcon(temp.getPath());
                icon.setImage(icon.getImage().getScaledInstance(ImageTools.getImgWidth(temp) / 3, ImageTools.getImgHeight(temp) / 3, Image.SCALE_SMOOTH));
                JLabel label = new JLabel(i.name, icon, SwingConstants.CENTER);
                label.setVerticalTextPosition(SwingConstants.BOTTOM);
                label.setHorizontalTextPosition(SwingConstants.CENTER);
                label.setFont(FontUtil.getDefaultFont(18));
                label.addMouseListener(new AbstractMouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        NormalProductionFrame frame = new NormalProductionFrame(i, temp);
                    }
                });
                centerPanel.add(label);
            }
    }
}

    private void initView() {
        centerPanel.setLayout(new GridLayout(2, 3, 20, 20));
        downPanel.add(previousPage);
        downPanel.add(nextPage);

        setLayout(new BorderLayout());
        add(upPanel, BorderLayout.NORTH);
        add(downPanel, BorderLayout.SOUTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);
    }
}
